package core;

import java.util.ArrayList;

public class Calculation {

    private final ArrayList<Category> categoriesList = new ArrayList<>();

    public Calculation() {
        this.categoriesList.add(new Category("Food"));
        this.categoriesList.add(new Category("Transportation"));
        this.categoriesList.add(new Category("Entertainment"));
        this.categoriesList.add(new Category("Clothing"));
        this.categoriesList.add(new Category("Other"));
    }

    public void addAmountToCategory(Category category, int amount) {
        if (!checkAmountIscorrect(amount)) {
            throw new IllegalArgumentException("Cannot add non-positive values!");
        }

        category.addAmount(amount);

    }

    public int getSum(Category category) {
        return category.getAmount();
    }


    public Category getCategory(String CategoryName) {
        return this.categoriesList.stream().filter(cat -> cat.getCategoryName().
                equals(CategoryName)).findAny().orElse(null);
    }

    public int getTotalSum() {

        int sum = 0;

        for (Category cat : categoriesList) {
            sum += cat.getAmount();
        }

        return sum;

    }


    private boolean checkAmountIscorrect(int amount) {
        return amount >= 0;
    }

    public ArrayList<Category> getCategoriesList() {
        return categoriesList;
    }


    public static void main(String[] args) {
        Calculation calc = new Calculation();


    }

}
