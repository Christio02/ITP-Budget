package ui;

import core.Calculation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController {
    @FXML
    private TableView<Calculation> table;
    @FXML
    private TableColumn<Calculation, Integer> brukt;

    @FXML
    private TableColumn<Calculation, String> kategori;



}
