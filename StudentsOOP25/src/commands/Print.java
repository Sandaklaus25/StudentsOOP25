package commands;

import interfaces.Command;
import models.FileManager;
import models.StudentsManager;
import exceptions.InsufficientArgumentsException;
/**
 * Command implementation for displaying detailed information about a specific student.
 * <p>
 * The Print command retrieves and displays comprehensive information about a single
 * student identified by their faculty number. This includes personal details,
 * academic status, program/specialty, year/course, group, average grade.
 * </p>
 *
 * @see Command
 * @see StudentsManager
 * @see PrintAll
 *
 * <p><b>Command Format:</b> {@code print <facultyNumber>}</p>
 * <p><b>Example:</b> {@code print 12345}</p>
 *
 * <p><b>Parameters:</b></p>
 * <ul>
 *   <li>{@code facultyNumber} - The student's 5-digit faculty number</li>
 * </ul>
 *
 * <p><b>Information Displayed:</b></p>
 * <ul>
 *   <li>Student's full name</li>
 *   <li>Faculty number</li>
 *   <li>Academic specialty/program</li>
 *   <li>Academic year/course</li>
 *   <li>Current group assignment</li>
 *   <li>Current status (active, interrupted, graduated)</li>
 *   <li>Average grade</li>
 * </ul>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>Exactly 2 arguments required (command + faculty number)</li>
 *   <li>Student must exist in the system</li>
 *   <li>Faculty number must be valid format</li>
 * </ul>
 *
 * <p><b>Output Format:</b></p>
 * <ul>
 *   <li>Structured, human-readable format</li>
 *   <li>Organized by information categories</li>
 *   <li>Includes all relevant student data</li>
 * </ul>
 */
public class Print implements Command {
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
        return StudentsManager.getInstance().print(t[1]);
    }
}
