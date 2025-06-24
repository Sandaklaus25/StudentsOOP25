package commands;

import interfaces.Command;
import models.FileManager;
import exceptions.InsufficientArgumentsException;
/**
 * Command implementation for gracefully terminating the application.
 * <p>
 * The Exit command provides a clean shutdown mechanism for the student management
 * system. It displays a closing message and terminates the program execution.
 * This command is always available regardless of system state.
 * </p>
 *
 * @see Command
 *
 * <p><b>Command Format:</b> {@code exit}</p>
 * <p><b>Example:</b> {@code exit}</p>
 *
 * <p><b>Parameters:</b> None</p>
 *
 * <p><b>Behavior:</b></p>
 * <ul>
 *   <li>Displays "Program is closing..." message</li>
 *   <li>Immediately terminates the application with System.exit(0)</li>
 *   <li>Does not perform any data validation or saving</li>
 *   <li>Available even when no file is loaded</li>
 * </ul>
 *
 * <p><b>Warning:</b> This command does not automatically save changes.
 * Ensure you save your work before exiting if you want to preserve changes.</p>
 *
 * <p><b>Return Value:</b> Always returns false since the program terminates
 * before the return statement is reached.</p>
 */
public class Exit implements Command {
    /**
     *
     * @param t  an array of arguments required for the command
     * @param fm the {@link FileManager} instance to perform file-related operations
     * @return false if command failed
     * @throws InsufficientArgumentsException not used here
     */
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        System.out.println("Program is closing...");
        System.exit(0);
        return false;
    }
}
