package Models;

import java.util.*;

public class Student {
    private final String fullName;
    private final String facultyNumber;
    private Specialty specialty;
    private Byte course;
    private char[] group;
    private StudentStatus status;
    private double averageGrade;

    private final HashMap<Discipline, List<Integer>> disciplineGrades = new HashMap<>();

    public String getFullName() {
        return fullName;
    }

    public String getFacultyNumber() {
        return facultyNumber;
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

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public void updateAverageGrade() {
        averageGrade = averageCalcAll();
    }

    public double getAverageGrade() {
        updateAverageGrade();
        return averageGrade;
    }

    public HashMap<Discipline, List<Integer>> getDisciplineGrades() {
        return disciplineGrades;
    }

    public char[] getGroup() {
        return group;
    }

    public void setGroup(char[] group) {
        this.group = group;
    }

    public void RemoveStudentDiscipline() {
        disciplineGrades.clear();
    }

    public void RemoveGradeFromStudentDiscipline(Discipline discipline, Integer grade) {
        List<Integer> grades = disciplineGrades.get(discipline);
        if (grades != null) {
            grades.remove(grade);
        }
    }

    public double averageCalc(HashMap.Entry<Discipline, List<Integer>> disciplineGrades) {
        if(disciplineGrades == null || disciplineGrades.getValue() == null) {
            return 2;
        }
        double sum = 0;

        for(Integer grade : disciplineGrades.getValue()) {
            sum += grade;
        }
        return sum/disciplineGrades.getValue().size();
    }

    public double averageCalcAll() {
        double sum = 0;
        int count = 0;
        for(HashMap.Entry<Discipline, List<Integer>> entry : disciplineGrades.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                sum += averageCalc(entry);
                count++;
            }
        }
        if(count == 0) return 2;
        return sum/count;
    }

    public boolean meetsPassingThresholdForDiscipline(HashMap.Entry<Discipline, List<Integer>> disciplineGrades) {
        return averageCalc(disciplineGrades) >= 3;
    }

    public boolean hasPassedRequiredSubjectsWithMaxTwoCoursesFails() {
        int failedCoursesCount = 0;
        Map<Discipline, List<Byte>> allDisciplineCourses = specialty.getDisciplineCourses();

        for (Map.Entry<Discipline, List<Byte>> entry : allDisciplineCourses.entrySet()) {
            Discipline discipline = entry.getKey();
            List<Byte> courses = entry.getValue();

            if (!discipline.isMandatory()) continue;

            for (Byte disciplineCourse : courses) {
                if (disciplineCourse >= this.course) continue;

                List<Integer> grades = disciplineGrades.get(discipline);

                if (grades == null) {
                    grades = new ArrayList<>();
                }
                boolean passed = !grades.isEmpty() && meetsPassingThresholdForDiscipline(Map.entry(discipline, grades));

                if (!passed) {
                    failedCoursesCount++;
                    break;
                }
            }
        }

        return (failedCoursesCount <= 2);
    }

    public boolean hasPassedRequirementsSubjectsForSpecialty(Specialty specialty) {
        for (Map.Entry<Discipline, List<Byte>> entry : specialty.getDisciplineCourses().entrySet()) {
            Discipline discipline = entry.getKey();
            List<Byte> requiredCourses = entry.getValue();

            if (discipline.isMandatory()) {
                for (Byte requiredCourse : requiredCourses) {
                    if (this.course > requiredCourse) {
                        List<Integer> grades = getDisciplineGrades().get(discipline);

                        if (grades == null || grades.isEmpty()) {
                            return false;
                        }

                        Map.Entry<Discipline, List<Integer>> studentEntry =
                                new AbstractMap.SimpleEntry<>(discipline, grades);

                        if (!meetsPassingThresholdForDiscipline(studentEntry)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean hasPassedAllSubjects() {
        for (HashMap.Entry<Discipline, List<Integer>> entry : disciplineGrades.entrySet()) {
            List<Integer> grades = entry.getValue();

            if (grades.isEmpty()) {
                return false;
            }
            if (!meetsPassingThresholdForDiscipline(entry)) {
                return false;
            }
        }

        return true;
    }
    public void setCourseUp() {
        if (getDisciplineGrades().isEmpty()) {
            throw new IllegalStateException("Студентът няма записани дисциплини и не може да премине.");
        }

        for (Map.Entry<Discipline, List<Integer>> entry : getDisciplineGrades().entrySet()) {
            Discipline discipline = entry.getKey();
            List<Byte> courses = specialty.getDisciplineCourses().get(discipline);

            if (courses != null
                    && courses.contains(this.course)
                    && !meetsPassingThresholdForDiscipline(entry))
            {
                entry.getValue().clear();
            }
        }
            this.course++;
            setUpCourseDisciplines();
            updateAverageGrade();
    }

    public void removeEmptyOldDisciplines(Specialty newSpecialty)
    {
        Iterator<Map.Entry<Discipline, List<Integer>>> iterator = disciplineGrades.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Discipline, List<Integer>> entry = iterator.next();
            Discipline discipline = entry.getKey();

            if (!discipline.isMandatory()) continue;

            if (!newSpecialty.getDisciplineCourses().containsKey(discipline)
                    && entry.getValue().isEmpty()) {
                iterator.remove();
                System.out.println("Дисциплината " + discipline.getName() + " бе премахната за студент.");
            }
        }

        updateAverageGrade();
    }

    public void setUpCourseDisciplines() {
        for(HashMap.Entry<Discipline, List<Byte>> entry : specialty.getDisciplineCourses().entrySet()) {
            Discipline discipline = entry.getKey();
            List<Byte> courses = entry.getValue();

            if(!discipline.isMandatory()) continue;
            if(courses.contains(this.course) && !getDisciplineGrades().containsKey(discipline)) {
                StudentsManager.getInstance().enrollIn(facultyNumber, discipline);
                System.out.println("Дисциплината "+discipline.getName()+" бе добавена за студент.");
            }
        }
    }

    public void addDisciplineGrades(Discipline discipline, List<Integer> grades) {
        if (discipline == null) {
            throw new IllegalArgumentException("Дисциплината не може да бъде празна!");
        }

        if (grades == null) {
            disciplineGrades.put(discipline, new ArrayList<>());
        } else {
            List<Integer> existingGrades = disciplineGrades.getOrDefault(discipline, new ArrayList<>());
            existingGrades.addAll(grades);
            disciplineGrades.put(discipline, existingGrades);
        }

        updateAverageGrade();
    }

    public Student(String fullName, String facultyNumber, byte course, Specialty specialty, char[] group) {
        this.fullName = fullName;
        this.facultyNumber = facultyNumber;
        this.course = course;
        this.specialty = specialty;
        this.group = group;
        status = StudentStatus.active;
        averageGrade = 2;
    }

    @Override
    public String toString() {
        updateAverageGrade();
        return "\nИме: " + fullName +
                " | ФН: " + facultyNumber +
                " | Специалност: " + specialty.getName() +
                " | Курс: " + course +
                " | Група: " + group[0]+group[1] +
                " | Статус: " + status +
                " | Среден успех: " + String.format("%.2f", getAverageGrade());
    }

    public String toStringAlternative() {
        updateAverageGrade();
        return  "\nИме: " + getFullName() + "\n" +
                "ФН: " + getFacultyNumber() + "\n" +
                "Специалност: " + getSpecialty().getName() + "\n" +
                "Курс: " + getCourse() + "\n" +
                "Група: " + group[0]+group[1] + "\n" +
                "Статус: " + getStatus() + "\n" +
                "Среден успех: " + String.format("%.2f", getAverageGrade());
    }
}
