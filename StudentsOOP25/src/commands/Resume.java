package commands;

import commands.interfaces.Command;
import models.FileManager;
import models.StudentsManager;
import exceptions.InsufficientArgumentsException;
/**
 * The Resume command restores the academic rights of a student who has been interrupted.
 * <p>
 * This command reactivates the status of a student who previously interrupted
 * their studies, allowing them to continue their academic program.
 * </p>
 * <p>
 * Command format: resume <fn>
 * </p>
 */
public class Resume implements Command {
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        return StudentsManager.getInstance().resume(t[1]);
    }
}
