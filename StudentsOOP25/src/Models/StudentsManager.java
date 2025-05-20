package Models;

import java.util.*;

public class StudentsManager {
    private static final StudentsManager instance = new StudentsManager();
    private final List<Student> students = new ArrayList<>();

    private StudentsManager() {}

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public static StudentsManager getInstance() {
        return instance;
    }

    public String enroll(String facultyNumber, Specialty specialty, char[] group, String fullName) {
        if (facultyNumber == null || specialty == null || group == null || fullName == null) {
            return "Грешка: Всички параметри са задължителни";
        }

        for (Student student : students) {
            if (student.getFacultyNumber().equals(facultyNumber)) {
                return "Студент с факултетен номер " + facultyNumber + " вече съществува в системата!";
            }
        }
        Student s = new Student(fullName, facultyNumber, (byte) 1, specialty, group);
        students.add(s);
        s.setUpCourseDisciplines();
        return "Студентът " + s.getFullName() + " е успешно записан/а.";
    }

    public String advance(String facultyNumber) {
        if (facultyNumber == null) {
            return "Грешка: Невалиден факултетен номер";
        }

        for(Student s : students) {
            if(s.getFacultyNumber().equals(facultyNumber)) {
                if(!s.getStatus().isActive()) {
                    return ("Студентът трябва да е в активен статус.");
                }
                if(s.getCourse()==4)
                {
                    return "Студентът е в последния си курс и трябва да се дипломира вместо да сменя курс";
                }

                try {
                    s.setCourseUp();
                    return ("Студентът успешно е преминал в следващия курс. Невзетите изпити са запазени.");
                } catch (IllegalStateException e) {
                    return e.getMessage();
                }
            }
        }
        return ("Студентът с факултетен номер " + facultyNumber + " не е намерен!");
    }

    public String addGrade(String facultyNumber, Discipline discipline, int grade) {
        if (facultyNumber == null || discipline == null) {
            return "Грешка: Факултетен номер и дисциплина са задължителни";
        }

        if (!(grade > 1 && grade < 7)) {
            return "Невалиден вход за оценка! Оценката трябва да е число между 2 и 6!";
        }

        for (Student s : students) {
            if(s.getFacultyNumber().equals(facultyNumber) && s.getStatus().isActive()) {
                if(s.getDisciplineGrades().containsKey(discipline)) {
                    if (grade == 2 && s.getDisciplineGrades().get(discipline).isEmpty()) {
                        return "Студента не е издържал изпита! Оценката НЕ БЕ запазена и той трябва да повтаря!";
                    }

                    s.getDisciplineGrades().get(discipline).add(grade);
                    s.updateAverageGrade();
                    return ("Оценка успешно добавена.");
                }
                else return ("Студента няма записана дисциплина с това име!");
            }
            else if(s.getFacultyNumber().equals(facultyNumber) && !s.getStatus().isActive())
            {
                return "Студент трябва да е в активен статус за да може да му бъде връчена оценка";
            }
        }
        return "Студент с факултетен номер "+facultyNumber+" не е намерен!";
    }

    public String change(String facultyNumber, String option, String value) {
        if (facultyNumber == null || option == null || value == null) {
            return "Грешка: Всички параметри са задължителни";
        }
        for (Student s : students) {
            if (s.getFacultyNumber().equals(facultyNumber) && s.getStatus().isActive()) {
                switch (option.toLowerCase()) {
                    case "group":
                        if (value.length() != 2) {
                            return "Групата трябва да е точно 2 символа.";
                        }

                        char[] group = {value.charAt(0), value.charAt(1)};
                        try {
                            if(!Character.isDigit(group[0]) || !Character.isLetter(group[1])) {
                                throw new IllegalArgumentException("Групата може да е само 2 символа от които първото е число второто е буква. Пример: 2а");
                            }
                        } catch (IllegalArgumentException e) {
                            return e.getMessage();
                        }

                        s.setGroup(group);
                        return ("Групата на студента успешно бе сменена.");

                    case "year":
                        byte newCourse;
                        try {
                            newCourse = Byte.parseByte(value);
                        } catch (NumberFormatException e) {
                            return "Невалиден формат за курс! Трябва да е число!";
                        }
                        if (!s.hasPassedRequiredSubjectsWithMaxTwoCoursesFails()) {
                            throw new IllegalStateException("Студентът не е преминал достатъчно задължителни дисциплини за да премине в следващ курс.");
                        }

                        if (newCourse == s.getCourse() + 1) {
                            return advance(facultyNumber);
                        } else {
                            return "Може да се премине само в следващ курс!";
                        }

                    case "program":
                        try {
                            Specialty newSpecialty = SpecialtyManager.getSpecialtyByName(value);

                            if (s.hasPassedRequirementsSubjectsForSpecialty(newSpecialty)) {
                                s.removeEmptyOldDisciplines(newSpecialty);
                                s.setSpecialty(newSpecialty);
                                s.setUpCourseDisciplines();
                                return ("Успешно преминаване към друга специалност.");
                            } else {
                                return ("Студентът не може да премине към другата специалност поради оценките си.");
                            }
                        } catch (ClassNotFoundException e) {
                            return e.getMessage();
                        }
                    default:
                        return "Невалидна опция: " + option + "\t\t\t Трябва да е group, program или year!";
                }
            } else if (s.getFacultyNumber().equals(facultyNumber)) {
                return "Студентът трябва да е в активен статус за да се променят данните му.";
            }
        }
        return ("Студентът с факултетен номер " + facultyNumber + " не може да бъде намерен!");
    }

    public String graduate(String facultyNumber) {
        if (facultyNumber == null) {
            return "Грешка: Невалиден факултетен номер";
        }

        for (Student s : students) {
            if(s.getFacultyNumber().equals(facultyNumber)) {
                if (!s.getStatus().isActive()) {
                    return ("Студентът трябва да е в активен статус!");
                }
                if(s.getCourse() != 4)
                {
                    return "Студентът трябва да е преминал през всички курсове (1-4)";
                }
                if (s.hasPassedAllSubjects()) {
                    s.setStatus(StudentStatus.graduated);
                    return ("Честито дипломиране на студентът!");
                } else {
                    return ("Студентът не е преминал успешно някои дисциплини и не може да се дипломира!");
                }
            }
        }
        return ("Студентът с факултетен номер " + facultyNumber + " не е намерен!");
    }

    public String interrupt(String facultyNumber) {
        if (facultyNumber == null) {
            return "Грешка: Невалиден факултетен номер";
        }

        for (Student s : students) {
            if(s.getFacultyNumber().equals(facultyNumber)) {
                if (s.getStatus().isInterrupted()) {
                    return "Студентът вече е в прекъснат статус.";
                }else if(s.getStatus().hasGraduated()){
                    return "Дипломирал се студент не може да стане прекъснат.";
                }
                s.setStatus(StudentStatus.interrupted);
                return ("Студентът е обновен като прекъснал.");
            }
        }
        return ("Студентът с факултетен номер " + facultyNumber + " не е намерен!");
    }

    public String resume(String facultyNumber) {
        if (facultyNumber == null) {
            return "Грешка: Невалиден факултетен номер";
        }

        for (Student s : students) {
            if(s.getFacultyNumber().equals(facultyNumber)) {
                if (s.getStatus().isActive()) {
                    return "Студентът е активен.";
                }
                else if(s.getStatus().hasGraduated()){
                    return "Дипломирал се студент не може да се върне.";
                }
                s.setStatus(StudentStatus.active);
                return ("Студентът е обновен като активен/продължаващ.");
            }
        }
        return ("Студентът с факултетен номер " + facultyNumber + " не е намерен!");
    }

    public String print(String facultyNumber) {
        if (facultyNumber == null) {
            return "Грешка: Невалиден факултетен номер";
        }

        for (Student s : students) {
            if(s.getFacultyNumber().equals(facultyNumber)) {
                return ("Извеждане справка на студент с факултетен номер: "+facultyNumber+s.toStringAlternative());
            }
        }
        return ("Студентът с факултетен номер " + facultyNumber + " не е намерен!");
    }

    public String printAll(Specialty specialty, Byte course) {
        if (specialty == null || course == null) {
            return "Грешка: Специалност и курс са задължителни";
        }

        System.out.println("\nИзвеждане справка на всички студенти от специалност "+specialty.getName()+" и курс "+course+":\n--------------------");
        boolean found = false;

        for (Student s : students) {
            if(s.getSpecialty().equals(specialty) && s.getCourse().equals(course)) {
                System.out.println("."+s);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Няма студенти, отговарящи на критериите.");
        }

        System.out.println(".");
        return "--------------------\nКрай!";
    }

    public String enrollIn(String facultyNumber, Discipline discipline) {
        if (facultyNumber == null || discipline == null) {
            return "Грешка: Факултетен номер и дисциплина са задължителни";
        }

        for (Student s : students) {
            if(s.getFacultyNumber().equals(facultyNumber) && s.getStatus().isActive()) {
                if (!s.getSpecialty().getDisciplineCourses().containsKey(discipline)) {
                    return "Дисциплината не принадлежи към специалността на студента.";
                }

                if (!s.getSpecialty().getDisciplineCourses().get(discipline).contains(s.getCourse())) {
                    return "Дисциплината не е достъпна за текущия курс на студента.";
                }

                if(s.getDisciplineGrades().putIfAbsent(discipline, new ArrayList<>()) != null) {
                    return ("Дисциплината вече беше добавена. Нищо не се промени.");
                }

                s.updateAverageGrade();
                return ("Дисциплината "+discipline.getName()+" бе добавена.");
            }
            else if(s.getFacultyNumber().equals(facultyNumber)) {
                return ("Дисциплината не може да бъде добавена за студента.");
            }
        }
        return ("Студентът с факултетен номер " + facultyNumber + " не е намерен!");
    }

    public String protocol(Discipline discipline) {
        if (discipline == null) {
            return "Грешка: Невалидна дисциплина";
        }

        // Карта: Objects.Specialty -> (Курс -> Списък със студенти)
        HashMap<Specialty, HashMap<Byte, List<Student>>> protocolMap = new HashMap<>();

        for (Student s : students) {
            if (s.getDisciplineGrades().containsKey(discipline)) {

                s.updateAverageGrade();

                HashMap<Byte, List<Student>> courseMap = protocolMap.computeIfAbsent(s.getSpecialty(), k -> new HashMap<>());

                List<Student> studentList = courseMap.computeIfAbsent(s.getCourse(), k -> new ArrayList<>());

                studentList.add(s);
            }
        }

        // Извеждане на протоколите
        for (HashMap.Entry<Specialty, HashMap<Byte, List<Student>>> entry : protocolMap.entrySet()) {
            Specialty specialty = entry.getKey();
            HashMap<Byte, List<Student>> courseMap = entry.getValue();

            for (HashMap.Entry<Byte, List<Student>> courseEntry : courseMap.entrySet()) {
                Byte course = courseEntry.getKey();
                List<Student> studentList = getStudentsSortBy(courseEntry);

                System.out.println("\n------------------");
                System.out.println("Протокол за специалност: " + specialty.getName() +
                        ", курс: " + course +
                        ", дисциплина: " + discipline.getName());
                System.out.println("------------------");

                for (Student s : studentList) {
                    System.out.print(".");
                    System.out.println(s);
                }
                System.out.print(".");
            }
        }
        System.out.println("\n------------------");
        return "Извеждането на протоколи приключи.";
    }

    private static List<Student> getStudentsSortBy(Map.Entry<Byte, List<Student>> courseEntry) {
        List<Student> studentList = courseEntry.getValue();

        studentList.sort((s1, s2) -> {
            try {
                int fn1 = Integer.parseInt(s1.getFacultyNumber());
                int fn2 = Integer.parseInt(s2.getFacultyNumber());
                return fn1 - fn2;
            } catch (NumberFormatException e) {
                return s1.getFacultyNumber().compareTo(s2.getFacultyNumber());
            }
        });
        return studentList;
    }

    public String report(String facultyNumber) {
        if (facultyNumber == null) {
            return "Грешка: Невалиден факултетен номер";
        }

        StringBuilder builder = new StringBuilder();
        boolean found = false;

        for (Student s : students) {
            if (s.getFacultyNumber().equals(facultyNumber)) {
                found = true;
                System.out.println("Извеждане справка на студент с факултетен номер и име: " + facultyNumber +" "+s.getFullName());
                System.out.println("Оценки на студентът:");

                if (s.getDisciplineGrades().isEmpty()) {
                    builder.append("Студентът няма записани оценки.\n");
                } else {
                    for (HashMap.Entry<Discipline, List<Integer>> entry : s.getDisciplineGrades().entrySet()) {
                        builder.append(entry.getKey().getName()).append(": ");

                        if (entry.getValue().isEmpty()) {
                            builder.append("няма оценки");
                        } else {
                            builder.append(entry.getValue());
                        }

                        builder.append("\n");
                    }
                }
            }
        }

        if (!found) {
            return "Студентът с факултетен номер " + facultyNumber + " не е намерен!";
        }

        return builder.toString();
    }

    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Студентът не може да бъде null");
        }

        for (Student existingStudent : students) {
            if (existingStudent.getFacultyNumber().equals(student.getFacultyNumber())) {
                return;
            }
        }

        students.add(student);
    }

    public void clear() {
        students.clear();
    }
}
