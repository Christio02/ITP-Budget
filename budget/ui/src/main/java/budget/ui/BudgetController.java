package budget.ui;
import budget.core.Calculation;
import budget.core.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import budget.utility.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BudgetController {

    // Static field to hold the instance
//    private static BudgetController instance;
//
//    // Method to get the instance
//    public static BudgetController getInstance() {
//        return instance;
//    }


    @FXML
    private TableView<Category> table;

    @FXML
    private Button saveBtn;

    @FXML
    private ImageView saveIcon;

    @FXML
    private TextField input;

    @FXML
    private Button inputBtn;

    @FXML
    private TableColumn<Category, String> category;

    @FXML
    private TableColumn<Category, Integer> amountUsed;

    @FXML
    private SplitPane splitPane;

    @FXML
    private ComboBox<String> selector;

    @FXML
    private Text totalSum;

    @FXML
    private Button returnMenuBtn;

    @FXML
    private Text budgetTitle;

    private final ObservableList<Category> categoryList = FXCollections.observableArrayList();
    private Calculation calc;

    private final DataSingleton data = DataSingleton.getInstance();

    @FXML
    public void initialize() {
        calc = data.getCalculation();
        setupUI();
    }

    private void setupUI() {
        // Set up the category selection ComboBox
        ObservableList<String> categoryOptions = FXCollections.observableArrayList(
                "Food", "Entertainment", "Transportation", "Clothing", "Other"
        );
        selector.setItems(categoryOptions);

        // Set up the table columns
        category.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        amountUsed.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // Add categories to the list and set the table
        categoryList.addAll(calc.getCategoriesList());
        table.setItems(categoryList);

        // Set the divider position for the SplitPane
        splitPane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
            splitPane.setDividerPosition(0, 0.4);
        });

        // Set hover effects for the save button
        saveBtn.setOnMouseEntered(event -> {
            Image hoverImage = new Image(getClass().getResource("/budget/images/saveIconHover.png").toString());
            saveIcon.setImage(hoverImage);
        });

        saveBtn.setOnMouseExited(event -> {
            Image normalImage = new Image(getClass().getResource("/budget/images/saveIcon.png").toString());
            saveIcon.setImage(normalImage);
        });
        budgetTitle.setText(data.getCalcName());
        totalSum.setText(Integer.toString(calc.getTotalSum()));
    }

    @FXML
    private void loadMainMenu(ActionEvent event) throws Exception {
        ChangeScene.changeToScene(getClass(), event, "startmenu-fxml.fxml");
    }

    public void receiveSelectedCalculation(Calculation receivedCalculation) {
        addCalculation(receivedCalculation);
    };

    public void addCalculation(Calculation calc) {
        String name = this.budgetTitle.getText();
        data.addCalculation(name, calc);
    }

    public Map<String, Calculation> getCalculations() {
        return data.getCalculations();
    }

    @FXML
    public void addAmount() {
        // Get the input values
        int amountToAdd = Integer.parseInt(input.getText());
        String category = selector.getValue();


        for (Category cat : categoryList) {
            if (cat.getCategoryName().equals(category)) {
                calc.addAmountToCategory(cat, amountToAdd);
                table.refresh();
                input.clear();
            }
        }

        totalSum.setText(Integer.toString(calc.getTotalSum()));

    }



    @FXML
    public void saveBudget() {
        addCalculation(this.calc);
        try {
            FileUtility.writeToFile(getCalculations());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
