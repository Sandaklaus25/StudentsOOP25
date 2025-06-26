package commands;

import interfaces.Command;
import models.Discipline;
import exceptions.InsufficientArgumentsException;
import models.DisciplineManager;
import models.StudentsManager;

/**
 * Command implementation for generating academic protocols for specific disciplines.
 * <p>
 * The Protocol command produces a comprehensive listing of all students enrolled
 * in a particular discipline, displaying their academic information in a concise,
 * protocol-suitable format. This is typically used for official academic documentation
 * and examination records.
 * </p>
 *
 * @see Command
 * @see StudentsManager
 * @see DisciplineManager
 * @see Discipline
 *
 * <p><b>Command Format:</b> {@code protocol <discipline>}</p>
 * <p><b>Example:</b> {@code protocol Mathematics}</p>
 *
 * <p><b>Parameters:</b></p>
 * <ul>
 *   <li>{@code discipline} - Name of the academic discipline/course</li>
 * </ul>
 *
 * <p><b>Protocol Contents:</b></p>
 * <ul>
 *   <li>List of all students enrolled in the discipline sorted by faculty number</li>
 *   <li>Student identification information (faculty numbers, names, program, year, status, average grade)</li>
 *   <li>Academic program and group assignments</li>
 *   <li>One-line summary format per student</li>
 * </ul>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>Exactly 2 arguments required (command + discipline name)</li>
 *   <li>Discipline must exist in the system</li>
 *   <li>At least one student must be enrolled in the discipline</li>
 * </ul>
 *
 * <p><b>Output Format:</b></p>
 * <ul>
 *   <li>Compact, one-row format per student</li>
 *   <li>Organized for easy review and record-keeping</li>
 *   <li>Includes all essential student information</li>
 * </ul>
 *
 * <p><b>Use Cases:</b></p>
 * <ul>
 *   <li>Examination protocols</li>
 *   <li>Course enrollment verification</li>
 *   <li>Academic record documentation</li>
 *   <li>Official reporting requirements</li>
 * </ul>

 */
public class Protocol implements Command {
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

        Discipline discipline = DisciplineManager.getDisciplineByName(t[1]);
        if (discipline == null)return false;

        return StudentsManager.getInstance().protocol(discipline);
    }
}
