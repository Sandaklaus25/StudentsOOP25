package commands;

import commands.interfaces.Command;
import models.FileManager;
import exceptions.InsufficientArgumentsException;

import java.io.IOException;

/**
 * Command implementation for saving current data to a new file with specified name.
 * <p>
 * The SaveAs command creates a new file with the specified name and writes all
 * current student data to it. This allows users to create backups, alternative
 * versions, or save data to different locations while preserving the original file.
 * </p>
 *
 * @see Command
 * @see FileManager
 * @see Save
 *
 * <p><b>Command Format:</b> {@code saveas <filename>}</p>
 * <p><b>Example:</b> {@code saveas savefile.txt}</p>
 *
 * <p><b>Parameters:</b></p>
 * <ul>
 *   <li>{@code filename} - Name for the new file (can include path)</li>
 * </ul>
 *
 * <p><b>Prerequisites:</b></p>
 * <ul>
 *   <li>A file must be currently loaded with data</li>
 *   <li>Write permissions in target directory</li>
 *   <li>Valid filename format</li>
 * </ul>
 *
 * <p><b>Operation Details:</b></p>
 * <ul>
 *   <li>Creates new file with specified name</li>
 *   <li>Writes complete current dataset</li>
 *   <li>Original file remains unchanged</li>
 * </ul>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>Exactly 2 arguments required (command + filename)</li>
 *   <li>Filename must be valid for the file system</li>
 *   <li>Target directory must be writable</li>
 * </ul>
 *
 * <p><b>File Handling:</b></p>
 * <ul>
 *   <li>Creates new file if it doesn't exist</li>
 *   <li>Overwrites existing file if it exists</li>
 *   <li>Maintains data format compatibility</li>
 *   <li>Preserves all student information integrity</li>
 * </ul>
 *
 * <p><b>Use Cases:</b></p>
 * <ul>
 *   <li>Creating data backups</li>
 *   <li>Archiving academic records</li>
 *   <li>Distributing data copies</li>
 *   <li>Version control and history tracking</li>
 * </ul>
 *
 * <p><b>Important Notes:</b></p>
 * <ul>
 *   <li>Does not switch to the new file as current</li>
 *   <li>Original file remains the active working file</li>
 *   <li>Subsequent saves will still use original file</li>
 * </ul>
 */
public class SaveAs implements Command {
    /**
     *
     * @param t  an array of arguments required for the command
     * @param fm the {@link FileManager} instance to perform file-related operations
     * @return true or false depending on command execution
     * @throws InsufficientArgumentsException when incorrect number of arguments provided
     */
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Invalid number of arguments!");

        return fm.writeFile(t[1]);
    }
}
