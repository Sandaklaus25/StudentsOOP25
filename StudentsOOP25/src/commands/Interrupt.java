package commands;

import commands.interfaces.Command;
import models.FileManager;
import models.StudentsManager;
import exceptions.InsufficientArgumentsException;
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
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        return StudentsManager.getInstance().interrupt(t[1]);
    }
}
