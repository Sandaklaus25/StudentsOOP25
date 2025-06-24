package interfaces;
/**
 * An interface representing a receiver in the command pattern for file system operations.
 * Implementations of this interface define how file-related commands are executed.
 */
public interface FileSystemReceiver {
    /**
     * Opens a file or resource based on the provided input.
     *
     * @param input the path or identifier of the file to open
     * @return {@code true} if the file was successfully opened, {@code false} otherwise
     */
    boolean openFile(String input);
    /**
     * Writes data to the specified file.
     *
     * @param filename the name of the file to write to
     * @return {@code true} if the write operation was successful, {@code false} otherwise
     */
    boolean writeFile(String filename);
    /**
     * Closes the currently open file or resource.
     *
     * @return {@code true} if the file was successfully closed, {@code false} otherwise
     */
    boolean closeFile();
}
