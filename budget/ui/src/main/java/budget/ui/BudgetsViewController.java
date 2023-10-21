package budget.ui;

import budget.core.Calculation;
import budget.core.Category;
import budget.utility.FileUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Map;

public class BudgetsViewController {

    @FXML
    private ListView<String> nameCalc;

    @FXML
    private ListView<Calculation> calcObject;

    @FXML
    private Text budgetTitle;

    private String name;

    private Map<String, Calculation> calculationMap;

    private StartMenuController menuController = new StartMenuController();
    @FXML
    public void initialize() {
        Calculation calc1 = new Calculation();
        menuController = StartMenuController.getInstance();
        name = menuController.getCalcName();
        calculationMap = BudgetController.getInstance().getCalculations();
        try {
            if (FileUtility.getLoad()) {
                FileUtility.readFile(calculationMap);
                budgetTitle.setText(name);
            }

        } catch (IOException e) {
            e.getStackTrace();
        }


        ObservableList<String> listOfCalcNames = FXCollections.observableArrayList();
        ObservableList<Calculation> listOfCalcObjects = FXCollections.observableArrayList();
        for (Map.Entry<String, Calculation> entry: this.calculationMap.entrySet()) {
            String name = entry.getKey();
            Calculation calc = entry.getValue();
            if (!name.equals("Text")) {
                listOfCalcNames.add(name);
                listOfCalcObjects.add(calc);
            }
        }

       nameCalc.setItems(listOfCalcNames); calcObject.setItems(listOfCalcObjects);

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

        nameCalc.setOnMouseClicked(event ->
        {
            if (event.getClickCount() == 2) {
                String selectedCalcName = nameCalc.getSelectionModel().getSelectedItem();
                if (selectedCalcName != null) {
                    loadSelectedBudgetScene(ActionEvent event);
                }
            }
        });

    }

    @FXML
    private void loadSelectedBudgetScene(ActionEvent event) throws Exception {
        ChangeScene.changeToScene(getClass(), event, "budget-view.fxml");
    }

    @FXML
    private void loadMainMenu(ActionEvent event) throws Exception {
        ChangeScene.changeToScene(getClass(), event, "startmenu-fxml.fxml");

    }


}
