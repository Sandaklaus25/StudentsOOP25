package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;
/**
 * Command implementation for closing the current file.
 * Format: close
 */
public class Close implements Command {
    /**
     * Closes the currently opened file and clears the loaded data.
     *
     * @param t Array containing command tokens where:
     *          t[0] - command name
     * @param sm Reference to the StudentsManager
     * @param fm Reference to the FileManager
     * @return Result message of the operation
     * @throws InsufficientArgumentsException When incorrect number of arguments is provided
     */
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        return fm.closeFile();
    }
}
