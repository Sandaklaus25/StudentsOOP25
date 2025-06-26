package models;

import interfaces.FileSystemReceiver;

import java.util.Map;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
/**
 * The {@code FileManager} class handles loading and saving application data
 * from and to text files. It implements the {@link FileSystemReceiver} interface.
 * <p>
 * It is responsible for parsing and formatting data related to students, disciplines,
 * and specialties.
 * </p>
 */
public class FileManager implements FileSystemReceiver {
    private static final FileManager instance = new FileManager();

    private boolean isLoaded;
    private File loadedFile;
    /**
     * Private constructor to enforce singleton pattern.
     */
    private FileManager() {
        isLoaded = false;
    }
    /**
     * Returns the singleton instance of FileManager.
     *
     * @return the singleton SpecialtyManager instance
     */
    public static FileManager getInstance() {return instance;}
    /**
     * Returns whether a file is currently loaded.
     *
     * @return {@code true} if a file is loaded, {@code false} otherwise
     */
    public boolean isLoaded() {
        return isLoaded;
    }
    /**
     * Sets the loaded state of the file manager.
     *
     * @param isLoaded {@code true} if a file is considered loaded, {@code false} otherwise
     */
    public void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }
    /**
     * Returns the currently loaded file.
     *
     * @return the loaded file, or {@code null} if none is loaded
     */
    public File getLoadedFile() {
        return loadedFile;
    }
    /**
     * Opens and loads data from a file given by name. Parses disciplines,
     * specialties, and students from the file.
     *
     * @param input the filename to load (without path)
     * @return {@code true} if the file was loaded successfully, {@code false} otherwise
     */
    @Override
    public boolean openFile(String input) {
        if (this.isLoaded()) {
            System.out.println("There is existing loaded file!");
            return false;
        }

        String filename = input.trim();

        loadedFile = new File(System.getProperty("user.dir") + File.separator + "Saves" + File.separator + filename);

        try (BufferedReader reader = new BufferedReader(new FileReader(loadedFile))) {
            return processFileContent(reader, SpecialtyManager.getInstance(), DisciplineManager.getInstance());
        } catch (IOException e) {
            System.out.println("Error when loading file! Probably wrong name or type!");
            return false;
        }
    }

    private boolean processFileContent(BufferedReader reader, SpecialtyManager specialtiesManager, DisciplineManager disciplineManager) throws IOException {
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
                    processSpecialtiesSection(line, specialtiesManager);
                    break;

                case "# Students":
                    try {
                        if (!processStudentsSection(line, reader)) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("Error when loading student data! " + e.getMessage());
                        return false;
                    }
                    break;
            }
        }
        setLoaded(true);
        System.out.println("File loaded successfully: " + loadedFile.getName());
        return true;
    }

    private void processDisciplinesSection(String line, DisciplineManager disciplineManager) {
        String[] discParts = line.split(" ");
        if (discParts.length == 2) {
            String name = discParts[0].trim();
            boolean mandatory = Boolean.parseBoolean(discParts[1].trim());
            disciplineManager.create(name, mandatory);
        }
    }

    private void processSpecialtiesSection(String line, SpecialtyManager specialtiesManager) {
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

    private boolean processStudentsSection(String line, BufferedReader reader) throws IOException {
        StudentsManager studentsManager = StudentsManager.getInstance();
        int studentCount = 0;
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
            StudentStatus status = StudentStatus.valueOf(parts[5].trim().toUpperCase());

            Specialty specialty = SpecialtyManager.getSpecialtyByName(specialtyName);
            Student student = new Student(fullName, facultyNumber, course, specialty, group);
            student.setStatus(status);

            if (parts.length >= 7 && !parts[6].trim().isEmpty()) {
                processStudentDisciplines(student, parts[6]);
            }

            if(studentsManager.addStudent(student)){
                studentCount++;
            }
        } while ((line = reader.readLine()) != null && !line.trim().startsWith("#"));
        System.out.println(" "+studentCount+" students have been loaded successfully.\n");
        return true;
    }

    private void processStudentDisciplines(Student student, String disciplinesData) {
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
                            if (grade >= 2 && grade <= 6) {
                                grades.add(grade);
                            } else {
                                throw new IllegalArgumentException();
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("A grade has an invalid value! Faculty number: " + student.getFacultyNumber());
                }
            }
            student.addDisciplineGrades(discipline, grades);
        }
    }
    /**
     * Saves the current application data to the specified file in the proper text format.
     *
     * @param filename the name of the file to write (with or without .txt extension)
     * @return {@code true} if the file was saved successfully, {@code false} otherwise
     */
    @Override
    public boolean writeFile(String filename) {
        filename = filename.trim();
        if(!filename.endsWith(".txt")) filename += ".txt";
        File file = new File(System.getProperty("user.dir") + File.separator + "Saves" + File.separator + filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            writer.write("# Disciplines\n");
            for (Discipline d : DisciplineManager.getInstance().getDisciplines()) {
                writer.write(d.getName() + " " + d.isMandatory());
                writer.newLine();
            }

            writer.write("\n# Specialties\n");
            for (Specialty s : SpecialtyManager.getInstance().getSpecialties()) {
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
            for (Student student : StudentsManager.getInstance().getStudents()) {
                writer.write(formatStudentForFile(student));
                writer.newLine();
            }

            System.out.println("File saved successfully: " + file.getName());
            return true;
        } catch (Exception e) {
            System.out.println("There was an error while writing on the file: " + e.getMessage());
            return false;
        }
    }
    /**
     * Formats a student object into a string suitable for saving in a text file.
     *
     * @param student the student to format
     * @return the formatted string
     */
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
    /**
     * Clears all data and resets the file manager state.
     *
     * @return {@code true} when the operation completes
     */
    @Override
    public boolean closeFile() {
        SpecialtyManager.getInstance().clear();
        StudentsManager.getInstance().clear();
        DisciplineManager.getInstance().clear();
        setLoaded(false);
        System.out.println("File was closed. (Data reset)");
        return true;
    }
    /**
     * Builds a list of discipline grade entries for a student in string format.
     *
     * @param student the student whose grades are being processed
     * @return list of discipline entries as strings
     */
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