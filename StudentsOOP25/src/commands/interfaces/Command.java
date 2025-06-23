package commands.interfaces;

import models.FileManager;
import exceptions.InsufficientArgumentsException;

/**
 * The Command interface defines the contract for all command objects in the system.
 * <p>
 * All command classes must implement this interface to provide consistent behavior
 * when executed within the application. Each command processes specific arguments
 * and performs actions on the StudentsManager and FileManager components.
 * </p>
 */
public interface Command {
    /**
     * Executes the command using the provided arguments and a {@link FileManager} instance.
     *
     * @param t  an array of arguments required for the command
     * @param fm the {@link FileManager} instance to perform file-related operations
     * @return {@code true} if the command was executed successfully, {@code false} otherwise
     * @throws InsufficientArgumentsException if the required arguments are missing or incomplete
     */
    boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException;
}
