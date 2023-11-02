package budget.ui;
import budget.core.Calculation;
import budget.core.Category;
import budget.utility.Json;
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
import budget.utility.FileUtility;
import javafx.scene.chart.PieChart;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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


    @FXML
    public final void initialize() {
        calc = dataSingleton.getCalculation();
        calc.setName(dataSingleton.getCalcName());
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
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        pieChart.getData().clear();

        for (Category cat : calc.getCategoriesList()) {
            PieChart.Data data = new PieChart.Data(cat.getCategoryName(), cat.getAmount());
            pieChartData.add(data);
        }
        int totalAmount = calc.getTotalSum();
        for (PieChart.Data data : pieChartData) {
            Node node = data.getNode();
            if (node != null) {
                String color = getCategoryColor(data.getName());
                node.setStyle("-fx-pie-color: " + color + ";");
                // Customize the data label with a percentage
                Label label = new Label();
                label.setText(String.format("%.1f%%", (data.getPieValue() / totalAmount) * 100));

                // Set the label's style
                label.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

                // Add the label to the pie chart
                data.getNode().setUserData(label);
            }
        }

        // Clear existing data and add the filtered data to the PieChart
        pieChart.getData().clear();
        pieChart.getData().addAll(pieChartData);

        pieChart.setTitle("Budget Distribution");
    }

    private String getCategoryColor(final String categoryName) {
        return switch (categoryName) {
            case "Food" -> "#106bc7";
            case "Entertainment" -> "#ffe100";
            case "Transportation" -> "#FF0000";
            case "Clothing" -> "#00ff1e";
            default -> "#ff00d9";
        };
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
        ChangeScene.changeToScene(getClass(), event, "startmenu-fxml.fxml", 600, 400);
    }

    /**
     * Add a new calculation to the data.
     *
     * @param newCalc The new calculation to add.
     */
    public final void addCalculation(final Calculation newCalc) {
        String name = this.budgetTitle.getText();
        dataSingleton.addCalculation(name, newCalc);
    }

    /**
     * Get the list of calculations.
     *
     * @return A map of calculation names to Calculation objects.
     */
    public final Map<String, Calculation> getCalculations() {
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

        System.out.println(dataSingleton.getCalculations());

        if (dataSingleton.getCalculations().containsKey(this.calc.getName())) {
            System.out.println("Oppdaterer budsjett!");
            updateBudget();

        } else {
            addCalculation(this.calc);
            createNewBudget();
        }

//        try {
//            FileUtility.writeToFile(getCalculations(), "/../utility/src/main/resources/budget/utility/savedBudget.json");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void sendRequest(Calculation calculation, String httpMethod, String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(httpMethod);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String jsonCalculation = Json.getMapper().writeValueAsString(calculation);
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
                writer.write(jsonCalculation);
            }
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("Budget was written correctly!");
            } else {
                System.out.println("Budget was not written correctly " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNewBudget() {
        sendRequest(this.calc, "POST", "http://localhost:8080/budget");
    }

    private  void updateBudget() {
        sendRequest(this.calc, "PUT", "http://localhost:8080/budget/" + this.calc.getName());
    }




}
