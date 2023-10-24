package budget.ui;
import budget.core.Calculation;
import budget.core.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import budget.utility.FileUtility;
import java.util.Map;

public class BudgetController {
    /**
     * The table for displaying categories and their amounts.
     */
    @FXML
    private TableView<Category> table;

    /**
     * The button for saving the current budget.
     */
    @FXML
    private Button saveBtn;

    /**
     * The icon for the save button.
     */
    @FXML
    private ImageView saveIcon;
    /**
     * The divider position for the SplitPane.
     */
    private static final double SPLIT_PANE_DIVIDER_POSITION = 0.4;

    /**
     * The input field for adding amounts to categories.
     */
    @FXML
    private TextField input;

    /**
     * The button for adding amounts to categories.
     */
    @FXML
    private Button inputBtn;

    /**
     * The table column for category names.
     */
    @FXML
    private TableColumn<Category, String> category;

    /**
     * The table column for category amounts.
     */
    @FXML
    private TableColumn<Category, Integer> amountUsed;

    /**
     * The SplitPane for the budget view.
     */
    @FXML
    private SplitPane splitPane;

    /**
     * The ComboBox for selecting categories.
     */
    @FXML
    private ComboBox<String> selector;

    /**
     * The text for displaying the total sum of the budget.
     */
    @FXML
    private Text totalSum;
    /**
     *  The button for returning to the main menu.
     */
    @FXML
    private Button returnMenuBtn;

    /**
     * Title of the budget.
     */
    @FXML
    private Text budgetTitle;

    /**
     * List of categories.
     */
    private final ObservableList<Category> categoryList = FXCollections.observableArrayList();

    /**
     * The current calculation.
     */
    private Calculation calc;

    /**
     * Singleton for data retrieval and storing.
     */
    private final DataSingleton data = DataSingleton.getInstance();
    /**
     * Initialize the controller and set up the UI.
     */
    @FXML
    public final void initialize() {
        calc = data.getCalculation();
        setupUI();
    }

    /**
     * Set up the UI components and their behaviors.
     */
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
            splitPane.setDividerPosition(0, SPLIT_PANE_DIVIDER_POSITION);
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

    /**
     * Switch to the main menu view.
     *
     * @param event The event triggering the action.
     * @throws Exception If there is an error during scene change.
     */
    @FXML
    private void loadMainMenu(final ActionEvent event) throws Exception {
        ChangeScene.changeToScene(getClass(), event, "startmenu-fxml.fxml");
    }

    /**
     * Add a new calculation to the data.
     *
     * @param newCalc The new calculation to add.
     */
    public final void addCalculation(final Calculation newCalc) {
        String name = this.budgetTitle.getText();
        data.addCalculation(name, newCalc);
    }

    /**
     * Get the list of calculations.
     *
     * @return A map of calculation names to Calculation objects.
     */
    public final Map<String, Calculation> getCalculations() {
        return data.getCalculations();
    }

    /**
     * Add an amount to the selected category.
     */
    @FXML
    public void addAmount() {
        try {
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
        } catch (NumberFormatException e) {
            showAlertDialog("Invalid input", "Please enter a valid integer.");
        } catch (IllegalArgumentException e) {
            showAlertDialog("Invalid input", "The amount must be a positive integer.");
        }
    }

    /**
     * Show an alert dialog with the given title and content.
     * @param title
     * @param content
     */
    private void showAlertDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Save the current budget to a file.
     */
    @FXML
    public final void saveBudget() {
        addCalculation(this.calc);
        try {
            FileUtility.writeToFile(getCalculations());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
