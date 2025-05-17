import Commands.Controller;


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
            String output = controller.open(input);
            if(output != null) {
                System.out.println(output);
            }
            else {
                System.out.println("Невалидна команда!");
            }

            for (int i = 0; i < 3; ++i) System.out.println();
        }
    }
}
