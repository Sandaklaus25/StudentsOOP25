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

    // <editor-fold desc="Getters and Setters">
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
    // </editor-fold>

    // <editor-fold desc="User Actions">

    public void RemoveStudentDiscipline(Discipline discipline) {
        disciplineGrades.remove(discipline);
    }

    public void RemoveGradeFromStudentDiscipline(Discipline discipline, Integer grade) {
        List<Integer> grades = disciplineGrades.get(discipline);
        if (grades != null) {
            grades.remove(grade);
        }
    }

    public double averageCalc(HashMap.Entry<Discipline, List<Integer>> disciplineGrades) {
        double sum = 0;

        for(Integer grade : disciplineGrades.getValue())
        {
            sum += grade;
        }
        if(sum == 0) return 2;
        return sum/disciplineGrades.getValue().size();
    }

    public double averageCalcAll() {
        double sum = 0;
        for(HashMap.Entry<Discipline, List<Integer>> entry : disciplineGrades.entrySet())
        {
            sum+=averageCalc(entry);
        }
        if(sum == 0) return 2;
        return sum/disciplineGrades.size();
    }

    public boolean meetsPassingThresholdForDiscipline(HashMap.Entry<Discipline, List<Integer>> disciplineGrades) {
        return averageCalc(disciplineGrades)>=3;
    }

    public boolean hasPassedRequiredSubjectsWithMaxTwoCourses() {
        int failedCoursesCount = 0;
        HashMap<Discipline, List<Byte>> allDisciplineCourses = specialty.getDisciplineCourses();

        for (HashMap.Entry<Discipline, List<Byte>> entry : allDisciplineCourses.entrySet()) {
            Discipline discipline = entry.getKey();
            List<Byte> courses = entry.getValue();

            if (!discipline.getIsMandatory()) continue;

            Collections.sort(courses);

            boolean isFromPreviousCourse = false;
            for (Byte courseNumber : courses) {
                if (courseNumber < this.course) {
                    isFromPreviousCourse = true;
                    break;
                }
            }

            if (!isFromPreviousCourse) continue;

            List<Integer> grades = disciplineGrades.getOrDefault(discipline, new ArrayList<>());

            boolean passed = !grades.isEmpty() && averageCalc(Map.entry(discipline, grades)) >= 3;

            if (!passed) {
                failedCoursesCount++;
            }
        }

        return failedCoursesCount <= 2;
    }

    public boolean hasPassedRequiredSubjects() {
        for (HashMap.Entry<Discipline, List<Integer>> entry : disciplineGrades.entrySet()) {
            Discipline discipline = entry.getKey();
            List<Integer> grades = entry.getValue();

            if (discipline.getIsMandatory() && grades.isEmpty()) {
                return false;
            }

            if (discipline.getIsMandatory() && !meetsPassingThresholdForDiscipline(entry)) {
                return false;
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

            if (meetsPassingThresholdForDiscipline(entry)) {
                return false;
            }
        }
        return true;
    }

    public void setCourseUp() {
        if (getDisciplineGrades().isEmpty()) {
            throw new IllegalStateException("Студентът няма записани дисциплини и не може да премине.");
        }


        if (!hasPassedRequiredSubjects()) {
            throw new IllegalStateException("Студентът има дисциплини без оценки или не е преминал всички задължителни дисциплини.");
        }


        for (HashMap.Entry<Discipline, List<Integer>> entry : getDisciplineGrades().entrySet()) {
            Discipline discipline = entry.getKey();
            List<Byte> courses = specialty.getDisciplineCourses().get(discipline);

            if (courses != null && courses.stream().anyMatch(courseNumber -> courseNumber < this.course)) {
                RemoveStudentDiscipline(discipline);
            }
        }

        this.course++;
        updateAverageGrade();
    }

    // </editor-fold>

    protected Student(String fullName, String facultyNumber, byte course, Specialty specialty, char[] group) {
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
    public String toStringAlternative()
    {
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
