package utility;

import core.Calculation;
import core.Category;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtility {

    public static boolean load;

    public static void writeToFile(Calculation calc) throws IOException{
        String currentDirectory = System.getProperty("user.dir");
        String filePath = currentDirectory + "/../utility/src/main/resources/savedBudget.txt";

        File folder = new File(filePath);

        PrintWriter writer = new PrintWriter(new FileWriter(folder));


        ArrayList<Category> tempArray = new ArrayList<>(calc.getCategoriesList());



        for (Category category: tempArray) {
            writer.println("CategoryName: " + category.getCategoryName());
            writer.println("CategoryAmount: " + category.getAmount());
            writer.println("------------------------------");
        }
        
        writer.close();
    }

    public static void readFromFile(Calculation calculation) throws FileNotFoundException {
//        Calculation calc = new Calculation();
        String currentDirectory = System.getProperty("user.dir");
        String filePath = currentDirectory + "/../utility/src/main/resources/savedBudget.txt";

        Scanner scanner = new Scanner(new File(filePath));

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();
            if (line.equals("------------------------------")) {
                continue;
            }
            String catName = line.split(": ")[1];
            String amountString = scanner.nextLine();
            int amount = Integer.parseInt(amountString.split(": ")[1]);

            Category cat = calculation.getCategory(catName);

            if (cat != null) {
                calculation.addAmountToCategory(cat, amount);
//            System.out.println(catName + " " + amount)
            }


        }
//        System.out.println(calc.getTotalSum());



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
            readFromFile(calc2);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}
