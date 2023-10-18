package budget.core;


import java.util.ArrayList;


/**
 * This class represents a collection of budgets.
 */
public class Budgets {
    /**
     * List of calculation objects.
     */
    private ArrayList<Calculation> budgetsList = new ArrayList<>();

    /**
     * Constructs a new Budgets object and adds an initial Calculation.
     *
     * @param calc The Calculation to add to the Budgets.
     */
    public Budgets(final Calculation calc) {
        if (calc != null) {
            addBudget(calc);
        } else {
            throw new
                    IllegalArgumentException("Cannot add non-existent object!");
        }
    }

    /**
     * Retrieves a copy of the list of budgets.
     *
     * @return A copy of the list of budgets.
     */
    public ArrayList<Calculation> retrieveBudgets() {
        return new ArrayList<>(this.budgetsList);
    }
    /**
     * Adds a Calculation to the list of budgets if it's not already present.
     *
     * @param calc The Calculation to add.
     */
    public void addBudget(final Calculation calc) {
        if (!retrieveBudgets().contains(calc)) {
            this.budgetsList.add(calc);
        }
    }


}
