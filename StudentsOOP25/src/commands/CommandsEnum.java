package commands;

public enum CommandsEnum {
    ENROLL("enroll"),
    ADVANCE("advance"),
    CHANGE("change"),
    GRADUATE("graduate"),
    INTERRUPT("interrupt"),
    RESUME("resume"),
    PRINT("print"),
    PRINTALL("printall"),
    ENROLLIN("enrollin"),
    ADDGRADE("addgrade"),
    PROTOCOL("protocol"),
    REPORT("report"),
    OPEN("open"),
    CLOSE("close"),
    SAVE("save"),
    SAVEAS("saveas"),
    HELP("help"),
    EXIT("exit");

    private final String commandName;
    /**
     * Constructs a command enum with the given display text.
     *
     * @param commandName the phrase that triggers this command
     */
    CommandsEnum(String commandName) {
        this.commandName = commandName;
    }

    /**
     * Returns the string presented inside the enum's value
     *
     * @return string name of command
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Finds the enum constant that corresponds to the given input string.
     * The comparison ignores case.
     *
     * @param command the input string to match against the command display texts
     * @return the matching CommandsEnum constant if found; otherwise, null
     */
    public static CommandsEnum fromString(String command) {
        for (CommandsEnum cmd : CommandsEnum.values()) {
            if (cmd.commandName.equalsIgnoreCase(command)) {
                return cmd;
            }
        }
        return null;
    }
}
