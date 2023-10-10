package budget.core;

import java.util.ArrayList;

/**
 * This class is responsible for the calculation of the budget.
 */
public class Calculation {

    private final ArrayList<Category> categoriesList = new ArrayList<>();

    /**
     * Creates a new Calculation object.
     */
    public Calculation() {
        this.categoriesList.add(new Category("Food"));
        this.categoriesList.add(new Category("Transportation"));
        this.categoriesList.add(new Category("Entertainment"));
        this.categoriesList.add(new Category("Clothing"));
        this.categoriesList.add(new Category("Other"));
    }

    /**
     * Adds the specified amount to the specified category.
     *
     * @param category The category to add the amount to
     * @param amount The amount to add to the category
     * @throws IllegalArgumentException if the amount is negative
     */
    public void addAmountToCategory(Category category, int amount) {
        if (!checkAmountIscorrect(amount)) {
            throw new IllegalArgumentException("Cannot add non-positive values!");
        }

        category.addAmount(amount);

    }

    public int getSum(Category category) {
        return category.getAmount();
    }


    /**
     * @param CategoryName The name of the category to get
     * @return The category with the specified name
     */
    public Category getCategory(String CategoryName) {
        return this.categoriesList.stream().filter(cat -> cat.getCategoryName().
                equals(CategoryName)).findAny().orElse(null);
    }

    /**
     * @return The total sum of all categories
     */
    public int getTotalSum() {

        int sum = 0;

        for (Category cat : categoriesList) {
            sum += cat.getAmount();
        }

        return sum;

    }


    /**
     * @param amount The amount to check
     * @return True if the amount is positive, false otherwise
     */
    private boolean checkAmountIscorrect(int amount) {
        return amount >= 0;
    }

    public ArrayList<Category> getCategoriesList() {
        return categoriesList;
    }


    public static void main(String[] args) {
        Calculation calc = new Calculation();
        Category food = calc.getCategory("Food");
        food.addAmount(200);
        System.out.println(calc.getSum(food));


    }

}
