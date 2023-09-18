package core;

import java.util.ArrayList;

public class Category {

    private final String categoryName;
    private int amount;
    private ArrayList<Integer> addedAmounts = new ArrayList<>();

    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.amount = 0;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getAmount() {
        return amount;
    }

    public void addAmount(int amount) {
        this.addedAmounts.add(amount);
        this.amount += amount;
    }

    public ArrayList<Integer> getBudgetHistory() {
        return this.addedAmounts;
    }



    @Override
    public String toString() {
        return getCategoryName();
    }
}
