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


    public void enroll(int facultyNumber, Specialty specialty, char group, String fullName) {
        Student s = new Student(fullName, facultyNumber, (byte)1, specialty, group);
        students.add(s);
        System.out.println("Студентът " + s.getFullName() +" е успешно записан.");
    }

    public void advance(int facultyNumber) {
        for(Student s : students)
        {
            if(s.getFacultyNumber()==facultyNumber && s.getStatus()== StudentStatus.записан)
            {
                s.setCourseUp();
                System.out.println("Студентът успешно е преминал курса.");
                return;
            }
            else if(s.getFacultyNumber()==facultyNumber && s.getStatus()!= StudentStatus.записан)
            {
                System.out.println("Студентът трябва да е в активен статус.");
                return;
            }
        }
        System.out.println("Студентът не е намерен!");
    }

    public void change(int facultyNumber, String option, String value) {
        for (Student s : students) {
            if (s.getFacultyNumber() == facultyNumber  && s.getStatus()== StudentStatus.записан) {
                switch (option.toLowerCase()) {
                    case "group":
                        s.setGroup(value.charAt(0));
                        System.out.println("Групата на студента успешно бе сменена.");
                        return;
                    case "year":
                        if (Byte.parseByte(value) == s.getCourse()+1 && s.hasPassedRequiredSubjectsWithMaxTwoCourses()) {
                            advance(facultyNumber);
                        }
                        return;
                    case "program":
                        if (s.hasPassedRequiredSubjects()) {
                            s.setSpecialty(SpecialtyManager.getSpecialtyByString(value));
                            System.out.println("Успешно преминаване към друга специалност.");
                        }
                        else System.out.println("Студентът не може да преминаване към друга специалност поради оценките си.");
                        return;
                    default:
                        System.out.println("Невалиден вход!");
                        return;
                }
            }
        }
        System.out.println("Нещо се обърка!");
    }

    public void graduate(int facultyNumber) {
        for (Student s : students)
        {
            if(s.getFacultyNumber()==facultyNumber && s.hasPassedAllSubjects())
            {
                s.setStatus(StudentStatus.завършил);
                System.out.println("Честито дипломиране на студентът!");
                return;
            }
            else if(s.getFacultyNumber()==facultyNumber && !s.hasPassedAllSubjects())
            {
                System.out.println("Студентът не е положил успешно някои изпити и не може да се дипломира!");
                return;
            }
            else if(s.getFacultyNumber()==facultyNumber && s.getStatus()!= StudentStatus.записан)
            {
                System.out.println("Студентът трябва да е в активен статус.");
                return;
            }
        }
        System.out.println("Студентът не е намерен!");
    }

    public void interrupt(int facultyNumber) {
        for (Student s : students)
        {
            if(s.getFacultyNumber()==facultyNumber)
            {
                s.setStatus(StudentStatus.прекъснал);
                System.out.println("Студентът е обновен като прекъснал!");
                return;
            }
        }
        System.out.println("Студентът не е намерен!");
    }

    public void resume(int facultyNumber) {
        for (Student s : students)
        {
            if(s.getFacultyNumber()==facultyNumber)
            {
                s.setStatus(StudentStatus.записан);
                System.out.println("Студентът е обновен като записан/продължаващ!");
                return;
            }
        }
        System.out.println("Студентът не е намерен!");
    }

    public void print(int facultyNumber) {
        for (Student s : students)
        {
            if(s.getFacultyNumber()==facultyNumber)
            {
                System.out.println("Извеждане справка на студент с факултетен номер: "+facultyNumber);
                System.out.println(s.toStringAlternative());
                return;
            }
        }
        System.out.println("Студентът не е намерен!");
    }

    public void printAll(Specialty specialty, Byte course) {
        for (Student s : students)
        {
            if(s.getSpecialty()==specialty && s.getCourse().equals(course))
            {
                System.out.println("Извеждане справка на всички студенти от специалност "+specialty.getName()+" и курс "+course+":");
                System.out.println(s);
            }
        }
    }

    public void enrollIn(int facultyNumber, Discipline course) {
        for (Student s : students) {
            if(s.getFacultyNumber()==facultyNumber
                    && s.getSpecialty().getDisciplineCourses().containsKey(course)
                    && s.getSpecialty().getDisciplineCourses().get(course).contains(s.getCourse())
                    && s.getStatus()== StudentStatus.записан)
            {
                s.getDisciplineGrades().putIfAbsent(course, new ArrayList<>());
                s.updateAverageGrade();
                System.out.println("Дисциплина добавена.");
                return;
            }
            else if(s.getFacultyNumber()==facultyNumber)
            {
                System.out.println("Дисциплината не може да бъде добавена за студента.");
                return;
            }
        }
        System.out.println("Студентът не е намерен!");
    }

    public void addGrade(int facultyNumber, Discipline discipline, Integer grade) {
        if(grade<2||grade>6) {
            System.out.println("Невалиден вход за оценка! Оценката трябва да е число между 2 и 6!");
            return;
        }
        else if(grade == 2)
        {
            System.out.println("Студента не е издържал изпита! Оценката НЕ БЕ запазена и той трябва да повтаря!");
            return;
        }
        for (Student s : students) {
            if(s.getFacultyNumber()==facultyNumber && s.getStatus()== StudentStatus.записан)
            {
                if(s.getDisciplineGrades().containsKey(discipline))
                {
                    s.getDisciplineGrades().get(discipline).add(grade);
                    s.updateAverageGrade();
                    System.out.println("Оценка успешно добавена.");
                }
                else System.out.println("Дисциплината не може да бъде намерена!");
                return;
            }
        }
        System.out.println("Студентът не е намерен!");
    }

    public void protocol(Discipline discipline) {
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
                studentList.sort(Comparator.comparingInt(Student::getFacultyNumber));

                System.out.println("Протокол за специалност: " + specialty.getName() +
                        ", курс: " + course +
                        ", дисциплина: " + discipline.getName());

                for (Student s : studentList) {
                    System.out.println(s);
                }

                System.out.println("----------");
            }
        }
    }

    public void report(int facultyNumber) {
        for (Student s : students) {
            if (s.getFacultyNumber() == facultyNumber) {
                System.out.println("Извеждане справка на студент с факултетен номер и име: " + facultyNumber +" "+s.getFullName());
                System.out.println("Оценки на студентът:");
                for (HashMap.Entry<Discipline, List<Integer>> entry : s.getDisciplineGrades().entrySet()) {
                    System.out.println(entry.getKey().getName() + ": " + entry.getValue().toString());
                }
                return;
            }
        }
    }

    public void clear() {
        students.clear();
    }
}
