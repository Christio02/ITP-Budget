package budget.ui;

import budget.core.Calculation;
import budget.core.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import java.util.Optional;


public class BudgetsViewController {
    /**
     * Listview for names.
     */
    @FXML
    private ListView<String> nameCalc;
    /**
     * Listview for calculation objects.
     */
    @FXML
    private ListView<Calculation> calcObject;

    /**
     * Singleton for data retrieval and storing.
     */
    private final DataSingleton data = DataSingleton.getInstance();

    /**
     * Selected name in listview.
     */
    private String selectedName;

    /**
     * List of calculations.
     */
    private ArrayList<Calculation> calculations;

    /**
     * Initialize the BudgetsView UI and set up event handling.
     */
    @FXML
    public final void initialize() {
        this.calculations = data.getCalculations();


        ObservableList<String> listOfCalcNames = FXCollections.observableArrayList();
        ObservableList<Calculation> listOfCalcObjects = FXCollections.observableArrayList();
        for (Calculation calc: this.calculations) {
                listOfCalcNames.add(calc.getName());
                listOfCalcObjects.add(calc);

        }

       nameCalc.setItems(listOfCalcNames); calcObject.setItems(listOfCalcObjects);



        handleDoubleClickObject();
        updateView();
        observerSelectedName();

    }

    /**
     * Observes the selected name in the nameCalc ListView.
     */
    private void observerSelectedName() {
        nameCalc.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedName = newValue;
                Calculation selectedCalc = calculations.stream().filter(calc -> calc.getName().equals(selectedName)).findFirst().orElse(null);
                calcObject.getSelectionModel().select(selectedCalc);
                calcObject.scrollTo(selectedCalc);
            }
        });
    }

    /**
     * Handle double-click on an object in the nameCalc ListView.
     */
    @SuppressWarnings("magicnumber")
    private void handleDoubleClickObject() {
        nameCalc.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedCalcName = nameCalc.getSelectionModel().getSelectedItem();

                if (selectedCalcName != null) {
                    Calculation selectedCalc = data.getCalculations().
                            stream().filter(calc -> calc.getName().equals(selectedCalcName)).
                            findFirst().orElse(null);
                    if (selectedCalc != null) {
                        data.setCalcName(selectedCalcName);
                        data.setCalculation(selectedCalc);
                        try {
                            ChangeScene.changeToScene(getClass(), event, "budget-view.fxml", 1200, 420);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }

    /**
     * Update the view for the calcObject ListView.
     */
    private void updateView() {
        calcObject.setCellFactory(param -> new ListCell<Calculation>() {
            @Override
            protected void updateItem(final Calculation item, final boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    // Customize the text representation here
                    StringBuilder text = new StringBuilder("Calculation: "  + "\nTotal Sum: " + item.getTotalSum() + "\nCategories:\n");

                    for (Category category : item.getCategoriesList()) {
                        text.append(category.getCategoryName()).append(": ").append(category.getAmount()).append("\nBudget History: ");
                        for (int amount : category.getBudgetHistory()) {
                            text.append(amount).append(", ");
                        }
                        text.delete(text.length() - 2, text.length()); // Remove the trailing comma and space
                        text.append("\n");
                    }

                    setText(text.toString());
                }
            }
        });

    }

    /**
     * Load the main menu.
     * @param event The event triggering the action.
     */
    @SuppressWarnings("magicnumber")
    @FXML
    private void loadMainMenu(final ActionEvent event) throws Exception {
        ChangeScene.changeToScene(getClass(), event, "startmenu-fxml.fxml", 600, 400);
    }

    /**
     * Delete the selected budget.
     */
    @FXML
    public final void deleteBudget() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

        confirmationAlert.setHeaderText("Are you sure you want to delete budget?");
        confirmationAlert.setContentText("Deleting budget, means you'll never be able to retrieve it!");

        ButtonType confirm = new ButtonType("Confirm");
        ButtonType cancel = new ButtonType("Cancel");

        confirmationAlert.getButtonTypes().setAll(confirm, cancel);

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        int selectedIndex = nameCalc.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0 && result.isPresent() && result.get().equals(confirm)) {

            // Check if the selected item is also in calcObject
            Calculation selectedCalc = calcObject.getItems().get(selectedIndex);

            if (selectedCalc != null) {
                data.deleteEntry(selectedCalc);
                // Remove the selected item from the calculationMap
                calculations.remove(selectedCalc);
//                data.deleteRequest(selectedCalcName);

                // Remove the selected items from the ListViews
                nameCalc.getItems().remove(selectedIndex);
                calcObject.getItems().remove(selectedIndex);

                // Set the selection back to the first item
                if (!nameCalc.getItems().isEmpty()) {
                    nameCalc.getSelectionModel().selectFirst();
                }
            }
        }

    }





}
