package budget.springrest.repository;

import budget.core.Calculation;
import budget.utility.FileUtility;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;

@Repository
public class CalculationRepositorylList {
    private static final String DATA_FILE_PATH = "classpath:budget/utility/savedBudget.json";

    private ArrayList<Calculation> budgets = new ArrayList<>();

    public CalculationRepositorylList() {
        // Load data from the file when the application opens
        loadDataFromFile();
    }

    public ArrayList<Calculation> findAll() {
        return new ArrayList<>(this.budgets);
    }

    public Calculation findByName(String name) {
        return budgets.stream()
                .filter(calculation -> calculation.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No budget found by that name!"));
    }

    public Calculation create(Calculation calc) {
        if (calc.getName().equals("null")) {
            throw new IllegalArgumentException("Invalid budget name!");
        }
        budgets.add(calc);
        saveDataToFile(); // Save data to the file after creating
        return calc;
    }

    public void update(Calculation calculation) {
        Calculation existingCalc = budgets
                .stream()
                .filter(c -> c.getName().equals(calculation.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Budget you are trying to update not found"));

        int index = findBudgetIndex(existingCalc.getName());
        budgets.set(index, calculation);
        saveDataToFile(); // Save data to the file after updating
    }

    public void delete(String name) {
        budgets.removeIf(c -> c.getName().equals(name));
        FileUtility.deleteBudget(name, this.budgets);
    }

    private int findBudgetIndex(String name) {
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

    public boolean checkDuplicate(String name) {
        for (Calculation calc : this.budgets) {
            if (calc.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // Method to save data to the file
    private void saveDataToFile() {
        try {
            FileUtility.writeToFile(budgets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load data from the file
    private void loadDataFromFile() {
        try {
            FileUtility.readFile(this.budgets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
