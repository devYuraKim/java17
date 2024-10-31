import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        useFile("testfile.txt");
        usePath("pathfile.txt");
    }

    private static void useFile(String filename){
        File file = new File(filename);
        boolean fileExists = file.exists();
        System.out.printf("File '%s' %s%n", filename,
                fileExists ? "exists" : "does not exist");

        if(fileExists){
            System.out.printf("Deleting file [%s]%n", filename);
            //if file.delete() is true, then fileExists will be false and vice versa
            fileExists = !file.delete();
        }

        if (!fileExists) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("Created file [%s]%n", filename);
            if(file.canWrite()){
                System.out.println("Would write to file here");
            }
        }
    }

    private static void usePath(String filename){
        Path path = Path.of(filename);
        boolean fileExists = Files.exists(path);
        System.out.printf("File '%s' %s%n", filename,
                fileExists ? "exists" : "does not exist");

        if(fileExists){
            System.out.printf("Deleting file [%s]%n", filename);
            //if file.delete() is true, then fileExists will be false and vice versa
            try {
                Files.delete(path);
                fileExists = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!fileExists) {
            try {
                Files.createFile(path);
                System.out.printf("Created file [%s]%n", filename);
                if(Files.isWritable(path)) {
                    Files.writeString(path, """
                            WRITING TO A FILE
                            with Path and Files,
                            you can not only create and delete files
                            but also write to them.
                            """);
                }
                System.out.println("READING FROM THE FILE");
                System.out.println("=".repeat(40));
                System.out.println(">>>>>readString");
                System.out.print(Files.readString(path));
                System.out.println(">>>>>readAllLines.forEach");
                Files.readAllLines(path).forEach(System.out::println);
                System.out.println("=".repeat(40));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}