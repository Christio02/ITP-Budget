package ui;
import core.Calculation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utility.FileUtility;


public class StartMenuController {

    @FXML
    private Button loadBudgetBtn;

    BudgetController budgetController = new BudgetController();
    Calculation calc = new Calculation();


    @FXML
    public void initialize() {
        budgetController = BudgetController.getInstance(); // gets current budgetController

    }



    @FXML
    private void loadNewBudget(ActionEvent event) throws Exception {
        FileUtility.load = false;
        ChangeScene.changeToScene(getClass(), event, "hello-view.fxml");
    }

    @FXML
    private void loadPrevBudget(ActionEvent event) throws Exception {
        FileUtility.load = true;
        ChangeScene.changeToScene(getClass(), event, "hello-view.fxml");

    }
}
