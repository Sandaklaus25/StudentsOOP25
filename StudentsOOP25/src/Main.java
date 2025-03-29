import java.util.*;

public class Main {
    public static void main(String[] args) {
        Discipline javaProgramming = new Discipline("Java Programming", true);
        Discipline algorithms = new Discipline("Algorithms", true);
        Discipline databases = new Discipline("Databases", false);

        List<Byte> javaCourses = new ArrayList<>(Arrays.asList((byte) 1, (byte) 2, (byte) 3));
        List<Byte> algorithmsCourses = new ArrayList<>(Arrays.asList((byte) 1, (byte) 2));
        List<Byte> databasesCourses = new ArrayList<>(Arrays.asList((byte) 1));

        Specialty computerScience = new Specialty("Computer Science");
        computerScience.AddDiscipline(javaProgramming, javaCourses);
        computerScience.AddDiscipline(databases, databasesCourses);

        Specialty AI = new Specialty("Artificial Intelligence");
        AI.AddDiscipline(algorithms, algorithmsCourses);

        Student student1 = new Student("John Doe", 123456, (byte) 2, computerScience, 'A', StudentStatus.записан);
        Student student2 = new Student("Jane Smith", 654321, (byte) 3, AI, 'B', StudentStatus.завършил);

        student1.setAverageGrade(5.75);
        student2.setAverageGrade(6.00);

        System.out.println("Student 1: " + student1);
        System.out.println("Student 2: " + student2);
        System.out.println("Specialty name: " + computerScience.getName());
    }
}