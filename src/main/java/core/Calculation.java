package core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Calculation {

    private Map<String, ArrayList<Integer>> categoriesAndAmount;

    public Calculation() {
        categoriesAndAmount = new HashMap<>();
    }

    public void addAmountToCategory(String category, int amount) {
        if (!checkAmountIscorrect(amount)) {
            throw new IllegalArgumentException("Cannot add non-positive values!");
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
        System.out.println(calc);
    }



}
