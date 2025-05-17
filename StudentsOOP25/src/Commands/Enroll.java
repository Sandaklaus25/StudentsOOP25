package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.Specialty;
import Models.SpecialtyManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;

import java.util.Arrays;

public class Enroll implements Command{
        @Override
        public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
                if(t.length<5)
                        throw new InsufficientArgumentsException ("Има грешка при въведения брой аргументи!");
                String studentFullName = String.join(" ", Arrays.copyOfRange(t, 4, t.length));;
                Specialty specialty;
                try {
                        specialty = SpecialtyManager.getSpecialtyByString(t[2]);
                }catch (ClassNotFoundException e)
                {
                        return e.getMessage();
                }

                char[] group = t[3].toCharArray();
                try {
                        if(group.length!=2 || !Character.isLetter(group[1]) || !Character.isDigit(group[0]))
                                throw new IllegalArgumentException("Групата може да е само 2 символа от които първото е число второто е буква. Пример: 2а");
                }catch (IllegalArgumentException e){
                        return e.getMessage();
                }

                String facultyNumber = t[1];

                return sm.enroll(facultyNumber,specialty,group,studentFullName);
        }
}
