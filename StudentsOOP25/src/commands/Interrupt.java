package commands;

import interfaces.Command;
import models.FileManager;
import models.StudentsManager;
import exceptions.InsufficientArgumentsException;
/**
 * Command implementation for marking students status changed as having interrupted their studies.
 * <p>
 * The Interrupt command updates a student's academic status to indicate they have
 * temporarily suspended their studies. Interrupted students maintain their records
 * but are not considered active for academic purposes until their status is resumed.
 * </p>
 *
 * @see Command
 * @see StudentsManager
 * @see Resume
 *
 * <p><b>Command Format:</b> {@code interrupt <facultyNumber>}</p>
 * <p><b>Example:</b> {@code interrupt 12345}</p>
 *
 * <p><b>Parameters:</b></p>
 * <ul>
 *   <li>{@code facultyNumber} - The student's 5-digit faculty number</li>
 * </ul>
 *
 * <p><b>Prerequisites:</b></p>
 * <ul>
 *   <li>Student must exist in the system</li>
 *   <li>Student must currently be in active status</li>
 *   <li>Student cannot already be interrupted</li>
 * </ul>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>Exactly 2 arguments required (command + faculty number)</li>
 *   <li>Student must be found in the system</li>
 *   <li>Cannot interrupt already interrupted students</li>
 *   <li>Cannot interrupt graduated students</li>
 * </ul>
 *
 * <p><b>Effects:</b></p>
 * <ul>
 *   <li>Sets student status to "interrupted"</li>
 *   <li>Student excluded from active student lists</li>
 *   <li>Preserves all academic records and enrollments</li>
 *   <li>Can be reversed using the "resume" command</li>
 * </ul>
 */
public class Interrupt implements Command {
    /**
     *
     * @param t  an array of arguments required for the command
     * @param fm the {@link FileManager} instance to perform file-related operations
     * @return true or false depending on command execution
     * @throws InsufficientArgumentsException when incorrect number of arguments provided
     */
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Invalid number of arguments!");
        return StudentsManager.getInstance().interrupt(t[1]);
    }
}
