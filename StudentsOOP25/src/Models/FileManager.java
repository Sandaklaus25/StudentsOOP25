package Models;

import Commands.Interfaces.FileSystemReceiver;

import java.util.Map;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class FileManager implements FileSystemReceiver {
    private boolean isLoaded;
    private File loadedFile;

    public FileManager() {
        isLoaded = false;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    public File getLoadedFile() {
        return loadedFile;
    }

    @Override
    public String openFile(String input) {
        if (this.isLoaded()) {
            return "Има вече зареден файл, затвори първия!";
        }
        SpecialtyManager specialtiesManager = SpecialtyManager.getInstance();
        DisciplineManager disciplineManager = DisciplineManager.getInstance();

        String filename = input.trim();

        loadedFile = new File(System.getProperty("user.dir") + File.separator + "Saves" + File.separator + filename);

        try (BufferedReader reader = new BufferedReader(new FileReader(loadedFile))) {
            return processFileContent(reader, specialtiesManager, disciplineManager);
        } catch (IOException e) {
            return "Грешка при зареждането на файл! Най-вероятно е объркано име или тип!";
        }
    }

    private String processFileContent(BufferedReader reader, SpecialtyManager specialtiesManager, DisciplineManager disciplineManager) throws IOException {
        String line;
        String section = "";

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("#")) {
                section = line;
                System.out.println();
                continue;
            }

            switch (section) {
                case "# Disciplines":
                    processDisciplinesSection(line, disciplineManager);
                    break;

                case "# Specialties":
                    try {
                        processSpecialtiesSection(line, specialtiesManager);
                    } catch (ClassNotFoundException e) {
                        return e.getMessage();
                    }
                    break;

                case "# Students":
                    try {
                        String result = processStudentsSection(line, reader);
                        if (!result.equals("success")) {
                            return result;
                        }
                    } catch (Exception e) {
                        return "Грешка при зареждането на студентски данни: " + e.getMessage();
                    }
                    break;
            }
        }
        this.isLoaded = true;
        return "Успешно зареден файл: " + loadedFile.getName();
    }

    private void processDisciplinesSection(String line, DisciplineManager disciplineManager) {
        String[] discParts = line.split(" ");
        if (discParts.length == 2) {
            String name = discParts[0].trim();
            boolean mandatory = Boolean.parseBoolean(discParts[1].trim());
            disciplineManager.create(name, mandatory);
        }
    }

    private void processSpecialtiesSection(String line, SpecialtyManager specialtiesManager)
            throws ClassNotFoundException {
        if (line.indexOf(' ') == -1) {
            specialtiesManager.create(line.trim());
        } else {
            List<Specialty> allSpecialties = specialtiesManager.getSpecialties();
            if (!allSpecialties.isEmpty()) {
                Specialty last = allSpecialties.getLast();
                if (last != null) {
                    String[] specParts = line.split(" ");
                    Discipline d = DisciplineManager.getDisciplineByName(specParts[0].trim());

                    List<Byte> years = new ArrayList<>();
                    for (int i = 1; i < specParts.length; i++) {
                        years.add(Byte.parseByte(specParts[i]));
                    }
                    last.addDiscipline(d, years);
                }
            }
        }
    }

    private String processStudentsSection(String line, BufferedReader reader) throws IOException, ClassNotFoundException {
        StudentsManager studentsManager = StudentsManager.getInstance();

        do {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\|");

            if (parts.length < 6) {
                continue;
            }

            String facultyNumber = parts[0].trim();
            String fullName = parts[1].trim();
            String specialtyName = parts[2].trim();
            byte course = Byte.parseByte(parts[3].trim());
            char[] group = parts[4].trim().toCharArray();
            StudentStatus status = StudentStatus.valueOf(parts[5].trim().toLowerCase());

            Specialty specialty = SpecialtyManager.getSpecialtyByName(specialtyName);
            Student student = new Student(fullName, facultyNumber, course, specialty, group);
            student.setStatus(status);

            if (parts.length >= 7 && !parts[6].trim().isEmpty()) {
                processStudentDisciplines(student, parts[6]);
            }

            studentsManager.addStudent(student);
        } while ((line = reader.readLine()) != null && !line.trim().startsWith("#"));

        return "success";
    }

    private void processStudentDisciplines(Student student, String disciplinesData)
            throws ClassNotFoundException {
        String[] disciplineEntries = disciplinesData.split(";");
        for (String entry : disciplineEntries) {
            if (entry.trim().isEmpty()) continue;

            String[] discSplit = entry.trim().split(":", 2);
            String disciplineName = discSplit[0].trim();
            String gradesRaw = discSplit.length == 2 ? discSplit[1].trim() : "";

            Discipline discipline = DisciplineManager.getDisciplineByName(disciplineName);

            List<Integer> grades = new ArrayList<>();
            if (!gradesRaw.isEmpty()) {
                try {
                    for (String gradeStr : gradesRaw.split(",")) {
                        gradeStr = gradeStr.trim();
                        if (!gradeStr.isEmpty()) {
                            int grade = Integer.parseInt(gradeStr);
                            if ((grade >= 3 && grade <= 6) || (grade == 2 && !grades.isEmpty())) {
                                grades.add(grade);
                            } else {
                                throw new NumberFormatException();
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Грешка при зареждане на оценка за " + student.getFacultyNumber() + '!');
                }
            }

            student.addDisciplineGrades(discipline, grades);
        }
    }

    @Override
    public String writeFile(String filename) {
        SpecialtyManager specialtiesManager = SpecialtyManager.getInstance();
        DisciplineManager disciplineManager = DisciplineManager.getInstance();
        StudentsManager studentsManager = StudentsManager.getInstance();

        filename = filename.trim();
        if(!filename.endsWith(".txt")) filename += ".txt";
        File file = new File(System.getProperty("user.dir") + File.separator + "Saves" + File.separator + filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            writer.write("# Disciplines\n");
            for (Discipline d : disciplineManager.getDisciplines()) {
                writer.write(d.getName() + " " + d.isMandatory());
                writer.newLine();
            }

            writer.write("\n# Specialties\n");
            for (Specialty s : specialtiesManager.getSpecialties()) {
                writer.write(s.getName());
                writer.newLine();

                for (Map.Entry<Discipline, List<Byte>> entry : s.getDisciplineCourses().entrySet()) {
                    writer.write(entry.getKey().getName());
                    for (Byte year : entry.getValue()) {
                        writer.write(" " + year);
                    }
                    writer.newLine();
                }
                writer.newLine();
            }

            writer.write("# Students\n");
            for (Student student : studentsManager.getStudents()) {
                writer.write(formatStudentForFile(student));
                writer.newLine();
            }

            return "Успешно е запазен файла: " + file.getName();
        } catch (Exception e) {
            return "Нещо се обърка при записването: " + e.getMessage();
        }
    }

    private String formatStudentForFile(Student student) {
        StringBuilder studentLine = new StringBuilder();

        studentLine
                .append(student.getFacultyNumber()).append(" | ")
                .append(student.getFullName()).append(" | ")
                .append(student.getSpecialty().getName()).append(" | ")
                .append(student.getCourse()).append(" | ")
                .append(new String(student.getGroup())).append(" | ")
                .append(student.getStatus().name().toLowerCase());

        studentLine.append(" | ");
        List<String> disciplineEntries = buildDisciplineEntries(student);
        studentLine.append(String.join("; ", disciplineEntries));

        return studentLine.toString();
    }

    @Override
    public String closeFile() {
        SpecialtyManager.getInstance().clear();
        StudentsManager.getInstance().clear();
        DisciplineManager.getInstance().clear();
        this.isLoaded = false;
        return "Файлът е успешно затворен.";
    }

    private List<String> buildDisciplineEntries(Student student) {
        List<String> disciplineEntries = new ArrayList<>();

        for (Map.Entry<Discipline, List<Integer>> entry : student.getDisciplineGrades().entrySet()) {
            Discipline discipline = entry.getKey();
            List<Integer> grades = entry.getValue();

            StringBuilder entryBuilder = new StringBuilder(discipline.getName());
            entryBuilder.append(":");

            if (!grades.isEmpty()) {
                boolean isFirst = true;
                for (Integer grade : grades) {
                    if (!isFirst) {
                        entryBuilder.append(",");
                    }
                    entryBuilder.append(grade);
                    isFirst = false;
                }
            }

            disciplineEntries.add(entryBuilder.toString());
        }

        return disciplineEntries;
    }
}