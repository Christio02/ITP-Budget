package budget.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 * This class is responsible for representing a budget category.
 */
public class Category {

    /**
     * The name of the category.
     */
    private final String categoryName;
    /**
     * Current amount of the category.
     */
    private int amount;
    /**
     * List of added amounts to the category.
     */

    private ArrayList<Integer> addedAmounts = new ArrayList<>();

    /**
     * Creates a new Category with the specified name and initial amount of 0.
     *
     * @param pCategoryName The name of the category
     */
    @JsonCreator
    public Category(@JsonProperty("categoryName")final String pCategoryName) {
        this.categoryName = pCategoryName;
        this.amount = 0;
    }

    /**
     * Get the name of the category.
     *
     * @return The name of the category
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Get the current budget amount for the category.
     *
     * @return The current budget amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Add the specified amount to the category.
     *
     * @param pAmount The amount to add to the category
     */
    public void addAmount(final int pAmount) {
        this.addedAmounts.add(pAmount);
        this.amount += pAmount;
    }

    /**
     * Get the history of added amounts to the category.
     *
     * @return List of added amounts
     */
    public ArrayList<Integer> getBudgetHistory() {
        return this.addedAmounts;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return String representation of the object
     */
    @Override
    public String toString() {
        return getCategoryName();
    }
}
