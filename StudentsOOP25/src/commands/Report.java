package commands;

import interfaces.Command;
import models.FileManager;
import models.StudentsManager;
import exceptions.InsufficientArgumentsException;
/**
 * Command implementation for generating comprehensive academic reports for individual students.
 * <p>
 * The Report command produces detailed academic transcripts and performance summaries
 * for a specific student. This includes complete grade histories, academic progress,
 * and overall performance metrics formatted for academic documentation.
 * </p>
 *
 * @see Command
 * @see StudentsManager
 * @see Print
 *
 * <p><b>Command Format:</b> {@code report <facultyNumber>}</p>
 * <p><b>Example:</b> {@code report 12345}</p>
 *
 * <p><b>Parameters:</b></p>
 * <ul>
 *   <li>{@code facultyNumber} - The student's 5-digit faculty number</li>
 * </ul>
 *
 * <p><b>Report Contents:</b></p>
 * <ul>
 *   <li>Complete academic transcript</li>
 *   <li>All enrolled disciplines with grades</li>
 *   <li>Academic performance statistics</li>
 *   <li>Grade point average calculations</li>
 *   <li>Academic status and progression</li>
 * </ul>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>Exactly 2 arguments required (command + faculty number)</li>
 *   <li>Student must exist in the system</li>
 *   <li>Faculty number must be valid format</li>
 * </ul>
 *
 * <p><b>Report Features:</b></p>
 * <ul>
 *   <li>Comprehensive academic history</li>
 *   <li>Official transcript format</li>
 *   <li>Performance analytics</li>
 *   <li>Suitable for administrative purposes</li>
 * </ul>
 *
 * <p><b>Difference from Print:</b></p>
 * <ul>
 *   <li>More detailed than basic print command</li>
 *   <li>Focuses on academic performance data</li>
 *   <li>Formatted for official documentation</li>
 *   <li>Includes calculated metrics and statistics</li>
 * </ul>
 */
public class Report implements Command {
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

        return StudentsManager.getInstance().report(t[1]);
    }
}
