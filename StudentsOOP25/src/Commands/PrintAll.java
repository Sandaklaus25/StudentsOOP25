package Commands;

import Commands.Interfaces.Command;
import Exceptions.InsufficientArgumentsException;
import Models.*;

public class PrintAll implements Command {
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=3)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        Specialty specialty;
        byte course;
        try {
             specialty = SpecialtyManager.getSpecialtyByString(t[1]);
             course = Byte.parseByte(t[2]);
        }
        catch (ClassNotFoundException | NumberFormatException e)
        {
            return e.getMessage();
        }
        return sm.printAll(specialty,course);
    }
}
