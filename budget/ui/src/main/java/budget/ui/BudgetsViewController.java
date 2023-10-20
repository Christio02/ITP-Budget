package budget.ui;

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

    private StartMenuController menuController = new StartMenuController();
    @FXML
    public void initialize() {
        Calculation calc1 = new Calculation();
        menuController = StartMenuController.getInstance();
        try {
            if (FileUtility.getLoad()) {
                FileUtility.readFromFile(calc1);
                String name = menuController.getCalcName();
                budgetTitle.setText(name);
            }

        } catch (IOException e) {
            e.getStackTrace();
        }



        ObservableList<String> listOfBudgets = FXCollections.observableArrayList();

        budgetsList.setItems(listOfBudgets);

    }


}
