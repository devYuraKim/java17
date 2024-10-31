import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        //Create a Path instance which references to the specified file path
        Path path = Path.of("more/depth/added/another/level/added/testing.txt");
        printPathInfo(path);
        logStatement(path);
        extraInfo(path);
    }//main method

    private static void printPathInfo(Path path){
        System.out.println("Path: " + path);
        System.out.println("Filename: " + path.getFileName());
        System.out.println("Parent: " + path.getParent());

        //Get absolute path and assign that to a variable
        Path absolutePath = path.toAbsolutePath();
        System.out.println("Absolute path: " + absolutePath);
        System.out.println("Absolute path root: " + absolutePath.getRoot());
        System.out.println("Root: " + path.getRoot());
        System.out.println("isAbsolute: " + path.isAbsolute());
        System.out.println(absolutePath.getRoot());
        //File hierarchy
        int i = 1;
        //Convert the path name elements into an iterator
        var it = path.toAbsolutePath().iterator();
        while(it.hasNext()){
            System.out.println(".".repeat(i++) + " " +it.next());
        }
        System.out.println("=".repeat(50));
        //Returns the number of name elements in the path
        int pathParts = absolutePath.getNameCount();
        for(int j = 0 ; j < pathParts; j++){
            //Retrieve the j-th name element of the path
            System.out.println(".".repeat(j+1) + " " + absolutePath.getName(j));
        }
    }//printPathInfo method

    private static void logStatement(Path path){
        try{
            Path parent = path.getParent();
            if(!Files.exists(parent)) {
                //Files.createDirectory(parent);
                Files.createDirectories(parent);
            }
            if(!Files.exists(path)){
                Files.createFile(path);
            }
            //Files.writeString will create the file automatically
            Files.writeString(path, Instant.now() + "\n",
                    StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }//logStatement method

    private static void extraInfo(Path path){
        try{
            var attributes = Files.readAttributes(path, "*");
            //Returns a SET whose elements are the MAP's entries(Key-Value pairs)
            attributes.entrySet().forEach(System.out::println);
            System.out.println(Files.probeContentType(path));
        }catch(IOException e){
            e.printStackTrace();
        }

    }//extraInfo method

}//Main