package commands;

import interfaces.Command;
import exceptions.InsufficientArgumentsException;
import models.StudentsManager;

/**
 * Command implementation for restoring academic rights of interrupted students.
 * <p>
 * The Resume command reactivates students who have previously had their studies
 * interrupted, returning them to active academic status. This allows interrupted
 * students to continue their education and regain access to all academic services.
 * </p>
 *
 * @see Command
 * @see StudentsManager
 * @see Interrupt
 *
 * <p><b>Command Format:</b> {@code resume <facultyNumber>}</p>
 * <p><b>Example:</b> {@code resume 12345}</p>
 *
 * <p><b>Parameters:</b></p>
 * <ul>
 *   <li>{@code facultyNumber} - The student's 5-digit faculty number</li>
 * </ul>
 *
 * <p><b>Prerequisites:</b></p>
 * <ul>
 *   <li>Student must exist in the system</li>
 *   <li>Student must currently be in interrupted status</li>
 *   <li>Student cannot be graduated</li>
 * </ul>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>Exactly 2 arguments required (command + faculty number)</li>
 *   <li>Student must be found in the system</li>
 *   <li>Can only resume students with interrupted status</li>
 *   <li>Cannot resume already active students</li>
 * </ul>
 *
 * <p><b>Effects:</b></p>
 * <ul>
 *   <li>Changes student status from interrupted to active</li>
 *   <li>Restores full academic privileges</li>
 *   <li>Student appears in active student lists</li>
 *   <li>Enables continued enrollment and grade assignment</li>
 *   <li>Preserves all previous academic records</li>
 * </ul>
 *
 * <p><b>Administrative Notes:</b></p>
 * <ul>
 *   <li>No academic data is lost during interrupt/resume cycle</li>
 *   <li>All previous enrollments and grades remain intact</li>
 *   <li>Student can immediately continue from where they left off</li>
 * </ul>
 */
public class Resume implements Command {
    /**
     *
     * @param t  an array of arguments required for the command
     * @return true or false depending on command execution
     * @throws InsufficientArgumentsException when incorrect number of arguments provided
     */
    @Override
    public boolean execute(String[] t) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Invalid number of arguments!");

        return StudentsManager.getInstance().resume(t[1]);
    }
}
