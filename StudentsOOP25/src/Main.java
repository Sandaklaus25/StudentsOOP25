import java.util.*;

public class Main {
    public static void main(String[] args) {
        Discipline math = new Discipline("Mathematics", true);
        Discipline oop = new Discipline("OOP", true);
        Discipline ai = new Discipline("Artificial Intelligence", false);

        Specialty cs = new Specialty("Computer Science");
        cs.AddDiscipline(math, Arrays.asList((byte)1, (byte)2));
        cs.AddDiscipline(oop, Arrays.asList((byte)2, (byte)3));
        cs.AddDiscipline(ai, Arrays.asList((byte)3));

        Specialty se = new Specialty("Software Engineering");
        se.AddDiscipline(math, Arrays.asList((byte)1));
        se.AddDiscipline(oop, Arrays.asList((byte)2));
        se.AddDiscipline(ai, Arrays.asList((byte)4));

        Specialty.specialties.add(cs);
        Specialty.specialties.add(se);

        Student s1 = new Student("Ivan Petrov", 1001, (byte)1, cs, 'A');
        Student s2 = new Student("Maria Ivanova", 1002, (byte)2, cs, 'B');
        Student s3 = new Student("Georgi Nikolov", 1003, (byte)2, se, 'A');
        Student s4 = new Student("Elena Stoyanova", 1004, (byte)4, se, 'B');
        Student s5 = new Student("Petar Dimitrov", 1005, (byte)1, cs, 'A');
        Student s6 = new Student("Nikolay Georgiev", 1006, (byte)2, cs, 'B');
        Student s7 = new Student("Teodora Hristova", 1007, (byte)3, cs, 'A');
        Student s8 = new Student("Iva Petrova", 1008, (byte)2, se, 'B');
        Student s9 = new Student("Dimitar Stoyanov", 1009, (byte)1, se, 'A');
        Student s10 = new Student("Kristina Marinova", 1010, (byte)4, se, 'B');
        Student s11 = new Student("Aleksandar Todorov", 1011, (byte)3, cs, 'C');
        Student s12 = new Student("Viktoria Vasileva", 1012, (byte)3, se, 'C');
        Student s13 = new Student("Simeon Ivanov", 1013, (byte)4, se, 'A');
        Student s14 = new Student("Elena Georgieva", 1014, (byte)2, cs, 'A');

        Student.enrollIn(1001, math);
        Student.enrollIn(1002, oop);
        Student.enrollIn(1003, oop);
        Student.enrollIn(1004, ai);
        Student.enrollIn(1005, math);
        Student.enrollIn(1006, math);
        Student.enrollIn(1006, oop);
        Student.enrollIn(1007, oop);
        Student.enrollIn(1007, ai);
        Student.enrollIn(1008, oop);
        Student.enrollIn(1009, math);
        Student.enrollIn(1010, ai);
        Student.enrollIn(1011, oop);
        Student.enrollIn(1011, ai);
        Student.enrollIn(1013, ai);
        Student.enrollIn(1014, math);
        Student.enrollIn(1014, oop);

        s1.setAverageGrade(2.43434343);
        s3.setAverageGrade(5.4);
        s7.setAverageGrade(34.30);
        s8.setAverageGrade(4.43);
        s9.setAverageGrade(6);


        Student.protocol(oop);
        Student.protocol(math);
        Student.protocol(ai);
    }
}
