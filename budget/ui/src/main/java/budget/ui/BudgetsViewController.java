package budget.ui;

import budget.core.Budgets;
import budget.core.Calculation;
import budget.core.Category;
import budget.utility.FileUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;

public class BudgetsViewController {

    @FXML
    private ListView<String> budgetsList;

    @FXML
    private Text budgetTitle;



    private Budgets budgets;

    private StartMenuController menuController = new StartMenuController();
    @FXML
    public void initialize() {
        Calculation calc1 = new Calculation();
        menuController = StartMenuController.getInstance();
        try {
            if (FileUtility.getLoad()) {
                FileUtility.readFromFile(calc1);
                String name = menuController.getCalcName();
                budgets = new Budgets(calc1);

            }

        } catch (IOException e) {
            e.getStackTrace();
        }



        ObservableList<String> listOfBudgets = FXCollections.observableArrayList();

        if (budgets != null) {
            for (Calculation calc : budgets.retrieveBudgets()) {
//                listOfBudgets.add("Name :" + calc.getName());
                for (Category category : calc.getCategoriesList()) {
                    listOfBudgets.add("Category: " + category.getCategoryName());
                    listOfBudgets.add("Total Amount: " + calc.getSum(category));
                }
            }

            // Calculate the total sum
            double totalSum = budgets.retrieveBudgets().stream()
                    .mapToDouble(calc -> calc.getCategoriesList().stream()
                            .mapToDouble(calc::getSum)
                            .sum())
                    .sum();
            listOfBudgets.add("Total Sum of All Budgets: " + totalSum);
        }

        budgetsList.setItems(listOfBudgets);

    }


}
