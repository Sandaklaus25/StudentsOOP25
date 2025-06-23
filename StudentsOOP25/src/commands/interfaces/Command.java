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
public interface Command { //could add here validations of args instead
    boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException;
}
