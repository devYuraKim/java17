import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String filename = "testing.csv";
        testFile2(filename);
        File file = new File(filename);
        if(!file.exists()){
            System.out.println("The file doesn't exist - Quitting Application");
            return;
        }
    }

    /*Closing resources with traditional try-catch block*/
    private static void testFile(String filename){
        Path path = Paths.get(filename);
        FileReader reader = null;
        try {
            //List<String> lines = Files.readAllLines(path);
            reader = new FileReader(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Finally clause");
        }
        System.out.println("The file exists");
    }

    /*Auto-closing resources with try-with-resources block*/
    private static void testFile2(String filename){
        try (FileReader reader = new FileReader(filename)) {
        } catch (FileNotFoundException e) {
            System.out.println("File [" + filename + "] doesn't exist");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            System.out.println("Finally clause");
        }
        System.out.println("The file exists");
    }
}