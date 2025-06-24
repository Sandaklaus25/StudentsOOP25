package commands;

import interfaces.Command;
import exceptions.InsufficientArgumentsException;
import models.*;
/**
 * Command implementation for displaying information about all students in a specific program and year.
 * <p>
 * The PrintAll command provides a comprehensive listing of all students enrolled
 * in a particular academic specialty and year level. This is useful for generating
 * class lists, academic reports, and administrative overviews.
 * </p>
 *
 * @see Command
 * @see StudentsManager
 * @see SpecialtyManager
 * @see Print
 *
 * <p><b>Command Format:</b> {@code printall <program> <year>}</p>
 * <p><b>Example:</b> {@code printall ComputerScience 2}</p>
 *
 * <p><b>Parameters:</b></p>
 * <ul>
 *   <li>{@code program} - Name of the academic specialty/program</li>
 *   <li>{@code year} - Academic year/course level (integer)</li>
 * </ul>
 *
 * <p><b>Filtering Criteria:</b></p>
 * <ul>
 *   <li>Students must be enrolled in the specified program</li>
 *   <li>Students must be in the specified academic year</li>
 *   <li>Includes students of all statuses (active, interrupted, graduated)</li>
 * </ul>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>Exactly 3 arguments required (command + 2 parameters)</li>
 *   <li>Program/specialty must exist in the system</li>
 *   <li>Year must be a valid small integer</li>
 *   <li>Year must be within acceptable range for the program</li>
 * </ul>
 *
 * <p><b>Output Information:</b></p>
 * <ul>
 *   <li>List of all matching the criteria students</li>
 *   <li>Student names and faculty numbers</li>
 *   <li>Current academic status</li>
 *   <li>Academics specialty/program</li>
 *   <li>Academics year/course</li>
 *   <li>Current group assignment</li>
 * </ul>
 *
 * <p><b>Use Cases:</b></p>
 * <ul>
 *   <li>Generating class rosters</li>
 *   <li>Academic planning and statistics</li>
 *   <li>Administrative reporting</li>
 * </ul>
 *
 */
public class PrintAll implements Command {
    /**
     *
     * @param t  an array of arguments required for the command
     * @param fm the {@link FileManager} instance to perform file-related operations
     * @return true or false depending on command execution
     * @throws InsufficientArgumentsException when incorrect number of arguments provided
     * @throws NumberFormatException when year parameter is not a valid integer
     */
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=3)
            throw new InsufficientArgumentsException("Invalid number of arguments!");

        Specialty specialty = SpecialtyManager.getSpecialtyByName(t[1]);
        if(specialty==null)return false;

        byte course;
        try {
             course = Byte.parseByte(t[2]);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Course must be a small integer!");
            return false;
        }

        return StudentsManager.getInstance().printAll(specialty,course);
    }
}
