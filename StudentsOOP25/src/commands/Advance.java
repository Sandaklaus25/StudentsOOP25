package commands;

import interfaces.Command;
import models.FileManager;
import models.StudentsManager;
import exceptions.InsufficientArgumentsException;
/**
 * Command implementation for advancing a student to the next academic year.
 * <p>
 * This command promotes a student to the next year of their academic program,
 * regardless of their current discipline completion status. It's an administrative
 * override that bypasses normal progression requirements.
 * </p>
 *
 * @see Command
 * @see StudentsManager
 *
 * <p><b>Command Format:</b> {@code advance <facultyNumber>}</p>
 * <p><b>Example:</b> {@code advance 12345}</p>
 *
 * <p><b>Parameters:</b></p>
 * <ul>
 *   <li>{@code facultyNumber} - The student's 5-digit faculty number</li>
 * </ul>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>Exactly 2 arguments required (command + faculty number)</li>
 *   <li>Student must exist in the system</li>
 *   <li>Student cannot already be in the maximum year</li>
 * </ul>
 *
 * <p><b>Note:</b> This command ignores failed disciplines and advances the student
 * regardless of their academic standing.</p>
 */
public class Advance implements Command {
    /**
     * @param t  an array of arguments required for the command
     * @param fm the {@link FileManager} instance to perform file-related operations
     * @return true or false depending on command execution
     * @throws InsufficientArgumentsException when incorrect number of arguments provided
     */
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Invalid number of arguments!");
        return StudentsManager.getInstance().advance(t[1]);
    }
}