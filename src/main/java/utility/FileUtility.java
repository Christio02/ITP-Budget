package utility;

import core.Calculation;
import core.Category;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtility {

    public static void writeToFile(Calculation calc) throws IOException{
        File folder = new File("src/main/resources");

        PrintWriter writer = new PrintWriter(new FileWriter(new File(folder, "savedBudget.txt")));

        ArrayList<Category> tempArray = new ArrayList<>(calc.getCategoriesList());



        for (Category category: tempArray) {
            writer.println("CategoryName: " + category.getCategoryName());
            writer.println("CategoryAmount: " + category.getAmount());
            writer.println("------------------------------");
        }
        
        writer.close();
    }

    public static void readFromFile() throws FileNotFoundException {
        Calculation calc = new Calculation();
        Scanner scanner = new Scanner(new File("src/main/resources/savedBudget.txt"));

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();
            if (line.equals("------------------------------")) {
                continue;
            }
            String catName = line.split(": ")[1];
            String amountString = scanner.nextLine();
            int amount = Integer.parseInt(amountString.split(": ")[1]);

            Category cat = calc.getCategory(catName);

            if (cat != null) {
                calc.addAmountToCategory(cat, amount);
//            System.out.println(catName + " " + amount)
            }


        }
        System.out.println(calc.getTotalSum());



    }

    public static void main(String[] args) {
        Calculation calc = new Calculation();
        Category food = calc.getCategory("Food");
        Category transport = calc.getCategory("Transportation");

        calc.addAmountToCategory(food, 200);
        calc.addAmountToCategory(food, 300);
        calc.addAmountToCategory(transport, 500);
        calc.addAmountToCategory(transport, 2000);


        try {
            writeToFile(calc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            readFromFile();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}
