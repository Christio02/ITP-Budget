package budget.springrest.repository;


import budget.core.Calculation;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * The '@Repository' annotation is used to mark this class as a Data Access Object (DAO) or repository.
 * It indicates that this class interacts with a data source, and it enables Spring's exception translation
 * for handling data access-related exceptions. Additionally, '@Repository' facilitates automatic
 * bean creation and is a best practice for code organization.
 */

@Repository
public class CalculationModelList {

    private ArrayList<Calculation> budgets = new ArrayList<>();

    public CalculationModelList() {
        budgets.add(new Calculation("test"));
        budgets.add(new Calculation("test2"));
    }

    //GET
    public ArrayList<Calculation> findAll() {
        return new ArrayList<>(this.budgets);
    }

    public Calculation findByName(String name) {
        return budgets.stream().filter(calculation -> calculation.getName().equals(name))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No budget found by that name!"));
    }

    public Calculation create(Calculation calc) {
        budgets.add(calc);
        return calc;
    }

    public void update(Calculation calculation) {
        Calculation existingCalc = budgets
                .stream().filter(c -> c.getName().equals(calculation.getName()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Budget you are trying to update not found"));
        int index = findBudgetIndex(existingCalc.getName());
        budgets.set(index, calculation);
    }

    public void delete(String name) {
        budgets.removeIf(c -> c.getName().equals(name));

    }
    public int findBudgetIndex(String name) {
        for (int i = 0; i < budgets.size(); i++) {
            if (budgets.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public boolean hasBudget(String name) {
        return findBudgetIndex(name) > 0;
    }

    public boolean checkValidBudgetName(String name) {
        return !name.isEmpty();
    }


    /*
    returns if a budget with the same name already exists in the list
     */
    public boolean checkDuplicate(String name) {
        for (Calculation calc : this.budgets) {
            if (calc.getName().equals(name))
                return true;
        }
        return false;
    }

    // POST
    public void addBuget(Calculation calc) {

        if (checkDuplicate(calc.getName())) {
            throw new IllegalArgumentException("A budget with that name already exists!");
        }

        else if (checkValidBudgetName(calc.getName())) {
            this.budgets.add(calc);
        }

        throw new IllegalArgumentException("Invalid budget name!");
    }


    //GET
    public Calculation getBudgetByName(String name) {
        return this.budgets.get(findBudgetIndex(name));
    }




}
