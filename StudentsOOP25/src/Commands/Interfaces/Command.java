package Commands.Interfaces;

import Models.FileManager;
import Exceptions.InsufficientArgumentsException;
import Models.StudentsManager;

public interface Command {
    String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException;
}
