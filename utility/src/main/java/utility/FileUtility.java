package utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.Calculation;
import core.Category;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtility {

    public static boolean load;

    public static void writeToFile(Calculation calc) throws IOException{
        File folder = new File("utility/src/main/java/utility/savedBudget.json");

//        PrintWriter writer = new PrintWriter(new FileWriter(new File(folder, "savedBudget.txt")));
//
//
//        ArrayList<Category> tempArray = new ArrayList<>(calc.getCategoriesList());
//
//
//
//        for (Category category: tempArray) {
//            writer.println("CategoryName: " + category.getCategoryName());
//            writer.println("CategoryAmount: " + category.getAmount());
//            writer.println("------------------------------");
//        }
//
//        writer.close();
        Json.getObjectMapper().writeValue(folder, calc);
    }

    public static void readFromFile(Calculation calc) throws IOException {
        File file = new File("utility/src/main/java/utility/savedBudget.json");
        Calculation loadCalc = Json.getObjectMapper().readValue(file, Calculation.class);

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
            throw new RuntimeException(e);
        }
        try {
            readFromFile(calc);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }



}
