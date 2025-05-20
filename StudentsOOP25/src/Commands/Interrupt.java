package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;
/**
 * The Interrupt command marks a student as having their studies interrupted.
 * <p>
 * This command updates a student's status to indicate that they have temporarily
 * suspended their academic activities.
 * </p>
 * <p>
 * Command format: interrupt <fn>
 * </p>
 */
public class Interrupt implements Command {
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        return sm.interrupt(t[1]);
    }
}
