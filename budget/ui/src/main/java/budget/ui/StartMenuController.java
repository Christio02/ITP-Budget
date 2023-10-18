package budget.ui;
import budget.core.Calculation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import budget.utility.FileUtility;

import java.util.Optional;



public class StartMenuController {
    private Dialog<String> dialog;

    private static StartMenuController instance;
    public static StartMenuController getInstance() {
        return instance;
    }

    private String calcName;

    @FXML
    private Button loadBudgetBtn;

    BudgetController budgetController = new BudgetController();

    public String getCalcName(){
        return this.calcName;
    }
    private Calculation test = new Calculation();

    public Calculation getCalculation() {
        return instance.test;
    }

    @FXML
    public void initialize() {
        instance = this;

        budgetController = BudgetController.getInstance(); // gets current budgetController
        dialog = new Dialog<>();
        dialog.setTitle("Set calculation name");
        dialog.setHeaderText("Please provide a unique name for budget");

        TextField inputText = new TextField();
        dialog.getDialogPane().setContent(inputText);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        // Result converter to handle the OK button click
        dialog.setResultConverter(buttonType -> {
            if (buttonType == okButton) {
                return inputText.getText();
            }
            return null;
        });

    }

    public void popUpOnLoadBudgets() {
        // Create and show the dialog
        Optional<String> result = dialog.showAndWait();

        // Further processing
        result.ifPresent(s -> this.calcName = s);
        System.out.println(this.getCalcName());
        this.test.setName(this.getCalcName());
        System.out.println(this.test.getName());

    }



    @FXML
    private void loadNewBudget(ActionEvent event) throws Exception {
        FileUtility.setLoad(false);
        popUpOnLoadBudgets();
        ChangeScene.changeToScene(getClass(), event, "hello-view.fxml");


    }

    @FXML
    private void loadPrevBudget(ActionEvent event) throws Exception {
        FileUtility.setLoad(true);
        ChangeScene.changeToScene(getClass(), event, "hello-view.fxml");

    }

    @FXML
    private void loadSavedBudgets(ActionEvent event) throws Exception {
        FileUtility.setLoad(true);
        ChangeScene.changeToScene(getClass(), event, "budgets-view.fxml");
    }
}

