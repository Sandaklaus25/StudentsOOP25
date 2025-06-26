import commands.CommandsEnum;
import commands.Controller;
import models.FileManager;
import java.util.Scanner;
/**
 * Starter class to handle user input and command execution loop.
 */
public class Starter {
    private static final String DEFAULT_SAVE_FILE_NAME = "savefile.txt";
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


        if(!FileManager.getInstance().openFile(DEFAULT_SAVE_FILE_NAME)) {
            System.out.println("\n Default save file has failed and might have been corrupted!");
            System.out.println("\n Closing file just in case...");
            FileManager.getInstance().closeFile();
        }

        System.out.println("\n\t\t(Use \""+ CommandsEnum.HELP.getCommandName() +"\" for more information)");

        while (true)
        {
            System.out.print(">> Enter input: ");
            input = scanner.nextLine();
            try{
                if(!controller.processUserInput(input)) System.out.println("Command failed!");
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
