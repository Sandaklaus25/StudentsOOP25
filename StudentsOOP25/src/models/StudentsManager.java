package models;

import java.util.*;

/**
 * A singleton class that manages a collection of students.
 * Provides methods to enroll, alter, delete and print check-up files (ex. report) and many more.
 */
public class StudentsManager {
    /** The singleton instance of {@code StudentsManager}. */
    private static final StudentsManager instance = new StudentsManager();
    /** The list of students managed by this class. */
    private final List<Student> students = new ArrayList<>();
    /**
     * Private constructor to enforce singleton pattern.
     */
    private StudentsManager() {}
    /**
     * Returns a copy of the list of all students currently managed.
     * @return List of students.
     */
    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }
    /**
     * Returns the singleton instance of StudentsManager.
     * @return StudentsManager instance.
     */
    public static StudentsManager getInstance() {
        return instance;
    }
    /**
     * Retrieves a student by their faculty number.
     * @param facultyNumber The unique faculty number of the student.
     * @return The Student object if found, or null and prints a message if not found.
     */
    public Student getStudentByFacultyNumber(String facultyNumber) {
        for (Student student : students) {
            if (student.getFacultyNumber().equals(facultyNumber)) {
                return student;
            }
        }
        System.out.println("Student with faculty number \"" + facultyNumber + "\" was not found!");
        return null;
    }
    /**
     * Enrolls a new student with the provided data.
     * @param facultyNumber The faculty number for the new student.
     *                      Must be Unique or prints a message.
     * @param specialty The specialty/program the student is enrolling in.
     * @param group The group identifier (char array of length 2, a digit followed by a letter).
     * @param fullName Full name of the student.
     * @return true if enrollment succeeded; false if student already exists or invalid data.
     */
    public boolean enroll(String facultyNumber, Specialty specialty, char[] group, String fullName) {
        Utility.validateArgsForNulls(facultyNumber,specialty,group,fullName);

        for (Student student : students) {
            if (student.getFacultyNumber().equals(facultyNumber)) {
                System.out.println("Student already exists");
                return false;
            }
        }

        Student s = new Student(fullName, facultyNumber, (byte) 1, specialty, group);
        students.add(s);
        System.out.println("Student " + s.getFullName() + " has been successfully enrolled.");
        s.setUpCourseDisciplines();


        return true;
    }
    /**
     * Advances a student to the next course/year.
     * @param facultyNumber The faculty number of the student to advance.
     * @return true if the student was advanced successfully, false otherwise.
     */
    public boolean advance(String facultyNumber) {
        Utility.validateArgsForNulls(facultyNumber);
        Student s = getStudentByFacultyNumber(facultyNumber);

        if(s==null) return false;
        if(!s.getStatus().isActive()) {
            System.out.println("Student must be in active status!");
            return false;
        }
        if(s.getCourse()==4)
        {
            System.out.println("Student is in his last year and has to graduate instead!");
            return false;
        }
        if(!s.setCourseUp())return false;

        System.out.println("Student successfully advanced. Failed exams, if any, remain on record.");
        return true;
    }
    /**
     * Adds a grade for a student in a specific discipline.
     * @param facultyNumber The faculty number of the student.
     * @param discipline The discipline for which the grade is given.
     * @param grade The grade to add (must be between 2 and 6 inclusive).
     * @return true if grade was added successfully; false otherwise.
     */
    public boolean addGrade(String facultyNumber, Discipline discipline, int grade) {
        Utility.validateArgsForNulls(facultyNumber,discipline,grade);

        if (!(grade > 1 && grade < 7)) {
            System.out.println("Grade must be between 2 and 6 inclusive!");
            return false;
        }

        Student s = getStudentByFacultyNumber(facultyNumber);

        if(s==null) {
            return false;
        }
        if(!s.getStatus().isActive()) {
            System.out.println("Student must be in active status to be graded!");
            return false;
        }
        if(!s.getDisciplineGrades().containsKey(discipline)) {
            System.out.println("The student is not enrolled in a discipline with that name!");
            return false;
        }

        s.getDisciplineGrades().get(discipline).add(grade);
        s.updateAverageGrade();
        System.out.println("Grade added successfully.");
        return true;
    }
    /**
     * Changes student information based on the given option and value.
     * @param facultyNumber The faculty number of the student.
     * @param option The option to change ("group", "year", or "program").
     * @param value The new value for the chosen option.
     * @return true if the change was successful; false otherwise.
     */
    public boolean change(String facultyNumber, String option, String value) {
        Utility.validateArgsForNulls(facultyNumber,option,value);
        Student s = getStudentByFacultyNumber(facultyNumber);
        if(s==null) return false;
        if (!s.getStatus().isActive()) {
            System.out.println("Student must be in active status to use change!");
            return false;
        }

        switch (option.toLowerCase()) {
            case "group":
                if(value.length()!=2
                        || !Character.isDigit(value.charAt(0))
                        || !Character.isLetter(value.charAt(1))) {
                    System.out.println("Group must be 2 characters long and must be starting with digit and ending with letter! Example: 2a");
                    return false;
                }

                char[] group = {value.charAt(0), value.charAt(1)};
                s.setGroup(group);
                System.out.println("Group changed successfully.");
                return true;

            case "year":
                byte newCourse;
                try {
                    newCourse = Byte.parseByte(value);
                } catch (NumberFormatException e) {
                    System.out.println("Year/course must be an integer!");
                    return false;
                }
                if (!s.hasPassedRequiredSubjectsWithMaxTwoCoursesFails()) {
                    System.out.println("Student has not passed enough required subjects to advance!");
                    return false;
                }
                if (newCourse != s.getCourse() + 1) {
                    System.out.println("Student can only advance to the next course/year!");
                    return false;
                }

                return advance(facultyNumber);

            case "program":
                    Specialty newSpecialty = SpecialtyManager.getSpecialtyByName(value);
                    if (newSpecialty == null) return false;
                    if (newSpecialty == s.getSpecialty())
                    {
                        System.out.println("Student is already in this specialty!");
                        return false;
                    }
                    if (!s.hasPassedRequirementsSubjectsForSpecialty(newSpecialty)) {
                        System.out.println("Student could not change program/specialty due to his disciplines and grades!");
                        return false;
                    }

                    s.removeEmptyOldDisciplines(newSpecialty);
                    s.setSpecialty(newSpecialty);
                    s.setUpCourseDisciplines();
                    System.out.println("Student successfully changed program/specialty.");
                    return true;

            default:
                System.out.println("Invalid option \"" + option + "\"! Option must be group, program or year!");
                return false;
        }
    }
    /**
     * Graduates a student if they meet the graduation criteria.
     * @param facultyNumber The faculty number of the student.
     * @return true if the student graduated successfully; false otherwise.
     */
    public boolean graduate(String facultyNumber) {
        Utility.validateArgsForNulls(facultyNumber);
        Student s = getStudentByFacultyNumber(facultyNumber);
        if(s==null) return false;
        if (!s.getStatus().isActive()) {
            System.out.println("Student must be in active status to use graduate!");
            return false;
        }
        if(s.getCourse() != 4)
        {
            System.out.println("The student must have passed all years/courses up to 4th (1-4)!");
            return false;
        }
        if (!s.hasPassedAllSubjects()) {
            System.out.println("Student has not passed all of his subjects and cannot graduate!");
            return false;
        }

        s.setStatus(StudentStatus.GRADUATED);
        System.out.println("Student has graduated. :)");
        return true;
    }
    /**
     * Interrupts (temporarily suspends) a student's status.
     * @param facultyNumber The faculty number of the student.
     * @return true if interruption was successful; false if student already interrupted or graduated.
     */
    public boolean interrupt(String facultyNumber) {
        Utility.validateArgsForNulls(facultyNumber);
        Student s = getStudentByFacultyNumber(facultyNumber);
        if(s==null) return false;

        if (s.getStatus().isInterrupted()) {
            System.out.println("Student was already interrupted!");
            return false;
        }
        else if(s.getStatus().hasGraduated()){
            System.out.println("Student has graduated and cannot be interrupted!");
            return false;
        }

        s.setStatus(StudentStatus.INTERRUPTED);
        System.out.println("Student has changed to interrupted. :(");
        return true;
    }
    /**
     * Resumes a student's status to active if currently interrupted.
     * @param facultyNumber The faculty number of the student.
     * @return true if resuming was successful; false otherwise.
     */
    public boolean resume(String facultyNumber) {
        Utility.validateArgsForNulls(facultyNumber);
        Student s = getStudentByFacultyNumber(facultyNumber);
        if(s==null) return false;

        if (s.getStatus().isActive()) {
            System.out.println("Student was already active!");
            return false;
        }
        else if(s.getStatus().hasGraduated()){  //can be adjusted to be able to resume but only if I had more data to work with. It is capped to 4 courses/years for now and there is no need for graduates to return
            System.out.println("Student has graduated and cannot be resumed!");
            return false;
        }

        s.setStatus(StudentStatus.ACTIVE);
        System.out.println("Student has been updated to active/resumed.");
        return true;
    }
    /**
     * Prints the details of a student identified by faculty number.
     * @param facultyNumber The faculty number of the student.
     * @return true if printing succeeded; false if student not found.
     */
    public boolean print(String facultyNumber) {
        Utility.validateArgsForNulls(facultyNumber);
        Student s = getStudentByFacultyNumber(facultyNumber);
        if(s==null) return false;

        System.out.println("Printing details for student with faculty number: "+facultyNumber+s.toStringAlternative());
        return true;
    }
    /**
     * Prints details for all students of a given specialty and course/year.
     * @param specialty The specialty to filter students.
     * @param course The course/year to filter students.
     * @return true always.
     */
    public boolean printAll(Specialty specialty, Byte course) {
        Utility.validateArgsForNulls(specialty,course);
        boolean found = false;

        System.out.println("\nPrinting details for ALL students of specialty "+specialty.getName()+" and year/course "+course+"...\n--------------------");
        for (Student s : students) {
            if(s.getSpecialty().equals(specialty) && s.getCourse().equals(course)) {
                System.out.println(s);
                found = true;
            }
        }
        System.out.println(".");
        System.out.println("--------------------\nEnd!");

        if (!found) {
            System.out.println("No students match the criteria, so none were printed.");
        }

        return true;
    }
    /**
     * Enrolls a student in a specific discipline.
     * @param facultyNumber The faculty number of the student.
     * @param discipline The discipline to enroll the student in.
     * @return true if enrollment succeeded; false otherwise.
     */
    public boolean enrollIn(String facultyNumber, Discipline discipline) {
        Utility.validateArgsForNulls(facultyNumber,discipline);
        Student s = getStudentByFacultyNumber(facultyNumber);
        if(s==null) return false;

        if(!s.getStatus().isActive()) {
            System.out.println("Student must be in active status to use enrollIn!");
            return false;
        }
        if (!s.getSpecialty().getDisciplineCourses().containsKey(discipline)) {
            System.out.println("Discipline is not included in the student's specialty!");
            return false;
        }
        if (!s.getSpecialty().getDisciplineCourses().get(discipline).contains(s.getCourse())) {
            System.out.println("Discipline unavailable for student in his course!");
            return false;
        }
        if(s.getDisciplineGrades().putIfAbsent(discipline, new ArrayList<>()) != null) {
            System.out.println("Student already in discipline. Nothing changed!");
            return false;
        }

        s.updateAverageGrade();
        System.out.println("Discipline "+discipline.getName()+" has been added successfully for this student.");
        return true;
    }
    /**
     * Prints a protocol report for a given discipline, grouping students by specialty and course.
     * @param discipline The discipline for which to print the protocol.
     * @return true always.
     */
    public boolean protocol(Discipline discipline) {
        Utility.validateArgsForNulls(discipline);

        HashMap<Specialty, HashMap<Byte, List<Student>>> protocolMap = buildProtocolMap(discipline);
        printProtocols(protocolMap, discipline);

        return true;
    }

    private HashMap<Specialty, HashMap<Byte, List<Student>>> buildProtocolMap(Discipline discipline) {
        HashMap<Specialty, HashMap<Byte, List<Student>>> protocolMap = new HashMap<>();

        for (Student student : students) {
            if (student.getDisciplineGrades().containsKey(discipline)) {
                student.updateAverageGrade();
                addStudentToProtocolMap(protocolMap, student);
            }
        }

        return protocolMap;
    }

    private void addStudentToProtocolMap(HashMap<Specialty, HashMap<Byte, List<Student>>> protocolMap, Student student) {
        Specialty specialty = student.getSpecialty();
        Byte course = student.getCourse();

        HashMap<Byte, List<Student>> courseMap = protocolMap.get(specialty);
        if (courseMap == null) {
            courseMap = new HashMap<>();
            protocolMap.put(specialty, courseMap);
        }

        List<Student> studentList = courseMap.get(course);
        if (studentList == null) {
            studentList = new ArrayList<>();
            courseMap.put(course, studentList);
        }

        studentList.add(student);
    }

    private void printProtocols(HashMap<Specialty, HashMap<Byte, List<Student>>> protocolMap, Discipline discipline) {
        List<Specialty> specialties = new ArrayList<>(protocolMap.keySet());
        Collections.sort(specialties, new SpecialtyComparator());

        for (Specialty specialty : specialties) {
            HashMap<Byte, List<Student>> courseMap = protocolMap.get(specialty);
            printSpecialtyProtocols(specialty,courseMap, discipline);
        }
        System.out.println("\n------------------");
        System.out.println("End!");
    }

    private void printSpecialtyProtocols(Specialty specialty, HashMap<Byte, List<Student>> courseMap, Discipline discipline) {
        List<Byte> sortedCourses = new ArrayList<>(courseMap.keySet());
        Collections.sort(sortedCourses);

        for (Byte course : sortedCourses) {
            List<Student> studentList = getStudentsSortByFacultyNumber(courseMap.get(course));
            printProtocolHeader(specialty, course, discipline);

            for (Student student : studentList) {
                System.out.println(student);
            }
            System.out.print(".");
        }
    }

    private List<Student> getStudentsSortByFacultyNumber(List<Student> students) {
        List<Student> studentList = new ArrayList<>(students);
        Collections.sort(studentList, new FacultyNumberComparator());
        return studentList;
    }

    private static class SpecialtyComparator implements Comparator<Specialty> {
        @Override
        public int compare(Specialty s1, Specialty s2) {
            return s1.getName().compareTo(s2.getName());
        }
    }

    private static class FacultyNumberComparator implements Comparator<Student> {
        @Override
        public int compare(Student s1, Student s2) {
            return s1.getFacultyNumber().compareTo(s2.getFacultyNumber());
        }
    }

    private void printProtocolHeader(Specialty specialty, Byte course, Discipline discipline) {
        System.out.println("\n------------------");
        System.out.println("Protocol specialty: " + specialty.getName() +
                ", year: " + course +
                ", discipline: " + discipline.getName());
        System.out.println("------------------");
    }
    /**
     * Prints a report of all grades for the student identified by faculty number.
     * @param facultyNumber The faculty number of the student.
     * @return true if report printed successfully; false otherwise.
     */
    public boolean report(String facultyNumber) {
        Utility.validateArgsForNulls(facultyNumber);

        StringBuilder builder = new StringBuilder();

        Student s = getStudentByFacultyNumber(facultyNumber);
        if(s == null)return false;

        System.out.println("Print report for student with faculty number and name: " + facultyNumber +" "+s.getFullName());
        System.out.println("Student grades:");

        if (s.getDisciplineGrades().isEmpty()) {
            builder.append("Student has no grades!\n");
        }
        else {
            for (HashMap.Entry<Discipline, List<Integer>> entry : s.getDisciplineGrades().entrySet()) {
                builder.append(entry.getKey().getName()).append(": ");

                if (entry.getValue().isEmpty()) {
                    builder.append("no grades");
                } else {
                    builder.append(entry.getValue());
                }

                builder.append("\n");
            }
        }
        System.out.println(builder.toString());
        return true;
    }
    /**
     * Adds a new student to the collection if the student is valid and doesn't already exist.
     *
     * @param student the Student object to be added to the collection
     * @return true if the student was successfully added, false if a student with the same
     *         faculty number already exists
     * @throws IllegalArgumentException if the student parameter is null
     */
    public boolean addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cant be of null value!");
        }

        for (Student existingStudent : students) {
            if (existingStudent.getFacultyNumber().equals(student.getFacultyNumber())) {
                System.out.println("Student with faculty number " + student.getFacultyNumber() + " already exists!");
                return false;
            }
        }
        students.add(student);
        return true;
    }
    /**
     * Removes all students from the collection, leaving it empty.
     * After calling this method, the collection will contain no students.
     */
    public void clear() {
        students.clear();
    }
}
