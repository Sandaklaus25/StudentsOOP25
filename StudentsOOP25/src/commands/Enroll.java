package commands;

import interfaces.Command;
import models.FileManager;
import models.Specialty;
import models.SpecialtyManager;
import models.StudentsManager;
import exceptions.InsufficientArgumentsException;

import java.util.Arrays;
/**
 * Command implementation for registering new students in the university system.
 * <p>
 * The Enroll command handles the complete student registration process, including
 * validation of all required information such as faculty numbers, academic programs,
 * group assignments, and student names. It ensures data integrity through comprehensive
 * input validation before creating new student records.
 * </p>
 *
 * @see Command
 * @see StudentsManager
 * @see SpecialtyManager
 * @see Specialty
 *
 * <p><b>Command Format:</b> {@code enroll <facultyNumber> <program> <group> <names...>}</p>
 * <p><b>Example:</b> {@code enroll 12345 ComputerScience 2a John Smith}</p>
 *
 * <p><b>Parameters:</b></p>
 * <ul>
 *   <li>{@code facultyNumber} - Exactly 5-digit unique identifier (stored as String)</li>
 *   <li>{@code program} - Academic specialty/major program name</li>
 *   <li>{@code group} - Two-character group code (digit + letter, e.g., "2a")</li>
 *   <li>{@code name} - Student's full name (can contain multiple words)</li>
 * </ul>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>Faculty number must be exactly 5 digits</li>
 *   <li>Faculty number must be unique in the system</li>
 *   <li>Program/specialty must exist in the system</li>
 *   <li>Group format: first character must be digit, second must be letter</li>
 *   <li>Full name is constructed from all remaining arguments</li>
 *   <li>Minimum 5 arguments required (command + 4 parameters)</li>
 * </ul>
 *
 * <p><b>Default Settings:</b></p>
 * <ul>
 *   <li>New students are enrolled in year 1</li>
 *   <li>Students start with active status</li>
 *   <li>Disciplines are initially assigned</li>
 * </ul>
 */
public class Enroll implements Command{
        /**
         *
         * @param t  an array of arguments required for the command
         * @param fm the {@link FileManager} instance to perform file-related operations
         * @return true or false depending on command execution
         * @throws InsufficientArgumentsException when incorrect number of arguments provided
         */
        @Override
        public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
                if(t.length<5) throw new InsufficientArgumentsException ("Invalid number of arguments!");

                String facultyNumber = t[1];
                try{
                        if(t[1].length()!=5)throw new NumberFormatException();
                        Integer.parseInt(t[1]);
                }catch (NumberFormatException e)
                {
                        System.out.println("Faculty number must be exactly 5 digits!");
                        return false;
                }

                Specialty specialty = SpecialtyManager.getSpecialtyByName(t[2]);
                if(specialty==null)return false;

                char[] group = t[3].toCharArray();

                if(group.length!=2 || !Character.isDigit(group[0]) || !Character.isLetter(group[1]))
                {
                        System.out.println("Group must be 2 characters long a digit and next letter! Example: 2a");
                        return false;
                }

                String studentFullName = String.join(" ", Arrays.copyOfRange(t, 4, t.length));

                return StudentsManager.getInstance().enroll(facultyNumber,specialty,group,studentFullName);
        }
}
