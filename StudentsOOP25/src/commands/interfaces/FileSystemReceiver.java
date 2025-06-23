package commands.interfaces;

public interface FileSystemReceiver {
    boolean openFile(String input);

    boolean writeFile(String filename);

    String closeFile();
}
