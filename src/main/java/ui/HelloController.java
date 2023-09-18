package ui;
import core.Calculation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {

    @FXML
    private TableView<Calculation> table;

    @FXML

    @FXML
    private TextField input;

    @FXML
    private Button inputBtn;

    @FXML
    private TableColumn<Calculation, Integer> Category;
    private TableColumn<Calculation, Integer> amountUsed;


    @FXML
    private ComboBox<String> selector;



    @FXML
    public void initialize() {
        ObservableList<String> categoryOptions = FXCollections.observableArrayList(
                "Food", "Entertainment"
                , "Clothing", "Other"
        );
        selector.setItems(categoryOptions);

    }

    @FXML
    public void addAmount() {
        // Get the input values
        Integer amountToAdd = Integer.parseInt(input.getText());
        String category = selector.getValue();

        // Create a new Calculation object with the values
        Calculation newCalc = new Calculation();

        // Add the newCalc to your TableView's data source
        table.getItems().add(newCalc);
    }
}




