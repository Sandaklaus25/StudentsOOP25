package Models;

import Commands.Interfaces.FileSystemReceiver;

import java.io.*;
import java.util.*;

public class FileManager implements FileSystemReceiver {
    private boolean is_loaded;
    private File loadedFile;

    public FileManager() {
        is_loaded = false;
    }

    public boolean isLoaded() {
        return is_loaded;
    }

    public void setLoaded(boolean is_loaded) {
        this.is_loaded = is_loaded;
    }

    public File getLoadedFile() {
        return loadedFile;
    }
    @Override
    public String openFile(String input) {
        if (this.isLoaded()) {
            return ("Има вече зареден файл, затвори първия!");
        }
        SpecialtyManager specialtiesManager = SpecialtyManager.getInstance();
        StudentsManager studentsManager = StudentsManager.getInstance();
        DisciplineManager disciplineManager = DisciplineManager.getInstance();

        String filename = input.trim();
        loadedFile = new File(System.getProperty("user.dir") + File.separator + "Saves" + File.separator + filename);

        try (BufferedReader reader = new BufferedReader(new FileReader(loadedFile))) {
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
                        String[] discParts = line.split(" ");
                        if (discParts.length == 2) {
                            String name = discParts[0].trim();
                            boolean mandatory = Boolean.parseBoolean(discParts[1].trim());
                            disciplineManager.create(name, mandatory);
                        }
                        break;

                    case "# Specialties":
                        if (line.indexOf(' ') == -1) {
                            specialtiesManager.create(line.trim());
                        } else {
                            Specialty last = specialtiesManager.getSpecialties().getLast();
                            if (last != null) {
                                String[] specParts = line.split(" ");
                                Discipline d;
                                try {
                                    d = DisciplineManager.getDisciplineByString(specParts[0].trim());

                                    List<Byte> years = new ArrayList<>();
                                    for (int i = 1; i < specParts.length; i++) {
                                        years.add(Byte.parseByte(specParts[i]));
                                    }
                                    last.getDisciplineCourses().put(d, years);
                                }catch (ClassNotFoundException e)
                                {
                                    return e.getMessage();
                                }
                            }
                        }
                        break;

                    case "# Students":
                        break;
                }
            }
            this.is_loaded = true;
            return "Успешно зареден файл: " + loadedFile.getName();
        } catch (IOException e) {
            return ("Грешка при зареждането на файл! Най-вероятно е объркано име или тип!");
        }
    }
    @Override
    public String writeFile(String filename) {
        SpecialtyManager specialtiesManager = SpecialtyManager.getInstance();
        StudentsManager studentsManager = StudentsManager.getInstance();
        DisciplineManager disciplineManager = DisciplineManager.getInstance();

        filename = filename.trim();
        File file = new File(System.getProperty("user.dir") + File.separator + "Saves" + File.separator + filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("# Disciplines\n");
            for (Discipline d : disciplineManager.getDisciplines()) {
                writer.write(d.getName() + " " + d.getIsMandatory());
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
            return ("Успешно е запазен файла: " + file.getName());
        } catch (Exception e) {
            return "Нещо се обърка при записването!";
        }
    }
    @Override
    public String closeFile() {
        SpecialtyManager.getInstance().getSpecialties().clear();
        StudentsManager.getInstance().getStudents().clear();
        DisciplineManager.getInstance().getDisciplines().clear();
        this.is_loaded = false;
        return "Файлът е успешно затворен.";
    }
}
