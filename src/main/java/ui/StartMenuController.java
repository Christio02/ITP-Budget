package ui;
import core.Calculation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utility.ChangeScene;
import utility.FileUtility;

import java.io.FileNotFoundException;


public class StartMenuController {

    @FXML
    private Button loadBudgetBtn;

    BudgetController budgetController = new BudgetController();
    Calculation calc = new Calculation();


    @FXML
    public void initialize() {
        budgetController = BudgetController.getInstance(); // gets current budgetController
        try {
            FileUtility.readFromFile(calc);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }



    @FXML
    private void loadNewBudget(ActionEvent event) throws Exception {
        ChangeScene.changeToScene(getClass(), event,"hello-view.fxml");
    }

    @FXML
    private void loadPrevBudget(ActionEvent event) throws Exception {
        System.out.println(calc.getTotalSum());
        this.budgetController.setCalculationFromFile(this.calc);
        ChangeScene.changeToScene(getClass(), event,"hello-view.fxml");

    }
}
