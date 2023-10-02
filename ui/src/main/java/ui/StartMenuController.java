package ui;
import core.Calculation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utility.ChangeScene;
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
        ChangeScene.changeToScene(getClass(), event, "ui/hello-view.fxml");
    }

    @FXML
    private void loadPrevBudget(ActionEvent event) throws Exception {
        FileUtility.load = true;
        System.out.println(calc.getTotalSum());
        ChangeScene.changeToScene(getClass(), event, "ui/hello-view.fxml");

    }
}

