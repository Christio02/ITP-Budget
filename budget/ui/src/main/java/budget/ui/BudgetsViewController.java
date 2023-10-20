package budget.ui;

import budget.core.Calculation;
import budget.core.Category;
import budget.utility.FileUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
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

    private Map<String, Calculation> calculationMap;

    private StartMenuController menuController = new StartMenuController();
    @FXML
    public void initialize() {
        Calculation calc1 = new Calculation();
        menuController = StartMenuController.getInstance();
        calculationMap = BudgetController.getInstance().getCalculations();
        try {
            if (FileUtility.getLoad()) {
                FileUtility.readFromFile(calculationMap);
                String name = menuController.getCalcName();
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
            listOfCalcNames.add(name);
            listOfCalcObjects.add(calc);

        }

       nameCalc.setItems(listOfCalcNames); calcObject.setItems(listOfCalcObjects);

    }


}
