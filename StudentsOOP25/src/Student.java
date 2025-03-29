public class Student {
    private String fullName;
    private int facultyNumber;
    private Specialty specialty;
    private Byte course;
    private char group;
    private StudentStatus status;
    private double averageGrade;

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

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }
    // </editor-fold>

    public Student(String fullName, int facultyNumber, byte course, Specialty specialty, char group , StudentStatus status) {
        this.fullName = fullName;
        this.facultyNumber = facultyNumber;
        this.course = course;
        this.specialty = specialty;
        this.status = status;
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "fullName='" + fullName + '\'' +
                ", facultyNumber=" + facultyNumber +
                ", specialty=" + specialty.toString() +
                ", course=" + course +
                ", group=" + group +
                ", status=" + status +
                ", averageGrade=" + averageGrade +
                '}';
    }
}
