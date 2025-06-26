package commands;

import interfaces.Command;
import exceptions.InsufficientArgumentsException;
import models.StudentsManager;

/**
 * Command implementation for modifying student attributes.
 * <p>
 * This command allows changes to a student's core academic information
 * including their program/specialty, group, or academic year. It provides
 * flexibility for handling transfers, corrections, and adjustments.
 * </p>
 *
 * @see Command
 * @see StudentsManager
 *
 * <p><b>Command Format:</b> {@code change <facultyNumber> <option> <value>}</p>
 * <p><b>Examples:</b></p>
 * <ul>
 *   <li>{@code change 12345 program ComputerScience}</li>
 *   <li>{@code change 12345 group 3b}</li>
 *   <li>{@code change 12345 year 2}</li>
 * </ul>
 *
 * <p><b>Parameters:</b></p>
 * <ul>
 *   <li>{@code facultyNumber} - The student's 5-digit faculty number</li>
 *   <li>{@code option} - Attribute to change: "program", "group", or "year"</li>
 *   <li>{@code value} - New value for the specified attribute</li>
 * </ul>
 *
 * <p><b>Supported Options:</b></p>
 * <ul>
 *   <li>{@code program} - Changes student's academic specialty/major</li>
 *   <li>{@code group} - Changes student's group assignment (format: digit + letter)</li>
 *   <li>{@code year} - Changes student's academic year/course level</li>
 * </ul>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>Exactly 4 arguments required</li>
 *   <li>Student must exist in the system</li>
 *   <li>Option must be one of the supported values</li>
 *   <li>Value must be valid for the specified option</li>
 * </ul>
 */
public class Change implements Command {
    /**
     *
     * @param t  an array of arguments required for the command
     * @return true or false depending on command execution
     * @throws InsufficientArgumentsException when incorrect number of arguments provided
     */
    @Override
        public boolean execute(String[] t) throws InsufficientArgumentsException {
        if(t.length!=4)
            throw new InsufficientArgumentsException("Invalid number of arguments!");
        String facultyNumber = t[1];
        String option = t[2];
        String value = t[3];

        return StudentsManager.getInstance().change(facultyNumber, option, value);
    }
}