package Models;

import java.io.*;
import java.util.*;

public class FileManager {
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

    public void load(String input) {
        if (this.isLoaded()) {
            System.out.println("Има вече зареден файл, затворете първия!");
            return;
        }
        SpecialtyManager specialtiesManager = SpecialtyManager.getInstance();
        StudentsManager studentsManager = StudentsManager.getInstance();
        DisciplineManager disciplineManager = DisciplineManager.getInstance();

        String filename = input.trim();
        if (!filename.endsWith(".txt")) {
            filename += ".txt";
        }
        loadedFile = new File(System.getProperty("user.dir") + File.separator + filename);

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
                                Discipline d = DisciplineManager.getDisciplineByString(specParts[0].trim());
                                if (d != null) {
                                    List<Byte> years = new ArrayList<>();
                                    for (int i = 1; i < specParts.length; i++) {
                                        years.add(Byte.parseByte(specParts[i]));
                                    }
                                    last.getDisciplineCourses().put(d, years);
                                }
                            }
                        }
                        break;

                    case "# Students":
                        break;
                }
            }
            System.out.println("Успешно зареден файл: " + loadedFile.getName());
            setLoaded(true);
        } catch (IOException e) {
            System.out.println("Грешка при зареждането на файл! Най-вероятно е объркано име или тип!");
        }
    }

    public void saveTo(String filename) {
        SpecialtyManager specialtiesManager = SpecialtyManager.getInstance();
        StudentsManager studentsManager = StudentsManager.getInstance();
        DisciplineManager disciplineManager = DisciplineManager.getInstance();

        filename = filename.trim();
        if (!filename.endsWith(".txt")) {
            filename += ".txt";
        }
        File file = new File(System.getProperty("user.dir") + File.separator + filename);

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
            System.out.println("Успешно е запазен файла: " + file.getName());
        } catch (IOException e) {
            System.out.println("Нещо се обърка при запазването!");
        }
    }

    public void close() {
        SpecialtyManager.getInstance().clear();
        StudentsManager.getInstance().clear();
        DisciplineManager.getInstance().clear();
        setLoaded(false);
        System.out.println(">> Файлът е успешно затворен.");
    }
}
