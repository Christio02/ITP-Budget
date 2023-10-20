package budget.ui;
import budget.core.Budgets;
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
import budget.utility.FileUtility;

import java.io.IOException;
import java.util.ArrayList;

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
    private Calculation calc = new Calculation();

    private FileUtility fileutility;

    private Budgets budgets;
    @FXML
    public void initialize() {
        ArrayList<Calculation> calculationArrayList = new ArrayList<>();
        calculationArrayList.add(calc);
//        instance = this;
        budgetTitle.setText(StartMenuController.getInstance().getCalcName());
        budgets = new Budgets(calculationArrayList);

        ObservableList<String> categoryOptions = FXCollections.observableArrayList(
                "Food", "Entertainment", "Transportation", "Clothing", "Other"
        );
        selector.setItems(categoryOptions);

        category.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        amountUsed.setCellValueFactory(new PropertyValueFactory<>("amount"));

        categoryList.addAll(calc.getCategoriesList()); // add all categories to the list
        table.setItems(categoryList); // set the table to display the list


        // Set the divider position to 40% of the screen and make it non-resizable
        splitPane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
            splitPane.setDividerPosition(0, 0.4);
        });

        // Set the save button to change image when hovered over
        saveBtn.setOnMouseEntered(event -> {
            Image hoverImage = new Image(getClass().getResource("/budget/images/saveIconHover.png").toString());
            saveIcon.setImage(hoverImage);
        });

        saveBtn.setOnMouseExited(event -> {
            Image normalImage = new Image(getClass().getResource("/budget/images/saveIcon.png").toString());
            saveIcon.setImage(normalImage);
        });
    }

    @FXML
    private void loadMainMenu(ActionEvent event) throws Exception {
        ChangeScene.changeToScene(getClass(), event, "startmenu-fxml.fxml");
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
        ArrayList<Calculation> testList = new ArrayList<>();
        testList.add(calc);
        this.budgets.addBudget(testList);

    }

    @FXML
    public void saveBudget() {
        try {
            fileutility.writeFile(this.budgets);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
