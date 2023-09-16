package core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Calculation {

    private Map<String, ArrayList<Integer>> categoriesAndAmount;
    private ArrayList<String> validCategories = new ArrayList<>(Arrays.asList("Food", "Transportation", "Entertainment", "Clothing", "Other"));

    public Calculation() {
        categoriesAndAmount = new HashMap<>();
    }

    public void addAmountToCategory(String category, int amount) {
        if (!checkAmountIscorrect(amount))  {
            throw new IllegalArgumentException("Cannot add non-positive values!");
        }

        if (!checkValidCategory(category)) {
            throw new IllegalArgumentException("Invalid category!");
        }


            if (categoriesAndAmount.containsKey(category)) {
                ArrayList<Integer> tempArray = this.categoriesAndAmount.get(category);
                tempArray.add(amount);
                this.categoriesAndAmount.put(category, tempArray);
            } else {
                ArrayList<Integer> newCategoryArray = new ArrayList<>();
                newCategoryArray.add(amount);
                this.categoriesAndAmount.put(category, newCategoryArray);
            }




    }

    private int getSum(String category) {
        if (!checkValidCategory(category)) {
            throw new IllegalArgumentException("Invalid category!");
        }

        if (!categoriesAndAmount.containsKey(category)) {
            return 0;
        }

        ArrayList<Integer> tempArray = this.categoriesAndAmount.get(category);
        int sum = 0;
        for (Integer categoryValue : tempArray) {
            sum += categoryValue;
        }
        return sum;
    }

    public String getCategory(int index) {
        return validCategories.get(index);
    }

    private int getTotalSum() {
        int sum = 0;
        for (String category : categoriesAndAmount.keySet()) {
           sum += getSum(category);
        }


        return sum;
    }

    private boolean checkValidCategory(String category) {
        return validCategories.contains(category);
    }

    private boolean checkAmountIscorrect(int amount) {
        return amount >= 0;
    }

    @Override
    public String toString() {
        return this.categoriesAndAmount.toString();
    }

    public static void main(String[] args) {
        Calculation calc= new Calculation();
        calc.addAmountToCategory("Food",200);
        calc.addAmountToCategory("Food",200);
        calc.addAmountToCategory("Food",200);
        calc.addAmountToCategory("Transportation", 300);

        System.out.println(calc);
        System.out.println(calc.getSum("Food"));
        System.out.println(calc.getTotalSum());
    }



}
