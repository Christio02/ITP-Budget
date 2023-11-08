package budget.ui;
import budget.core.Calculation;
import budget.core.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;


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
     * The PieChart for displaying the budget distribution.
     */
    @FXML
    private PieChart pieChart;

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
    private final DataSingleton dataSingleton = DataSingleton.getInstance();
    /**
     * Initialize the controller and set up the UI.
     */

    private ArrayList<Calculation> calculations;
    @FXML
    public final void initialize() {
        calc = dataSingleton.getCalculation();
        this.calculations = dataSingleton.getCalculations();
        System.out.println(calculations.toString());
        setupUI();
        populatePieChart();
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

        budgetTitle.setText(dataSingleton.getCalcName());
        totalSum.setText(Integer.toString(calc.getTotalSum()));


    }

    @SuppressWarnings("checkstyle:magicnumber")
    private void populatePieChart() {
        // Hvis pieChart ikke har noen data, initialiser den med alle kategorier
        if (pieChart.getData().isEmpty()) {
            for (Category cat : calc.getCategoriesList()) {
                if (cat.getAmount() > 0) {
                    PieChart.Data data = new PieChart.Data(cat.getCategoryName(), cat.getAmount());
                    pieChart.getData().add(data);
                }
            }
        } else {
            // Oppdater eksisterende data
            for (Category cat : calc.getCategoriesList()) {
                PieChart.Data existingData = findDataByName(cat.getCategoryName());
                if (existingData != null) {
                    if (cat.getAmount() > 0) {
                        existingData.setPieValue(cat.getAmount());
                    } else {
                        // Fjern kategorien fra piecharten hvis dens verdi er 0
                        pieChart.getData().remove(existingData);
                    }
                } else if (cat.getAmount() > 0) {
                    // Hvis data for denne kategorien ikke finnes, og den har en verdi større enn 0, legg den til
                    PieChart.Data data = new PieChart.Data(cat.getCategoryName(), cat.getAmount());
                    pieChart.getData().add(data);
                }
            }
        }

        // Sett fargene og oppdater labels
        updateColorsAndLabels();

        pieChart.setTitle("Budget Distribution");
    }

    private PieChart.Data findDataByName(final String categoryName) {
        for (PieChart.Data data : pieChart.getData()) {
            if (data.getName().equals(categoryName)) {
                return data;
            }
        }
        return null;
    }

    private void updateColorsAndLabels() {
        int totalAmount = calc.getTotalSum();
        for (PieChart.Data data : pieChart.getData()) {
            Node node = data.getNode();
            Label label = (Label) node.getUserData();
            if (label == null) {
                label = new Label();
                node.setUserData(label);
            }
            final int percentageDenominator = 100;
            label.setText(String.format("%.1f%%", (data.getPieValue() / totalAmount) * percentageDenominator));
        }
    }


    /**
     * Switch to the main menu view.
     *
     * @param event The event triggering the action.
     * @throws Exception If there is an error during scene change.
     */
    @SuppressWarnings("magicnumber")
    @FXML
    private void loadMainMenu(final ActionEvent event) throws Exception {
        ChangeScene.changeToScene(getClass(), event, "startmenu-fxml.fxml", 600, 425);
    }

    /**
     * Add a new calculation to the data.
     *
     * @param newCalc The new calculation to add.
     */
    public final void addCalculation(final Calculation newCalc) {
        String name = this.budgetTitle.getText();
        this.calc.setName(name);
        dataSingleton.addCalculation(newCalc);
    }

    /**
     * Get the list of calculations.
     *
     * @return A map of calculation names to Calculation objects.
     */
    public final ArrayList<Calculation> getCalculations() {
        return dataSingleton.getCalculations();
    }

    /**
     * Add an amount to the selected category.
     */
    @FXML
    public void addAmount() {
        try {
            int amountToAdd = Integer.parseInt(input.getText());
            String newCategory = selector.getValue();

            for (Category cat : categoryList) {
                if (cat.getCategoryName().equals(newCategory)) {
                    calc.addAmountToCategory(cat, amountToAdd);
                    table.refresh();
                    input.clear();
                }
            }
            totalSum.setText(Integer.toString(calc.getTotalSum()));
            populatePieChart();
        } catch (NumberFormatException e) {
            showAlertDialog("Invalid input", "Please enter a valid integer.");
        } catch (IllegalArgumentException e) {
            showAlertDialog("Invalid input", "The amount must be a positive integer.");
        }


    }

    /**
     * Show an alert dialog with the given title and content.
     * @param title The title of the alert dialog
     * @param content The content of the alert dialog
     */
    private void showAlertDialog(final String title, final String content) {
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


        if (checkDuplicate(this.calc.getName())) {
            System.out.println("Oppdaterer budsjett!");
            updateBudget();

        } else {
            System.out.println("Legger til nytt budsjett!");
            createNewBudget();
        }
    }

    private boolean checkDuplicate(String name) {
        for (Calculation calc : this.calculations) {
            if (calc.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void createNewBudget() {
        this.calc.setName(budgetTitle.getText());
        dataSingleton.addCalculation(this.calc);
    }


    private void updateBudget() {
        System.out.println(this.calc);  // Debug: Print calc before sending
        dataSingleton.updateCalculation(this.calc);
    }




}
