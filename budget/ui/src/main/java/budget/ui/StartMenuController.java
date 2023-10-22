package budget.ui;
import budget.core.Calculation;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import budget.utility.FileUtility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;



public class StartMenuController {
    private Dialog<String> dialog;

    @FXML
    private Button loadBudgetBtn;
    private DataSingleton data = DataSingleton.getInstance();
    @FXML
    public void initialize() {

        try {
            Map<String, Calculation> tempMap = new HashMap<>();
            FileUtility.readFile(tempMap);
            data.updateMap(tempMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void popUpOnLoadBudgets(ActionEvent event) {
        // Create and show the dialog
        Optional<String> result = dialog.showAndWait();
        // Further processing
        var ref = new Object() {
            String thisKey = "";
        };
        result.ifPresent(s -> ref.thisKey = s);
        boolean budgetExists = false;
        for (Map.Entry<String, Calculation> entry : data.getCalculations().entrySet()) {
            String presentKey = entry.getKey();
            if (ref.thisKey.equals(presentKey)) {
                budgetExists = true;
                break;

            }
        }
        if (budgetExists) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error, could not create new budget");
            errorAlert.setContentText("Budget with that name already exists!");
            errorAlert.showAndWait();

            errorAlert.setOnCloseRequest(Event::consume);
        } else {
            data.setCalculation(new Calculation());
            data.setCalcName(ref.thisKey);
            FileUtility.setLoad(false);

            try {
                ChangeScene.changeToScene(getClass(), event, "budget-view.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
    @FXML
    private void loadNewBudget(ActionEvent event) throws Exception {
        FileUtility.setLoad(false);
        popUpOnLoadBudgets(event);

    }
    @FXML
    private void loadPrevBudget(ActionEvent event) throws Exception {
        FileUtility.setLoad(true);
        ChangeScene.changeToScene(getClass(), event, "load-budgets.fxml");
    }
}

