package budget.core;

import java.util.ArrayList;

/**
 * This class is responsible for the calculation of the budget.
 */
public class Calculation {

    /**
     * This is a list that stores categories.
     */
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
     * @param amount   The amount to add to the category
     * @throws IllegalArgumentException if the amount is negative
     */
    public void addAmountToCategory(final Category category, final int amount) {
        if (!checkAmountIsCorrect(amount)) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        category.addAmount(amount);
    }

    /**
     * Retrieves sum for a specific category.
     *
     * @param category The category for retrieving the sum
     * @return The sum of all amounts added to the specified category
     */
    public int getSum(final Category category) {
        return category.getAmount();
    }

    /**
     * Get a category by name.
     *
     * @param categoryName The name of the category to get
     * @return The category with the specified name or null if not found
     */
    public Category getCategory(final String categoryName) {
        return this.categoriesList.stream()
                .filter(cat -> cat.getCategoryName().equals(categoryName))
                .findAny()
                .orElse(null);
    }

    /**
     * Get the total sum of all categories.
     *
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
     * Check if the amount is positive.
     *
     * @param amount The amount to check
     * @return True if the amount is positive, false otherwise
     */
    private boolean checkAmountIsCorrect(final int amount) {
        return amount >= 0;
    }

    /**
     * Get the list of categories.
     *
     * @return The list of categories
     */
    public ArrayList<Category> getCategoriesList() {
        return categoriesList;
    }


}
