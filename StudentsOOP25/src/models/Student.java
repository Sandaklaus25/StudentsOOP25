package models;

import java.util.*;
/**
 * Represents a student with a name, faculty number unique identifier,
 * Specialty of the student
 * @see Specialty
 * Academic year/course of the student, group,
 * His current status - ACTIVE, INTERRUPTED, GRADUATED
 * @see StudentStatus
 * and his average grade based on calculations.
 */
public class Student {
    private final String fullName;
    private final String facultyNumber;
    private Specialty specialty;
    private Byte course;
    private char[] group;
    private StudentStatus status;
    private double averageGrade;
    /**
     * A hashmap storing grades of the student of his disciplines
     */
    private final HashMap<Discipline, List<Integer>> disciplineGrades = new HashMap<>();
    /**
     * Returns the full name of the student.
     *
     * @return the student's full name
     */
    public String getFullName() {
        return fullName;
    }
    /**
     * Returns the faculty number of the student.
     *
     * @return the faculty number
     */
    public String getFacultyNumber() {
        return facultyNumber;
    }
    /**
     * Returns the specialty the student is enrolled in.
     *
     * @return the student's specialty
     */
    public Specialty getSpecialty() {
        return specialty;
    }
    /**
     * Sets the student's specialty.
     *
     * @param specialty the new specialty to set
     */
    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }
    /**
     * Returns the current course (year) of the student.
     *
     * @return the course number
     */
    public Byte getCourse() {
        return course;
    }
    /**
     * Returns the current status of the student.
     *
     * @return the student status
     */
    public StudentStatus getStatus() {
        return status;
    }
    /**
     * Sets the student's status.
     *
     * @param status the new student status
     */
    public void setStatus(StudentStatus status) {
        this.status = status;
    }
    /**
     * Updates the student's average grade based on all discipline grades.
     */
    public void updateAverageGrade() {
        averageGrade = averageCalcAll();
    }
    /**
     * Returns the student's current average grade.
     * This method updates the average grade before returning it.
     *
     * @return the average grade
     */
    public double getAverageGrade() {
        updateAverageGrade();
        return averageGrade;
    }
    /**
     * Returns a map of disciplines to their respective grades for this student.
     *
     * @return a map with disciplines as keys and list of grades as values
     */
    public HashMap<Discipline, List<Integer>> getDisciplineGrades() {
        return disciplineGrades;
    }
    /**
     * Returns the group identifier of the student.
     *
     * @return the group as a char array
     */
    public char[] getGroup() {
        return group;
    }
    /**
     * Sets the group identifier of the student.
     *
     * @param group the group as a char array
     */
    public void setGroup(char[] group) {
        this.group = group;
    }
    /**
     * Removes all disciplines and their grades for this student.
     */
    public void RemoveStudentDisciplinesGrades() {
        disciplineGrades.clear();
    }
    /**
     * Removes a specific grade from a given discipline for this student.
     *
     * @param discipline the discipline from which to remove the grade
     * @param grade      the grade to remove
     */
    public void RemoveGradeFromStudentDiscipline(Discipline discipline, Integer grade) {
        List<Integer> grades = disciplineGrades.get(discipline);
        if (grades != null) {
            grades.remove(grade);
        }
    }
    /**
     * Calculates the average grade for a specific discipline.
     * If the discipline grades are null or empty, returns 2 as default.
     *
     * @param disciplineGrades a map entry of discipline and its grades
     * @return the average grade for the discipline
     */
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
    /**
     * Calculates the average grade across all disciplines for the student.
     * If no grades are present, returns 2 as default.
     *
     * @return the average grade over all disciplines
     */
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
    /**
     * Checks if the student meets the passing threshold (>=3) for a specific discipline.
     *
     * @param disciplineGrades a map entry of discipline and its grades
     * @return true if passing threshold met, false otherwise
     */
    public boolean meetsPassingThresholdForDiscipline(HashMap.Entry<Discipline, List<Integer>> disciplineGrades) {
        return averageCalc(disciplineGrades) >= 3;
    }
    /**
     * Checks if the student has passed all mandatory subjects with no more than two course failures.
     *
     * @return true if passed within threshold, false otherwise
     */
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
    /**
     * Checks if the student has passed all required subjects for a given specialty.
     *
     * @param specialty the specialty to check requirements against
     * @return true if all required subjects passed, false otherwise
     */
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
    /**
     * Checks if the student has passed all subjects they are enrolled in.
     *
     * @return true if all subjects passed, false otherwise
     */
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
    /**
     * Attempts to increase the student's course year.
     * Clears failing discipline grades for current course disciplines before incrementing.
     * Using another method sets up new disciplines for the student
     *
     * @return true if course was successfully increased, false otherwise
     */
    public boolean setCourseUp() {
        if (getDisciplineGrades().isEmpty()) {
            System.out.println("Student has no disciplines!");
            return false;
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
            return true;
    }
    /**
     * Removes old mandatory disciplines from the student's record if they are not
     * part of the new specialty and have no grades.
     *
     * @param newSpecialty the specialty to compare disciplines with
     */
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
                System.out.println("Discipline " + discipline.getName() + " has been removed for student.");
            }
        }

        updateAverageGrade();
    }
    /**
     * Enrolls the student in all mandatory disciplines for their current course that they are not already enrolled in.
     */
    public void setUpCourseDisciplines() {
        for(HashMap.Entry<Discipline, List<Byte>> entry : specialty.getDisciplineCourses().entrySet()) {
            Discipline discipline = entry.getKey();
            List<Byte> courses = entry.getValue();

            if(!discipline.isMandatory()) continue;
            if(courses.contains(this.course) && !getDisciplineGrades().containsKey(discipline)) {
                StudentsManager.getInstance().enrollIn(facultyNumber, discipline);
            }
        }
    }
    /**
     * Adds grades for a given discipline.
     * Validates that the discipline argument is not null.
     *
     * @param discipline the discipline to add grades for
     * @param grades     the list of grades to add; if null, initializes with an empty list
     * @throws IllegalArgumentException if discipline is null
     */
    public void addDisciplineGrades(Discipline discipline, List<Integer> grades) {
        Utility.validateArgsForNulls(discipline);

        if (grades == null) {
            disciplineGrades.put(discipline, new ArrayList<>());
        } else {
            List<Integer> existingGrades = disciplineGrades.getOrDefault(discipline, new ArrayList<>());
            existingGrades.addAll(grades);
            disciplineGrades.put(discipline, existingGrades);
        }

        updateAverageGrade();
    }
    /**
     * Constructs a new Student object.
     *
     * @param fullName      the full name of the student
     * @param facultyNumber the faculty number of the student
     * @param course        the current course (year) of the student
     * @param specialty     the specialty the student is enrolled in
     * @param group         the group identifier as a char array
     */
    public Student(String fullName, String facultyNumber, byte course, Specialty specialty, char[] group) {
        this.fullName = fullName;
        this.facultyNumber = facultyNumber;
        this.course = course;
        this.specialty = specialty;
        this.group = group;
        status = StudentStatus.ACTIVE;
        averageGrade = 2;
    }
    /**
     * Returns a string representation of the student, including key info and average grade.
     *
     * @return string with student's details formatted in a line
     */
    @Override
    public String toString() {
        updateAverageGrade();
        return ".\nИме: " + fullName +
                " | ФН: " + facultyNumber +
                " | Специалност: " + specialty.getName() +
                " | Курс: " + course +
                " | Група: " + group[0]+group[1] +
                " | Статус: " + status +
                " | Среден успех: " + String.format("%.2f", getAverageGrade());
    }
    /**
     * Returns an alternative, multi-line string representation of the student.
     *
     * @return string with student's details in multi-line format
     */
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
