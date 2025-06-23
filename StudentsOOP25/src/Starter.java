import commands.Controller;

import java.util.Scanner;
/**
 * Starter class to handle user input and command execution loop.
 */
public class Starter {
    /**
     * Runs the command processing loop.
     * Opens the default savefile and then continuously reads user input from the console,
     * forwarding commands to the Controller.
     * Prints errors and feedback based on command execution results.
     */
    public void run()
    {
        Scanner scanner = new Scanner(System.in);
        String input;
        Controller controller = new Controller();

        if(!controller.open("open savefssile.txt"))
        {
            System.out.println("Savefile was not opened at start!");
        }


        System.out.println("\n\t\t(Use \"help\" for more information)");

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
