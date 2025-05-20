package Commands.Interfaces;

import Models.FileManager;
import Exceptions.InsufficientArgumentsException;
import Models.StudentsManager;
/**
 * The Command interface defines the contract for all command objects in the system.
 * <p>
 * All command classes must implement this interface to provide consistent behavior
 * when executed within the application. Each command processes specific arguments
 * and performs actions on the StudentsManager and FileManager components.
 * </p>
 */
public interface Command {
    String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException;
}
