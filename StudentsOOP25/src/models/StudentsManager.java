package models;

import java.util.*;
//fix later: edit its method to void if possible WITH empty returns if needed also remove many exceptions
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

    public Student getStudentByFacultyNumber(String facultyNumber) {
        for (Student student : students) {
            if (student.getFacultyNumber().equals(facultyNumber)) {
                return student;
            }
        }
        System.out.println("Student with faculty number \"" + facultyNumber + "\" was not found!");
        return null;
    }

    public static void validateArgsForNulls(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                throw new IllegalArgumentException("All arguments must be non-null.");
            }
        }
    }

    public boolean enroll(String facultyNumber, Specialty specialty, char[] group, String fullName) {
        validateArgsForNulls(facultyNumber,specialty,group,fullName);

        for (Student student : students) {
            if (student.getFacultyNumber().equals(facultyNumber)) {
                System.out.println("Student already exists");
                return false;
            }
        }

        Student s = new Student(fullName, facultyNumber, (byte) 1, specialty, group);
        students.add(s);
        s.setUpCourseDisciplines();

        System.out.println("Student " + s.getFullName() + " has been successfully enrolled.");
        return true;
    }

    public boolean advance(String facultyNumber) {
        validateArgsForNulls(facultyNumber);
        Student s = getStudentByFacultyNumber(facultyNumber);

        if(s==null) return false;
        if(!s.getStatus().isActive()) {
            throw new IllegalStateException("Student must be in active status!");
        }
        if(s.getCourse()==4)
        {
            throw new IllegalStateException("Student is in his last year and has to graduate instead.");
        }

        try { //fix later if needed
            s.setCourseUp();
            System.out.println("Student successfully advanced. Failed exams, if any, remain on record.");
            return true;
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean addGrade(String facultyNumber, Discipline discipline, int grade) {
        validateArgsForNulls(facultyNumber,discipline,grade);

        if (!(grade > 1 && grade < 7)) {
            throw new IllegalArgumentException("Grade must be between 2 and 6 inclusive.");
        }

        Student s = getStudentByFacultyNumber(facultyNumber);

        if(s==null) {
            return false;
        }
        if(!s.getStatus().isActive()) {
            throw new IllegalStateException("Student must be in active status to be graded.");
        }
        if(!s.getDisciplineGrades().containsKey(discipline)) {
            throw new IllegalStateException("The student is not enrolled in a discipline with that name.");
        }

        s.getDisciplineGrades().get(discipline).add(grade);
        s.updateAverageGrade();
        System.out.println("Grade added successfully.");
        return true;
    }

    public boolean change(String facultyNumber, String option, String value) {
        validateArgsForNulls(facultyNumber,option,value);
        Student s = getStudentByFacultyNumber(facultyNumber);
        if(s==null) return false;
        if (!s.getStatus().isActive()) {
            throw new IllegalStateException("Student must be in active status to use change"); //possible IllegalState
        }

        switch (option.toLowerCase()) {
            case "group":
                if(value.length()!=2
                        || !Character.isDigit(value.charAt(0))
                        || !Character.isLetter(value.charAt(1))) {
                        throw new IllegalArgumentException("Group must be 2 characters long and must be starting with digit and ending with letter. Example: 2a");
                }

                char[] group = {value.charAt(0), value.charAt(1)};
                s.setGroup(group);
                System.out.println("Group changed successfully.");
                return true;
                //break;
            case "year":
                byte newCourse;
                try { //fix later: does not show message because NumberFormat not included in first catch of Starter class IF it is made like the other if
                    newCourse = Byte.parseByte(value);
                } catch (NumberFormatException e) {
                    System.out.println("Year/course must be an integer!");
                    return false;
                }
                if (!s.hasPassedRequiredSubjectsWithMaxTwoCoursesFails()) {
                    throw new IllegalStateException("Student has not passed enough required subjects to advance!");
                }
                if (newCourse != s.getCourse() + 1) {
                    throw new IllegalArgumentException("Student can only advance to the next course/year!");
                }

                return advance(facultyNumber);
                //break;
            case "program":
                    Specialty newSpecialty = SpecialtyManager.getSpecialtyByName(value);
                    if (newSpecialty == null) return false;
                    if (!s.hasPassedRequirementsSubjectsForSpecialty(newSpecialty)) {
                        System.out.println("Student could not change program/specialty due to his disciplines and grades!");
                        return false;
                    }

                    s.removeEmptyOldDisciplines(newSpecialty);
                    s.setSpecialty(newSpecialty);
                    s.setUpCourseDisciplines();
                    System.out.println("Student successfully changed program/specialty.");
                    return true;
                    //break;
            default:
                throw new IllegalArgumentException("Invalid option \"" + option + "\"\t\t\t Option must be group, program or year!");
        }
    }

    public boolean graduate(String facultyNumber) {
        validateArgsForNulls(facultyNumber);
        Student s = getStudentByFacultyNumber(facultyNumber);
        if(s==null) return false;
        if (!s.getStatus().isActive()) {
            throw new IllegalStateException("Student must be in active status to use graduate");
        }
        if(s.getCourse() != 4)
        {
            throw new IllegalStateException("The student must have passed all years/courses up to 4th (1-4).");
        }
        if (!s.hasPassedAllSubjects()) {
            throw new IllegalStateException("Студентът не е преминал успешно някои дисциплини и не може да се дипломира!");
        }

        s.setStatus(StudentStatus.GRADUATED);
        System.out.println("Student has graduated. :)");
        return true;
    }

    public boolean interrupt(String facultyNumber) {
        validateArgsForNulls(facultyNumber);
        Student s = getStudentByFacultyNumber(facultyNumber);
        if(s==null) return false;

        if (s.getStatus().isInterrupted()) {
            throw new IllegalStateException("Student was already interrupted.");
        }
        else if(s.getStatus().hasGraduated()){
            throw new IllegalStateException("Student has graduated and cannot be interrupted.");
        }

        s.setStatus(StudentStatus.INTERRUPTED);
        System.out.println("Student has changed to interrupted. :(");
        return true;
    }

    public boolean resume(String facultyNumber) {
        validateArgsForNulls(facultyNumber);
        Student s = getStudentByFacultyNumber(facultyNumber);
        if(s==null) return false;
        if (s.getStatus().isActive()) {
            throw new IllegalStateException("Student was already active!");
        }
        else if(s.getStatus().hasGraduated()){  //can be adjusted to be able to resume but only if I had more data to work with. It is capped to 4 courses for now and there is no need for them to return
            throw new IllegalStateException("Student has graduated and cannot be resumed.");
        }

        s.setStatus(StudentStatus.ACTIVE);
        System.out.println("Student has been updated to active/resumed.");
        return true;
    }

    public boolean print(String facultyNumber) {
        validateArgsForNulls(facultyNumber);
        Student s = getStudentByFacultyNumber(facultyNumber);
        if(s==null) return false;

        System.out.println("Printing details for student with faculty number: "+facultyNumber+s.toStringAlternative());
        return true;
    }

    public boolean printAll(Specialty specialty, Byte course) {
        validateArgsForNulls(specialty,course);
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

        if (!found) {   //fix later if needed: can make to exceptions if deemed worthy
            System.out.println("No students pass the criteria, so none were printed.");
        }

        return true;
    }

    public boolean enrollIn(String facultyNumber, Discipline discipline) {
        validateArgsForNulls(facultyNumber,discipline);
        Student s = getStudentByFacultyNumber(facultyNumber);
        if(s==null) return false;

        if(!s.getStatus().isActive()) {
            throw new IllegalStateException("Student must be in active status to use enrollIn!");
        }
        if (!s.getSpecialty().getDisciplineCourses().containsKey(discipline)) {
            throw new IllegalStateException("Discipline is not included in the student's specialty!");
        }
        if (!s.getSpecialty().getDisciplineCourses().get(discipline).contains(s.getCourse())) {
            throw new IllegalStateException("Discipline unavailable for student in his course!");
        }
        if(s.getDisciplineGrades().putIfAbsent(discipline, new ArrayList<>()) != null) {
            throw new IllegalStateException("Student already in discipline. Nothing changed!");
        }

        s.updateAverageGrade();
        System.out.println("Discipline "+discipline.getName()+" has been added successfully.");
        return true;
    }

    public boolean protocol(Discipline discipline) {
        validateArgsForNulls(discipline);

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
        for (HashMap.Entry<Specialty, HashMap<Byte, List<Student>>> specialtyEntry : protocolMap.entrySet()) {
            printSpecialtyProtocols(specialtyEntry, discipline);
        }
        System.out.println("\n------------------");
        System.out.println("End!");
    }

    private void printSpecialtyProtocols(HashMap.Entry<Specialty, HashMap<Byte, List<Student>>> specialtyEntry, Discipline discipline) {
        Specialty specialty = specialtyEntry.getKey();
        HashMap<Byte, List<Student>> courseMap = specialtyEntry.getValue();

        for (HashMap.Entry<Byte, List<Student>> courseEntry : courseMap.entrySet()) {
            Byte course = courseEntry.getKey();
            List<Student> studentList = getStudentsSortByFacultyNumber(courseEntry);

            printProtocolHeader(specialty, course, discipline);

            for (Student student : studentList) {
                System.out.println(student);
            }
        }
    }

    private List<Student> getStudentsSortByFacultyNumber(HashMap.Entry<Byte, List<Student>> courseEntry) {
        List<Student> studentList = new ArrayList<>(courseEntry.getValue());
        studentList.sort(Comparator.comparing(Student::getFacultyNumber)); //new Faculty Number Comparator
        return studentList;
    }
//fix later
    private static class FacultyNumberComparator implements Comparator<Student> {
        public int compare(Student student1, Student student2) {
            String fn1 = student1.getFacultyNumber();
            String fn2 = student2.getFacultyNumber();

            return fn1.compareTo(fn2);
        }
    }

    private void printProtocolHeader(Specialty specialty, Byte course, Discipline discipline) {
        System.out.println("\n------------------");
        System.out.println("Protocol specialty: " + specialty.getName() +
                ", year: " + course +
                ", discipline: " + discipline.getName());
        System.out.println("------------------");
    }

    public boolean report(String facultyNumber) {
        validateArgsForNulls(facultyNumber);

        StringBuilder builder = new StringBuilder();

        Student s = getStudentByFacultyNumber(facultyNumber);
        if(s== null)return false;

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

    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cant be of null value!");
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
