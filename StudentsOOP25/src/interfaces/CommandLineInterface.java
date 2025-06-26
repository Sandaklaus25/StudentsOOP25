package interfaces;


/**
 * An interface representing a command-line interface capable of processing input commands.
 */
public interface CommandLineInterface {
    /**
     * Processes the given input string as a command.
     *
     * @param input the input command as a string
     * @return {@code true} if the input was successfully handled, {@code false} otherwise
     */
    boolean processUserInput(String input);
}
