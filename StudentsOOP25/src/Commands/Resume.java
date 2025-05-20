package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;
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
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        return sm.resume(t[1]);
    }
}
