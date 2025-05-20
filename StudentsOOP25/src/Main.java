import Commands.Controller;
import Models.Student;
import Models.StudentsManager;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        Controller controller = new Controller();

        System.out.println(controller.open("open savefile.txt"));

        for (int i = 0; i < 3; ++i) System.out.println();

        System.out.println("\t\t(Използвай \"help\" за помощ)");
        while (true) {
            System.out.print(">> Изчаква се команда: ");
            input = scanner.nextLine();
            try{
                String output = controller.open(input);
                System.out.println(output);
            }
            catch (Exception e){
                System.out.println("Невалидна команда!");
            }

            for (int i = 0; i < 3; ++i) System.out.println();
        }
    }
}
