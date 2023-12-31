package budget.ui;
import budget.core.Calculation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import budget.utility.FileUtility;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Alert;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller class for the start menu in the budget application.
 */
public class StartMenuController {

    /**
     * A dialog for entering a unique budget name.
     */
    private Dialog<String> dialog;

    /**
     * Button for loading a budget.
     */
    @FXML
    private Button loadBudgetBtn;
    /**
     * A singleton instance for managing data related to budget calculations.
     */
    private DataSingleton data = DataSingleton.getInstance();

    /**
     * List of calculations.
     */
    private  ArrayList<Calculation> calculations;

    /**
     * Initializes the controller, loading existing budget data if available.
     */
    @FXML
    public final void initialize() {

        this.calculations = data.getCalculations();

        dialog = new Dialog<>();
        dialog.setTitle("Set budget name");
        dialog.setHeaderText("Please provide a unique name for budget");

        TextField inputText = new TextField();
        inputText.setPromptText("Write name here...");
        inputText.setId("nameInput");
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


    /**
     * Displays a dialog for entering a unique budget name.
     * @return True if the budget can be loaded, false otherwise.
     */
    public final boolean popUpOnLoadBudgets() {
        boolean shouldLoad = true;
        Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) {
            return false;
        }

        String key = result.get();
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(key);
        if (key.isEmpty() || !matcher.find()) {
            shouldLoad = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid input!");
            alert.setContentText("Name is empty or name contains non-numerical characters!");

            alert.showAndWait();
            return shouldLoad;

        }

        boolean budgetExists = false;
        for (Calculation calc : calculations) {
            if (calc != null && key.equals(calc.getName())) {
                budgetExists = true;
                break;

            }
        }
        if (budgetExists) {
            shouldLoad = false;
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error, could not create new budget");
            errorAlert.setContentText("Budget with that name already exists!");
            errorAlert.showAndWait();

        } else {
            data.addCalculation(new Calculation(key));
            data.setCalcName(key);
            FileUtility.setLoad(false);
        }
        return shouldLoad;


    }
    /**
     * Handles the event when the "Load New Budget" button is clicked.
     * @param event The ActionEvent triggered by the button click.
     * @throws Exception if an exception occurs during the operation.
     */
    @SuppressWarnings("magicnumber")
    @FXML
    private void loadNewBudget(final ActionEvent event) throws Exception {
        FileUtility.setLoad(false);
        if (!popUpOnLoadBudgets()) {
            return;
        }
        ChangeScene.changeToScene(getClass(), event, "budget-view.fxml", 1200, 420);

    }
    /**
     * Handles the event when the "Load Previous Budget" button is clicked.
     * @param event The ActionEvent triggered by the button click.
     * @throws Exception if an exception occurs during the operation.
     */
    @SuppressWarnings("magicnumber")
    @FXML
    private void loadPrevBudget(final ActionEvent event) throws Exception {
        FileUtility.setLoad(true);
        ChangeScene.changeToScene(getClass(), event, "load-budgets.fxml", 612, 400);
    }
}
