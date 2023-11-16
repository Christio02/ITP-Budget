package budget.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is responsible for the calculation of the budget.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Calculation {

    /**
     * This is a list that stores categories.
     */
    private ArrayList<Category> categoriesList = new ArrayList<>();

    /**
     * Name of calculation.
     */
    private String name;

    /**
     * Creates a new Calculation object.
     */
    @JsonCreator
    public Calculation() {
        this.categoriesList.add(new Category("Food"));
        this.categoriesList.add(new Category("Transportation"));
        this.categoriesList.add(new Category("Entertainment"));
        this.categoriesList.add(new Category("Clothing"));
        this.categoriesList.add(new Category("Other"));
        name = null;
    }

    /**
     * Creates a new Calculation object, with given name.
     * @param newName the name of calculation.
     */
    @JsonCreator
    public Calculation(final String newName) {
        this.categoriesList.add(new Category("Food"));
        this.categoriesList.add(new Category("Transportation"));
        this.categoriesList.add(new Category("Entertainment"));
        this.categoriesList.add(new Category("Clothing"));
        this.categoriesList.add(new Category("Other"));
        this.name = newName;

    }

    /**
     * A constructor that creates a deep copy of object (spotbugs error).
     * @param  other calculation
     *
     */
    public Calculation(final Calculation other) {
        this.name = other.getName();
        this.categoriesList = new ArrayList<>();
        this.categoriesList.addAll(other.getCategoriesList());
    }

    /**
     * Sets current name of budget.
     * @param newName sets name
     */
    public void setName(final String newName) {
        this.name = newName;
    }

    /**
     * Gets current name.
     * @return String name.
     */
    public String getName() {
        return this.name;
    }




    /**
     * Adds the specified amount to the specified category.
     *
     * @param category The category to add the amount to.
     * @param amount   The amount to add to the category.
     * @throws IllegalArgumentException if the amount is negative.
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
     * @param amount The amount to check.
     * @return True if the amount is positive, false otherwise.
     */
    private boolean checkAmountIsCorrect(final int amount) {
        return amount >= 0;
    }

    /**
     * Get the list of categories.
     *
     * @return The list of categories.
     */
    public ArrayList<Category> getCategoriesList() {
        return new ArrayList<>(categoriesList);
    }

    /**
     * Equals method.
     * @param obj other object.
     * @return boolean if other equal is true or false.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Calculation calculation = (Calculation) obj;
        // Use Objects.equals for fields that could be null.
        return Objects.equals(name, calculation.getName());
    }
    /**
    Works same as regular hashCode, implemented so that equals have reliable method.
     @return hash of calculation name.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * A custom toString for object.
     * @return string of object.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Calculation Name: ").append(name).append("\n");
        result.append("Total Sum: ").append(getTotalSum()).append("\n");
        result.append("Categories:\n");

        for (Category category : categoriesList) {
            result.append(category.getCategoryName()).append(": ").append(category.getAmount()).append("\n");
        }

        return result.toString();
    }








}
