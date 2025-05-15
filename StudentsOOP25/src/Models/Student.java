package Models;

import java.util.*;

public class Student {
    private final String fullName;
    private final int facultyNumber;
    private Specialty specialty;
    private Byte course;
    private char group;
    private StudentStatus status;
    private double averageGrade;

    private final HashMap<Discipline, List<Integer>> disciplineGrades = new HashMap<>();

    // <editor-fold desc="Getters and Setters">
    public String getFullName() {
        return fullName;
    }

    public int getFacultyNumber() {
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

    public char getGroup() {
        return group;
    }

    public void setGroup(char group) {
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
        if(disciplineGrades.getValue().isEmpty())
        {
            return 2;
        }
        for(Integer grade : disciplineGrades.getValue())
        {
            sum += grade;
        }
        return sum/disciplineGrades.getValue().size();
    }

    public double averageCalcAll() {
        double sum = 0;
        for(HashMap.Entry<Discipline, List<Integer>> entry : disciplineGrades.entrySet())
        {
            sum+=averageCalc(entry);
        }
        return sum/disciplineGrades.size();
    }

    public boolean meetsPassingThresholdForDiscipline(HashMap.Entry<Discipline, List<Integer>> disciplineGrades) {
        return averageCalc(disciplineGrades)>=3;
    }

    public void setCourseUp() {
        for (HashMap.Entry<Discipline, List<Integer>> entry : getDisciplineGrades().entrySet())
        {
            if(!entry.getValue().isEmpty() && meetsPassingThresholdForDiscipline(entry))
            {
                RemoveStudentDiscipline(entry.getKey());
            }
            else
            {
                entry.getValue().clear();
            }
        }
        this.course++;
        updateAverageGrade();
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
    }   //temporary unstable

    public boolean hasPassedRequiredSubjects() {
        for(HashMap.Entry<Discipline, List<Integer>> entry : disciplineGrades.entrySet()) {
            if(entry.getValue().isEmpty()
                    && entry.getKey().getIsMandatory()
                    && meetsPassingThresholdForDiscipline(entry))
            {
                return false;
            }
        }
        return true;
    }

    public boolean hasPassedAllSubjects() {
        for(HashMap.Entry<Discipline, List<Integer>> entry : disciplineGrades.entrySet()) {
            if(entry.getValue().isEmpty()
                    && meetsPassingThresholdForDiscipline(entry))
            {
                return false;
            }
        }
        return true;
    }
    // </editor-fold>

    protected Student(String fullName, int facultyNumber, byte course, Specialty specialty, char group) {
        this.fullName = fullName;
        this.facultyNumber = facultyNumber;
        this.course = course;
        this.specialty = specialty;
        this.group = group;
        status = StudentStatus.записан;
    }

    @Override
    public String toString() {
        updateAverageGrade();
        return "Име: " + fullName +
                " | ФН: " + facultyNumber +
                " | Специалност: " + specialty.getName() +
                " | Курс: " + course +
                " | Група: " + group +
                " | Статус: " + status +
                " | Среден успех: " + String.format("%.2f", averageGrade);
    }
    public String toStringAlternative()
    {
        updateAverageGrade();
        return  "Име: " + getFullName() + "\n" +
                "ФН: " + getFacultyNumber() + "\n" +
                "Специалност: " + getSpecialty().getName() + "\n" +
                "Курс: " + getCourse() + "\n" +
                "Група: " + getGroup() + "\n" +
                "Статус: " + getStatus() + "\n" +
                "Среден успех: " + String.format("%.2f", getAverageGrade());
    }
}
