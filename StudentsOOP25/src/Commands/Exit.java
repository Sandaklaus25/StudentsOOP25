package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;
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
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        System.out.println("Програмата се затваря...");
        System.exit(0);
        return null;
    }
}
