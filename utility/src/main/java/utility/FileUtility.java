package utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Calculation;
import core.Category;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtility {

    public static boolean load;
    private static String pathname = "utility/src/main/resources/budget.json";

    public static void writeToFile(Calculation calc) throws IOException{
        // create file



        try{
            Json.getObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File(pathname), calc);
        } catch(IOException e) {
            System.out.println("An error occurred while writing to file: " + e.getMessage());
            e.printStackTrace();
        }



    }

    public static void readFromFile(Calculation calc) throws IOException {



        Calculation loadCalc = Json.getObjectMapper().readValue(new File(pathname), Calculation.class);

        calc.getCategoriesList().clear();

        for (Category cat : loadCalc.getCategoriesList()) {
            calc.getCategoriesList().add(cat);
        }

        }


    public static boolean getLoad() {
        return load;
    }
    public static void main(String[] args) {
        Calculation calc = new Calculation();
        Category food = calc.getCategory("Food");
        Category transport = calc.getCategory("Transportation");

        calc.addAmountToCategory(food, 200);
        calc.addAmountToCategory(food, 300);
        calc.addAmountToCategory(transport, 500);
        calc.addAmountToCategory(transport, 2000);

        Calculation calc2 = new Calculation();
        try {
            writeToFile(calc);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        try {
            readFromFile(calc);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }



}
