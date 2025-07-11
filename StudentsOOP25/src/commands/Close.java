package commands;

import interfaces.Command;
import exceptions.InsufficientArgumentsException;
import models.FileManager;

/**
 * Command implementation for closing the currently loaded file and clearing system data.
 * <p>
 * This command safely closes the active data file and clears all loaded student information
 * from memory. After execution, the system returns to an unloaded state where most
 * commands become unavailable until a new file is opened.
 * </p>
 *
 * @see Command
 * @see FileManager
 *
 * <p><b>Command Format:</b> {@code close}</p>
 * <p><b>Example:</b> {@code close}</p>
 *
 * <p><b>Parameters:</b> None</p>
 *
 * <p><b>Effects:</b></p>
 * <ul>
 *   <li>Closes the currently loaded file</li>
 *   <li>Clears all student data from memory</li>
 *   <li>Disables most commands until a new file is opened</li>
 *   <li>Preserves only "open", "help", and "exit" commands</li>
 * </ul>
 *
 * <p><b>Warning:</b> Any unsaved changes will be lost. Use "save" command
 * before closing if you want to preserve changes.</p>
 */
public class Close implements Command {
    /**
     *
     * @param t  an array of arguments required for the command
     * @return true or false depending on command execution
     * @throws InsufficientArgumentsException not used here
     */
    @Override
    public boolean execute(String[] t) throws InsufficientArgumentsException {
        FileManager.getInstance().closeFile();
        return true;
    }
}
