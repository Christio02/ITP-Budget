package budget.ui;

import budget.core.Calculation;
import budget.core.Category;
import budget.utility.FileUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BudgetsViewController {

    @FXML
    private ListView<String> nameCalc;

    @FXML
    private ListView<Calculation> calcObject;

    private String name;

    private Map<String, Calculation> calculationMap;

    private final DataSingleton data = DataSingleton.getInstance();

    private String selectedName;

    @FXML
    public void initialize() {
        System.out.println(data.getCalculations());
        calculationMap = new HashMap<>();

        calculationMap = data.getCalculations();


        ObservableList<String> listOfCalcNames = FXCollections.observableArrayList();
        ObservableList<Calculation> listOfCalcObjects = FXCollections.observableArrayList();
        for (Map.Entry<String, Calculation> entry: this.calculationMap.entrySet()) {
            String name = entry.getKey();
            Calculation calc = entry.getValue();
            if (!name.equals("overwrite")) {
                listOfCalcNames.add(name);
                listOfCalcObjects.add(calc);
            }
        }

       nameCalc.setItems(listOfCalcNames); calcObject.setItems(listOfCalcObjects);



        handleDoubleClickObject();
        updateView();
        observerSelectedName();

    }

    private void observerSelectedName() {
        nameCalc.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedName = newValue;
                Calculation selectedCalc = calculationMap.get(selectedName);
                calcObject.getSelectionModel().select(selectedCalc);
                calcObject.scrollTo(selectedCalc);
            }
        });
    }

    private void handleDoubleClickObject() {
        nameCalc.setOnMouseClicked(event ->
        {
            if (event.getClickCount() == 2) {
                String selectedCalcName = nameCalc.getSelectionModel().getSelectedItem();

                if (selectedCalcName != null) {
                    Calculation selectedCalc = data.getCalculations().get(selectedCalcName);
                    if (selectedCalc != null) {
                        data.setCalculation(selectedCalc);
                        data.setCalcName(selectedCalcName);
                        try {
                            ChangeScene.changeToScene(getClass(), event, "budget-view.fxml");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }

    private void updateView() {
        calcObject.setCellFactory(param -> new ListCell<Calculation>() {
            @Override
            protected void updateItem(Calculation item, boolean empty) {
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

    @FXML
    private void loadMainMenu(ActionEvent event) throws Exception {
        ChangeScene.changeToScene(getClass(), event, "startmenu-fxml.fxml");

    }

    @FXML
    public void deleteBudget() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

        confirmationAlert.setHeaderText("Are you sure you want to delete budget?");
        confirmationAlert.setContentText("Deleting budget, means you'll never be able to retrieve it!");

        ButtonType confirm = new ButtonType("Confirm");
        ButtonType cancel = new ButtonType("Cancel");

        confirmationAlert.getButtonTypes().setAll(confirm, cancel);

        Optional<ButtonType> result =confirmationAlert.showAndWait();

        int selectedIndex = nameCalc.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0 && result.isPresent() && result.get().equals(confirm)) {
            String selectedCalcName = nameCalc.getItems().get(selectedIndex);

            // Check if the selected item is also in calcObject
            Calculation selectedCalc = calcObject.getItems().get(selectedIndex);

            if (selectedCalc != null) {
                // Remove the selected item from the calculationMap
                calculationMap.remove(selectedCalcName);

                // Remove the selected items from the ListViews
                nameCalc.getItems().remove(selectedIndex);
                calcObject.getItems().remove(selectedIndex);
                data.updateMap(calculationMap);

                // Set the selection back to the first item
                if (!nameCalc.getItems().isEmpty()) {
                    nameCalc.getSelectionModel().selectFirst();
                }

            }
        }
    }


}
