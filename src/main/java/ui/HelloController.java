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
    private TableColumn<Calculation, Integer> brukt;

    @FXML
    private TextField input;

    @FXML
    private Button inputBtn;

    @FXML
    private TableColumn<Calculation, Integer> kategori;

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



}

