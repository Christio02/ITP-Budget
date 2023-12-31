package budget.springrest.repository;

import budget.core.Calculation;
import budget.utility.FileUtility;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;

@Repository
public class CalculationRepositoryList {
    private static String FILE_PATH = "../../utility/src/main/resources/budget/utility/savedBudget.json";



    private ArrayList<Calculation> budgets = new ArrayList<>();

    public CalculationRepositoryList() {
        // Load data from the file when the application opens
        loadDataFromFile();
    }

    public CalculationRepositoryList(CalculationRepositoryList repositoryList) {
        this.budgets = new ArrayList<>();
        for (Calculation calculation : repositoryList.budgets) {
            this.budgets.add(new Calculation(calculation));
        }
    }

    public ArrayList<Calculation> findAll() {
        return new ArrayList<>(this.budgets);
    }

    public Calculation findByName(String name) {
        return budgets.stream()
                .filter(calculation -> calculation.getName().equals(name))
                .findAny()
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
        saveDataToFile();
    }

    private int findBudgetIndex(String name) {
        for (int i = 0; i < budgets.size(); i++) {
            if (budgets.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public void clearBudgets() {
        this.budgets.clear();
        saveDataToFile();
    }


    public ArrayList<Calculation> getBudgets() {
        return new ArrayList<>(this.budgets);
    }

    // Method to save data to the file
    private void saveDataToFile() {
        try {
            FileUtility.writeToFile(this.budgets, FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load data from the file
    private void loadDataFromFile() {
        try {
            FileUtility.readFile(this.budgets, FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
