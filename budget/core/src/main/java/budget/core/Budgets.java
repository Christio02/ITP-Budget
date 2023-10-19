package budget.core;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Array;
import java.net.CacheRequest;
import java.util.ArrayList;


/**
 * This class represents a collection of budgets.
 */
public class Budgets {
    /**
     * List of calculation objects.
     */
    @JsonProperty("calculations")
    private ArrayList<Calculation> budgetsList = new ArrayList<>();

    /**
     * Constructs a new Budgets object and adds an initial Calculation.
     *
     * @param calculations ArrayList of calculation to add to the Budgets.
     */
    public Budgets(@JsonProperty("calculations") ArrayList<Calculation> calculations) {
        if (!calculations.isEmpty()) {
            addBudget(calculations);
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
    @JsonProperty("calculations")
    public ArrayList<Calculation> retrieveBudgets() {
        return new ArrayList<>(this.budgetsList);
    }
    /**
     * Adds a Calculation to the list of budgets if it's not already present.
     *
     * @param calc The Calculation to add.
     */
    @JsonCreator
    @JsonProperty("calculations")
    public void addBudget(ArrayList<Calculation> calc) {
        if (!retrieveBudgets().containsAll(calc)) {
            this.budgetsList.addAll(calc);
        }
    }



}
