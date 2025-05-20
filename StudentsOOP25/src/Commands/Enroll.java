package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.Specialty;
import Models.SpecialtyManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;

import java.util.Arrays;
/**
 * The Enroll command registers a new student into the system.
 * <p>
 * This command processes student registration with the following parameters:
 * <ul>
 *   <li>Faculty number - Must be exactly 5 digits but is saved as String</li>
 *   <li>Specialty/Program - The academic program the student is enrolling in</li>
 *   <li>Group - A two-character code consisting of a digit followed by a letter</li>
 *   <li>Full name - The student's complete name</li>
 * </ul>
 * </p>
 * <p>
 * Command format: enroll <fn> <program> <group> <name>
 * </p>
 */
public class Enroll implements Command{

        @Override
        public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
                if(t.length!=5)
                        throw new InsufficientArgumentsException ("Има грешка при въведения брой аргументи!");
                String studentFullName = String.join(" ", Arrays.copyOfRange(t, 4, t.length));;
                Specialty specialty;
                try {
                        specialty = SpecialtyManager.getSpecialtyByName(t[2]);
                }catch (ClassNotFoundException e)
                {
                        return e.getMessage();
                }

                char[] group = t[3].toCharArray();
                String facultyNumber = t[1];
                try {
                        if(group.length!=2 || !Character.isLetter(group[1]) || !Character.isDigit(group[0]))
                                throw new IllegalArgumentException("Групата може да е само 2 символа от които първото е число второто е буква. Пример: 2а");

                }catch (IllegalArgumentException e){
                        return e.getMessage();
                }
                try{
                        if(t[1].length()!=5)throw new NumberFormatException();
                        Integer.parseInt(t[1]);
                }catch (NumberFormatException e){return "Факултетения номер трябва да се състои от точно 5 цифри";}

                return sm.enroll(facultyNumber,specialty,group,studentFullName);
        }
}
