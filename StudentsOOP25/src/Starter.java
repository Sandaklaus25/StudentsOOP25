import commands.Controller;

import java.util.Scanner;

public class Starter {
    public void run()
    {
        Scanner scanner = new Scanner(System.in);
        String input;
        Controller controller = new Controller();

        System.out.println(controller.open("open savefile.txt"));

        for (int i = 0; i < 3; ++i) System.out.println();
        System.out.println("\t\t(Use \"help\" for more information)");

        while (true)
        {
            System.out.print(">> Enter input: ");
            input = scanner.nextLine();
            try{
                if(!controller.open(input)) System.out.println("Command failed!");
            }
            catch(IllegalArgumentException | IllegalStateException e)
            {
                System.out.println(e.getMessage());
            }
            catch (Exception e){
                System.out.println("Error: Invalid command!");
            }

            for (int i = 0; i < 3; ++i) System.out.println();
        }
    }
}
