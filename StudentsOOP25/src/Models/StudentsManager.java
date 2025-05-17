package Models;

import java.util.*;

public class StudentsManager {
    private static final StudentsManager instance = new StudentsManager();
    private final List<Student> students = new ArrayList<>();

    private StudentsManager() {}

    public List<Student> getStudents() {
        return students;
    }

    public static StudentsManager getInstance() {
        return instance;
    }

    public String enroll(String facultyNumber, Specialty specialty, char[] group, String fullName) {

        for (Student existingStudent : students) {
            if (existingStudent.getFacultyNumber().equals(facultyNumber)) {
                return "Студент с факултетен номер " + facultyNumber + " вече съществува в системата!";
            }
        }
        Student s = new Student(fullName, facultyNumber, (byte) 1, specialty, group);
        students.add(s);
        return "Студентът " + s.getFullName() + " е успешно записан/а.";
    }

    public String advance(String facultyNumber) {
        for(Student s : students)
        {
            if(s.getFacultyNumber().equals(facultyNumber) && s.getStatus()== StudentStatus.active)
            {
                s.setCourseUp();
               return ("Студентът успешно е преминал курса.");

            }
            else if(s.getFacultyNumber().equals(facultyNumber) && s.getStatus()!= StudentStatus.active)
            {
                return ("Студентът трябва да е в активен статус.");
            }
        }
        return ("Студентът с факултетен номер " + facultyNumber + " не е намерен!");
    }

    public String addGrade(String facultyNumber,Discipline discipline, int grade) {

        for (Student s : students) {
            if(s.getFacultyNumber().equals(facultyNumber) && s.getStatus() == StudentStatus.active)
            {
                if(s.getDisciplineGrades().containsKey(discipline))
                {
                    if (grade == 2 && s.getDisciplineGrades().get(discipline).isEmpty()) {
                        return "Студента не е издържал изпита! Оценката НЕ БЕ запазена и той трябва да повтаря!";
                    }
                    else if (!(grade > 1 && grade < 7)) {
                        return ("Невалиден вход за оценка! Оценката трябва да е число между 2 и 6!");
                    }

                    s.getDisciplineGrades().get(discipline).add(grade);
                    return ("Оценка успешно добавена.");
                }
                else return ("Студента няма записана дисциплина с това име!");
            }
        }
        return "Студент с факултетен номер "+facultyNumber+" не е намерен!";
    }

    public String change(String facultyNumber, String option, String value) {
        for (Student s : students) {
            if (s.getFacultyNumber().equals(facultyNumber)  && s.getStatus()== StudentStatus.active) {
                switch (option.toLowerCase()) {
                    case "group":

                            char[] group = {value.toCharArray()[0], value.toCharArray()[1]};
                        try {
                            if(!Character.isLetter(group[1]) || !Character.isDigit(group[0]))
                                throw new IllegalArgumentException("Групата може да е само 2 символа от които първото е число второто е буква. Пример: 2а");
                        }catch (IllegalArgumentException e){
                            return e.getMessage();
                        }
                            s.setGroup(group);
                         return ("Групата на студента успешно бе сменена.");
                    case "year":
                        if (Byte.parseByte(value) == s.getCourse()+1 && s.hasPassedRequiredSubjectsWithMaxTwoCourses()) {
                            return advance(facultyNumber);
                        }
                    case "program":
                        if (s.hasPassedRequiredSubjects()) {
                                try{
                                    s.setSpecialty(SpecialtyManager.getSpecialtyByString(value));
                                }catch (ClassNotFoundException e) {
                                    return e.getMessage();
                                }
                            return ("Успешно преминаване към друга специалност.");
                        }
                        return ("Студентът не може да преминаване към друга специалност поради оценките си.");

                    default:
                        throw new IllegalArgumentException("Невалидна опция: "+option+"\t\t\t Трябва да е group, program или year!");
                }
            }
        }
        return ("Студентът с факултетен номер" + facultyNumber+ " не може да бъде намерен!");
    }

    public String graduate(String facultyNumber) {
        for (Student s : students)
        {
            if(s.getFacultyNumber().equals(facultyNumber) && s.hasPassedAllSubjects())
            {
                s.setStatus(StudentStatus.graduated);
                return ("Честито дипломиране на студентът!");
            }
            else if(s.getFacultyNumber().equals(facultyNumber) && !s.hasPassedAllSubjects())
            {
                return ("Студентът не е положил успешно някои изпити и не може да се дипломира!");
            }
            else if(s.getFacultyNumber().equals(facultyNumber) && s.getStatus()!= StudentStatus.active)
            {
                return ("Студентът трябва да е в активен статус!");
            }
        }
        return ("Студентът с факултетен номер " + facultyNumber + " не е намерен!");
    }

    public String interrupt(String facultyNumber) {
        for (Student s : students)
        {
            if(s.getFacultyNumber().equals(facultyNumber))
            {
                s.setStatus(StudentStatus.interrupted);
                return ("Студентът е обновен като прекъснал.");
            }
        }
        return ("Студентът с факултетен номер " + facultyNumber + " не е намерен!");
    }

    public String resume(String facultyNumber) {
        for (Student s : students)
        {
            if(s.getFacultyNumber().equals(facultyNumber))
            {
                s.setStatus(StudentStatus.active);
                return ("Студентът е обновен като активен/продължаващ.");
            }
        }
        return ("Студентът с факултетен номер " + facultyNumber + " не е намерен!");
    }

    public String print(String facultyNumber) {
        for (Student s : students)
        {
            if(s.getFacultyNumber().equals(facultyNumber))
            {
                return ("Извеждане справка на студент с факултетен номер: "+facultyNumber+s.toStringAlternative());
            }
        }
        return ("Студентът с факултетен номер " + facultyNumber + " не е намерен!");
    }

    public String printAll(Specialty specialty, Byte course) {
        System.out.println("\nИзвеждане справка на всички студенти от специалност "+specialty.getName()+" и курс "+course+":\n--------------------");
        for (Student s : students)
        {
            if(s.getSpecialty()==specialty && s.getCourse().equals(course))
            {
                System.out.println("."+s);
            }
        }System.out.println(".");
        return "--------------------\nКрай!";
    }

    public String enrollIn(String facultyNumber, Discipline course) {
        for (Student s : students) {
            if(s.getFacultyNumber().equals(facultyNumber)
                    && s.getSpecialty().getDisciplineCourses().containsKey(course)
                    && s.getSpecialty().getDisciplineCourses().get(course).contains(s.getCourse())
                    && s.getStatus()== StudentStatus.active)
            {
                if(s.getDisciplineGrades().putIfAbsent(course, new ArrayList<>())!=null)
                {
                    return ("Дисциплината вече беше добавена. Нищо не се промени.");
                }
                s.updateAverageGrade();
                return ("Дисциплина добавена.");
            }
            else if(s.getFacultyNumber().equals(facultyNumber))
            {
                return ("Дисциплината не може да бъде добавена за студента.");
            }
        }
        return ("Студентът с факултетен номер " + facultyNumber + " не е намерен!");
    }

    public String protocol(Discipline discipline) {
        // Карта: Objects.Specialty -> (Курс -> Списък със студенти)
        HashMap<Specialty, HashMap<Byte, List<Student>>> protocolMap = new HashMap<>();

        for (Student s : students) {
            if (s.getDisciplineGrades().containsKey(discipline)) {   // Проверка дали студентът учи дисциплината
                s.updateAverageGrade();
                protocolMap.computeIfAbsent(s.getSpecialty(), k -> new HashMap<>()) // Създаване на поле за специалност в картата ако няма
                        .computeIfAbsent(s.getCourse(), k -> new ArrayList<>()) // Създаване на поле за курс в картата ако няма
                        .add(s);    // Добавяне на студент в списъка
            }
        }

        // Извеждане на протоколите
        for (HashMap.Entry<Specialty, HashMap<Byte, List<Student>>> entry : protocolMap.entrySet()) {
            Specialty specialty = entry.getKey();
            HashMap<Byte, List<Student>> courseMap = entry.getValue();

            for (HashMap.Entry<Byte, List<Student>> courseEntry : courseMap.entrySet()) {
                int course = courseEntry.getKey();
                List<Student> studentList = courseEntry.getValue();

                // Подреждане по факултетен номер

                studentList.sort(Comparator.comparingInt(s -> Integer.parseInt(s.getFacultyNumber())));
                System.out.println("\n------------------");
                System.out.println("Протокол за специалност: " + specialty.getName() +
                        ", курс: " + course +
                        ", дисциплина: " + discipline.getName());
                System.out.println("------------------");

                for (Student s : studentList) {
                    System.out.print(".");
                    System.out.println(s);
                }System.out.print(".");
            }
        }
        System.out.println("\n------------------");
        return "Извеждането на протоколи приключи.";
    }

    public String report(String facultyNumber) {
        StringBuilder msg = new StringBuilder();
        for (Student s : students) {
            if (s.getFacultyNumber().equals(facultyNumber)) {
                System.out.println("Извеждане справка на студент с факултетен номер и име: " + facultyNumber +" "+s.getFullName());
                System.out.println("Оценки на студентът:");
                for (HashMap.Entry<Discipline, List<Integer>> entry : s.getDisciplineGrades().entrySet()) {
                    msg.append(entry.getKey().getName()).append(": ").append(entry.getValue().toString());
                    msg.append("\n");
                }
            }
        }
        if(msg.isEmpty())
        {
            return ("Студентът с факултетен номер " + facultyNumber + " не е намерен!");
        }
        return msg.toString();
    }
}
