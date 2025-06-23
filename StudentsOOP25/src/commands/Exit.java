package commands;

import commands.interfaces.Command;
import models.FileManager;
import exceptions.InsufficientArgumentsException;
/**
 * The Exit command terminates the application.
 * <p>
 * When executed, this command displays a closing message and exits the program.
 * </p>
 * <p>
 * Command format: exit
 * </p>
 */
public class Exit implements Command {
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        System.out.println("Програмата се затваря...");
        System.exit(0);
        return false;
    }
}
