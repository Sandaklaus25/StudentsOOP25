package commands;

import commands.interfaces.Command;
import models.FileManager;
import models.StudentsManager;
import exceptions.InsufficientArgumentsException;
/**
 * The Graduate command marks a student as having completed their studies.
 * <p>
 * This command updates a student's status to "hasGraduated" if they have successfully
 * completed all required courses and meet graduation criteria.
 * </p>
 * <p>
 * Command format: graduate <fn>
 * </p>
 */
public class Graduate implements Command {
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        return StudentsManager.getInstance().graduate(t[1]);
    }
}
