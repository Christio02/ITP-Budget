package ui;
import core.Calculation;
import core.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;

public class HelloController {

    @FXML
    private TableView<Category> table;


    @FXML
    private TextField input;

    @FXML
    private Button inputBtn;

    @FXML
    private TableColumn<Category, String> category;

    @FXML
    private TableColumn<Category, Integer> amountUsed;


    @FXML
    private ComboBox<String> selector;


    private ObservableList<Category> categoryList = FXCollections.observableArrayList();
    private Calculation calc = new Calculation();

    @FXML
    public void initialize() {
        ObservableList<String> categoryOptions = FXCollections.observableArrayList(
                "Food", "Entertainment", "Transportation", "Clothing", "Other"
        );
        selector.setItems(categoryOptions);

        category.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        amountUsed.setCellValueFactory(new PropertyValueFactory<>("amount"));

        categoryList.addAll(calc.getCategoriesList()); // add all categories to the list
        table.setItems(categoryList); // set the table to display the list


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
                return;
            }
        }


    }
}




