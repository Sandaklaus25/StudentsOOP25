package commands;

import interfaces.Command;
import exceptions.InsufficientArgumentsException;
import models.*;
/**
 * Command implementation for adding a grade to a student for a specific discipline.
 * <p>
 * This command allows adding numerical grades (2-6) to students for specific courses.
 * The grade is validated to ensure it's a proper integer value within the expected range.
 * </p>
 *
 * @see Command
 * @see StudentsManager
 * @see DisciplineManager
 *
 * <p><b>Command Format:</b> {@code addgrade <facultyNumber> <discipline> <grade>}</p>
 * <p><b>Example:</b> {@code addgrade 12345 Mathematics 5}</p>
 *
 * <p><b>Parameters:</b></p>
 * <ul>
 *   <li>{@code facultyNumber} - The student's 5-digit faculty number</li>
 *   <li>{@code discipline} - Name of the academic discipline/course</li>
 *   <li>{@code grade} - Numerical grade between 2 and 6 (inclusive)</li>
 * </ul>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>Exactly 4 arguments required (command + 3 parameters)</li>
 *   <li>Grade must be a valid integer</li>
 *   <li>Student must exist in the system</li>
 *   <li>Discipline must be valid</li>
 * </ul>
 *
 */
public class AddGrade implements Command {
    /**
     *
     * @param t  an array of arguments required for the command
     * @return true or false depending on command execution
     * @throws InsufficientArgumentsException when incorrect number of arguments provided
     * @throws NumberFormatException when grade is not a valid integer
     */
    @Override
    public boolean execute(String[] t) throws InsufficientArgumentsException {
        if(t.length!=4)
            throw new InsufficientArgumentsException("Invalid number of arguments!");

        String facultyNumber = t[1];
        Discipline discipline = DisciplineManager.getDisciplineByName(t[2]);
        int grade;
        try {
            grade = Integer.parseInt(t[3]);
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid input for grade! Must be an integer between 2 and 6 inclusive!");
            return false;
        }

        return StudentsManager.getInstance().addGrade(facultyNumber,discipline,grade);
    }
}
