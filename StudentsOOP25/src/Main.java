//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Student student = new Student("Дебил", 235262, (byte)3, Specialty.СИТ, 'a', StudentStatus.завършил);
        System.out.println(student.toString());
    }
}