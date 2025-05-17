package Commands.Interfaces;

public interface FileSystemReceiver {
    String openFile(String input);

    String writeFile(String filename);

    String closeFile();
}
