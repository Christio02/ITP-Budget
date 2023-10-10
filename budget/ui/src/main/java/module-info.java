module gr2340.ui {
    requires gr2340.core;
    requires gr2340.utility;
    requires javafx.controls;
    requires javafx.fxml;

    opens budget.ui to javafx.fxml, javafx.graphics, javafx.controls;


}