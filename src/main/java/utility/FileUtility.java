package utility;

import java.io.*;

public class FileUtility {

    public static void writeToFile() throws IOException{
        File folder = new File("src/main/resources");

        PrintWriter writer = new PrintWriter(new FileWriter(new File(folder, "savedBudget.txt")));

        writer.println("Hello world");
        writer.close();
    }

    public static void main(String[] args) {
        try {
            writeToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
