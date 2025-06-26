package commands;

import interfaces.Command;
import exceptions.InsufficientArgumentsException;
import models.StudentsManager;

/**
 * Command implementation for marking students as having successfully completed their studies.
 * <p>
 * The Graduate command updates a student's academic status to indicate they have
 * fulfilled all graduation requirements. This typically involves checking that
 * the student has successfully passed all required disciplines and met other
 * institutional requirements.
 * </p>
 *
 * @see Command
 * @see StudentsManager
 *
 * <p><b>Command Format:</b> {@code graduate <facultyNumber>}</p>
 * <p><b>Example:</b> {@code graduate 12345}</p>
 *
 * <p><b>Parameters:</b></p>
 * <ul>
 *   <li>{@code facultyNumber} - The student's 5-digit faculty number</li>
 * </ul>
 *
 * <p><b>Graduation Requirements:</b></p>
 * <ul>
 *   <li>Student must exist in the system</li>
 *   <li>Student must have passing grades in all enrolled disciplines</li>
 *   <li>Student must not be in interrupted status</li>
 *   <li>Student must have completed required academic years</li>
 * </ul>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>Exactly 2 arguments required (command + faculty number)</li>
 *   <li>Student must meet all graduation criteria</li>
 *   <li>Cannot graduate a student who is already graduated</li>
 * </ul>
 *
 * <p><b>Effects:</b></p>
 * <ul>
 *   <li>Sets student status to "graduated"</li>
 *   <li>Records graduation date/timestamp</li>
 *   <li>Student becomes eligible for diploma generation</li>
 * </ul>
 */
public class Graduate implements Command {
    /**
     * @param t  an array of arguments required for the command
     * @return true or false depending on command execution
     * @throws InsufficientArgumentsException if number of arguments is not exactly 2
     */
    @Override
    public boolean execute(String[] t) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Invalid number of arguments!");
        return StudentsManager.getInstance().graduate(t[1]);
    }
}
