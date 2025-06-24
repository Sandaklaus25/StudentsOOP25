package commands;

import interfaces.Command;
import models.FileManager;
import exceptions.InsufficientArgumentsException;

/**
 * Command implementation for persisting current data to the currently loaded file.
 * <p>
 * The Save command writes all current student data, including any modifications,
 * back to the file that was originally opened. This ensures that changes made
 * during the session are preserved and will be available in future sessions.
 * </p>
 *
 * @see Command
 * @see FileManager
 * @see SaveAs
 *
 * <p><b>Command Format:</b> {@code save}</p>
 * <p><b>Example:</b> {@code save}</p>
 *
 * <p><b>Parameters:</b> None</p>
 *
 * <p><b>Prerequisites:</b></p>
 * <ul>
 *   <li>A file must be currently loaded</li>
 *   <li>File must be writable</li>
 *   <li>System must have write permissions</li>
 * </ul>
 *
 * <p><b>Operation Details:</b></p>
 * <ul>
 *   <li>Saves to the same file that was opened</li>
 *   <li>Overwrites the existing file content</li>
 *   <li>Preserves original file name and location</li>
 *   <li>Creates backup of current state</li>
 * </ul>
 *
 * <p><b>Data Saved:</b></p>
 * <ul>
 *   <li>All student records and information</li>
 *   <li>Academic enrollments and grades</li>
 *   <li>Student status changes</li>
 *   <li>Discipline assignments</li>
 *   <li>All modifications since file was loaded</li>
 * </ul>
 *
 * <p><b>Error Conditions:</b></p>
 * <ul>
 *   <li>No file currently loaded</li>
 *   <li>File is read-only or locked</li>
 *   <li>Insufficient disk space</li>
 *   <li>File system permissions issues</li>
 * </ul>
 *
 * <p><b>Best Practices:</b></p>
 * <ul>
 *   <li>Save frequently to prevent data loss</li>
 *   <li>Save before performing risky operations</li>
 *   <li>Verify save success before continuing</li>
 * </ul>
 */
public class Save implements Command {
    /**
     *
     * @param t  an array of arguments required for the command
     * @param fm the {@link FileManager} instance to perform file-related operations
     * @return true or false depending on command execution
     * @throws InsufficientArgumentsException when incorrect number of arguments provided
     */
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        return fm.writeFile(fm.getLoadedFile().getName());
    }
}
