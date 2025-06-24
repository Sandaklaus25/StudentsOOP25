package commands;

import interfaces.Command;
import models.Discipline;
import models.DisciplineManager;
import models.FileManager;
import models.StudentsManager;
import exceptions.InsufficientArgumentsException;
/**
 * Command implementation for enrolling students in specific academic disciplines.
 * <p>
 * This command registers an existing student for a particular course or discipline,
 * enabling them to receive grades and participate in that subject. It serves as
 * the bridge between student records and course enrollment management.
 * </p>
 *
 * @see Command
 * @see StudentsManager
 * @see DisciplineManager
 * @see Discipline
 *
 * <p><b>Command Format:</b> {@code enrollin <facultyNumber> <discipline>}</p>
 * <p><b>Example:</b> {@code enrollin 12345 Mathematics}</p>
 *
 * <p><b>Parameters:</b></p>
 * <ul>
 *   <li>{@code facultyNumber} - The student's 5-digit faculty number</li>
 *   <li>{@code discipline} - Name of the academic discipline/course</li>
 * </ul>
 *
 * <p><b>Prerequisites:</b></p>
 * <ul>
 *   <li>Student must already be registered in the system</li>
 *   <li>Discipline must exist in the system</li>
 *   <li>Student cannot already be enrolled in the same discipline</li>
 * </ul>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>Exactly 3 arguments required (command + 2 parameters)</li>
 *   <li>Faculty number must correspond to existing student</li>
 *   <li>Discipline name must be valid and exist</li>
 * </ul>
 *
 * <p><b>Effects:</b></p>
 * <ul>
 *   <li>Adds discipline to student's enrolled courses</li>
 *   <li>Enables grade assignment for this discipline</li>
 *   <li>Student appears in discipline protocols</li>
 * </ul>
 */
public class EnrollToDiscipline implements Command {
    /**
     *
     * @param t  an array of arguments required for the command
     * @param fm the {@link FileManager} instance to perform file-related operations
     * @return true or false depending on command execution
     * @throws InsufficientArgumentsException when incorrect number of arguments provided
     */
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=3)
            throw new InsufficientArgumentsException("Invalid number of arguments!");

        String facultyNumber = t[1];
        Discipline discipline = DisciplineManager.getDisciplineByName(t[2]);
        if(discipline == null)return false;

        return StudentsManager.getInstance().enrollIn(facultyNumber, discipline);
    }
}