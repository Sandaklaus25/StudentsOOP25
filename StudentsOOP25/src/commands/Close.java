package commands;

import commands.interfaces.Command;
import models.FileManager;
import exceptions.InsufficientArgumentsException;
/**
 * Command implementation for closing the current file.
 * Format: close
 */
public class Close implements Command {
    /**
     * Closes the currently opened file and clears the loaded data.
     *
     * @param t  Array containing command tokens where:
     *           t[0] - command name
     * @param fm Reference to the FileManager
     * @return Result message of the operation
     * @throws InsufficientArgumentsException When incorrect number of arguments is provided
     */
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        fm.closeFile();
        return true;
    }
}
