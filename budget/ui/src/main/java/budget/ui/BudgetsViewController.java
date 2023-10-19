package budget.ui;

import budget.core.Budgets;
import budget.core.Calculation;
import budget.core.Category;
import budget.utility.FileUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;

public class BudgetsViewController {

    @FXML
    private ListView<String> budgetsList;


    @FXML
    private Button returnMenuBtn;

    private static BudgetsViewController instance;
    public static BudgetsViewController getInstance() {
        return instance;
    }



    private Budgets budgets;

    private StartMenuController menuController = new StartMenuController();
    @FXML
    public void initialize() {
        instance = this;
        menuController = StartMenuController.getInstance();

        try {
            if (FileUtility.getLoad()) {
                budgets = FileUtility.readFile();
                System.out.println(budgets.retrieveBudgets());
            }
        } catch (IOException e) {
            e.getStackTrace();
        }

        ObservableList<String> listOfBudgets = FXCollections.observableArrayList();

        if (budgets != null) {
            for (Calculation calc : budgets.retrieveBudgets()) {
                String budgetName = calc.getName(); // Get the name of the current budget
                listOfBudgets.add("Name: " + budgetName);
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

    @FXML
    private void loadMainMenu(ActionEvent event) throws Exception {
        ChangeScene.changeToScene(getClass(), event, "startmenu-fxml.fxml");
    }


}
