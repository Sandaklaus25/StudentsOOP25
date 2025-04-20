import java.util.*;

public class Student {
    private String fullName;
    private int facultyNumber;
    private Specialty specialty;
    private Byte course;
    private char group;
    private StudentStatus status;
    private double averageGrade;
    private static List<Student> students = new ArrayList<>();
    private Map<Discipline, List<Integer>> disciplineGrades = new HashMap<>();

    // <editor-fold desc="Getters and Setters">
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getFacultyNumber() {
        return facultyNumber;
    }

    public void setFacultyNumber(int facultyNumber) {
        this.facultyNumber = facultyNumber;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public Byte getCourse() {
        return course;
    }

    public void setCourse(Byte course) {
        this.course = course;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public double getAverageGrade() {
        return averageGrade;
    }
    public Map<Discipline, List<Integer>> getDisciplineGrade() {
        return disciplineGrades;
    }

    public void setDisciplineGrade(Map<Discipline, List<Integer>> disciplineGrades) {
        this.disciplineGrades = disciplineGrades;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }
    // </editor-fold>

    private boolean hasPassedRequiredSubjectsWithMaxTwoFails() {
        return false;
    }
    private boolean hasPassedRequiredSubjects() {
        return false;
    }
    private boolean hasPassedRequirementsForSpecialty(Specialty specialty) {
        return false;
    }

    // <editor-fold desc="User Actions">
    public void AddDisciplineToStudent(Discipline discipline) {
        disciplineGrades.put(discipline, null);
    }

    public void RemoveStudentDiscipline(Discipline discipline) {
        disciplineGrades.remove(discipline);
    }

    public void AddGradesToStudent(Discipline discipline, List<Integer> grades) {
        disciplineGrades.put(discipline, grades);
    }

    public void RemoveGradeFromStudentDiscipline(Discipline discipline, Integer grade) {
        List<Integer> grades = disciplineGrades.get(discipline);
        if (grades != null) {
            grades.remove(grade);
        }
    }

    public static Student enroll(int facultyNumber, Specialty specialty, char group, String fullName) {
        Student s = new Student(fullName, facultyNumber, (byte)1, specialty, group);
        students.add(s);
        return s;
    }

    public static boolean advance(int facultyNumber)
    {
        for(Student s : students)
        {
            if(s.facultyNumber==facultyNumber && s.status!=StudentStatus.прекъснал)
            {
                s.course++;
                return true;
            }
        }
        return false;
    }
    public static boolean change(int facultyNumber, String option, String value) {   //maybe add some text for false statements
        for (Student s : students) {
            if (s.facultyNumber == facultyNumber  && s.status!=StudentStatus.прекъснал) {
                switch (option.toLowerCase()) {
                    case "group":
                        s.group = value.charAt(0);
                        return true;

                    case "year":
                        int nextYear = s.getCourse() + 1;
                        int requestedYear = Integer.parseInt(value);
                        if (requestedYear == nextYear && s.hasPassedRequiredSubjectsWithMaxTwoFails()) {
                            s.setCourse((byte) requestedYear);
                            return true;
                        }
                        return false;

                    case "program":
                        Specialty newSpecialty = Specialty.getSpecialtyByString(value);
                        if (s.hasPassedRequirementsForSpecialty(newSpecialty)) {
                            s.setSpecialty(newSpecialty);
                            return true;
                        }
                        return false;

                    default:
                        return false;
                }
            }
        }
        return false;
    }


    public static boolean graduate(int facultyNumber)
    {
        for (Student s : students)
        {
            if(s.facultyNumber==facultyNumber && s.hasPassedRequiredSubjects())
            {
                    s.status = StudentStatus.завършил;
                    System.out.println("Честито дипломиране на студента!");
                    return true;
            }
            else if(s.facultyNumber==facultyNumber && !s.hasPassedRequiredSubjects())
            {
                System.out.println("Студента не е положил успешно изпити и не може да се дипломира!");
                return true;
            }
        }
        System.out.println("Студента не е намерен!");
        return false;
    }

    public static void interrupt(int facultyNumber)
    {
        for (Student s : students)
        {
            if(s.facultyNumber==facultyNumber)
            {
                s.status = StudentStatus.прекъснал;
                System.out.println("Студента е обновен като прекъснал!");
            }
        }
        //System.out.println("Студента не е намерен!");
    }

    public static void resume(int facultyNumber)
    {
        for (Student s : students)
        {
            if(s.facultyNumber==facultyNumber)
            {
                s.status = StudentStatus.записан;
                System.out.println("Студента е обновен като записан/продължаващ!");
            }
        }
        //System.out.println("Студента не е намерен!");
    }

    public static void print(int facultyNumber)
    {
        for (Student s : students)
        {
            if(s.facultyNumber==facultyNumber)
            {
                System.out.println("Извеждане справка на студент с факултетен номер: "+facultyNumber);
                System.out.println(s.toString());
            }
        }
        //System.out.println("Студента не е намерен!");
    }

    public static void printAll(Specialty specialty, Byte course)
    {
        for (Student s : students)
        {
            if(s.specialty==specialty && s.course.equals(course))
            {
                System.out.println("Извеждане справка на всички студенти от специалност и курс: "+specialty.getName()+" "+course);
                System.out.println(s.toString());
            }
        }
    }

    public static void enrollIn(int facultyNumber, Discipline course) {
        for (Student s : students) {
            if(s.facultyNumber==facultyNumber
                    && s.getSpecialty().getDisciplineCourses().containsKey(course)
                    && s.getSpecialty().getDisciplineCourses().get(course).contains(s.getCourse())
                    && s.status!=StudentStatus.прекъснал)
            {
                s.disciplineGrades.putIfAbsent(course, new ArrayList<>());
                System.out.println("Дисциплина добавена.");
            }
            else if(s.facultyNumber==facultyNumber && s.status!=StudentStatus.прекъснал)
            {
                System.out.println("Дисциплина не може да бъде добавена за студента.");
            }
        }
        //System.out.println("Студента не е намерен");
    }

    public static boolean addGrade(int facultyNumber, Discipline discipline, Integer grade)
    {
        for (Student s : students) {
            if(s.facultyNumber==facultyNumber
                    && s.disciplineGrades.containsKey(discipline)
                    && s.status!=StudentStatus.прекъснал)
            {
                s.disciplineGrades.get(discipline).add(grade);
            }
        }
        return false;
    }

    public static void protocol(Discipline discipline) {
        // Карта: Specialty -> (Курс -> Списък със студенти)
        Map<Specialty, Map<Byte, List<Student>>> protocolMap = new HashMap<>();

        for (Student s : students) {
            if (s.disciplineGrades.containsKey(discipline)) {   // Проверка дали студентът учи дисциплината
                protocolMap
                        .computeIfAbsent(s.specialty, k -> new HashMap<>()) // Създаване на поле за специалност в картата ако няма
                        .computeIfAbsent(s.course, k -> new ArrayList<>()) // Създаване на поле за курс в картата ако няма
                        .add(s);    // Добавяне на студент в списъка
            }
        }

        // Извеждане на протоколите
        for (Map.Entry<Specialty, Map<Byte, List<Student>>> entry : protocolMap.entrySet()) {
            Specialty specialty = entry.getKey();
            Map<Byte, List<Student>> courseMap = entry.getValue();

            for (Map.Entry<Byte, List<Student>> courseEntry : courseMap.entrySet()) {
                int course = courseEntry.getKey();
                List<Student> studentList = courseEntry.getValue();

                // Подреждане по факултетен номер
                studentList.sort(Comparator.comparingInt(s -> s.facultyNumber));

                System.out.println("Протокол за специалност: " + specialty.getName() +
                        ", курс: " + course +
                        ", дисциплина: " + discipline.GetName());

                for (Student s : studentList) {
                    System.out.println(s);
                }

                System.out.println("----------");
            }
        }
    }

    // </editor-fold>
    public Student(String fullName, int facultyNumber, byte course, Specialty specialty, char group) {
        this.fullName = fullName;
        this.facultyNumber = facultyNumber;
        this.course = course;
        this.specialty = specialty;
        this.group = group;
        status = StudentStatus.записан;
        students.add(this);
    }

    @Override
    public String toString() {
        return "Студент { Име: " + fullName +
                " | ФН: " + facultyNumber +
                " | Специалност: " + specialty.getName() +
                " | Курс: " + course +
                " | Група: " + group +
                " | Статус: " + status +
                " | Среден успех: " + String.format("%.2f", averageGrade) + " }";
    }

}
