package commands;

import commands.interfaces.Command;
import exceptions.InsufficientArgumentsException;
import models.*;
/**
 * The PrintAll command print information of all students in a specific specialty and year.
 * <p>
 * This command displays information about all students enrolled in a particular
 * academic program and year of study.
 * </p>
 * <p>
 * Command format: printall <program> <year>
 * </p>
 */
public class PrintAll implements Command {
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=3)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        Specialty specialty = SpecialtyManager.getSpecialtyByName(t[1]);
        if(specialty==null)return false;

        byte course;
        try {

             course = Byte.parseByte(t[2]);
        }
        catch (NumberFormatException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        return StudentsManager.getInstance().printAll(specialty,course);
    }
}
