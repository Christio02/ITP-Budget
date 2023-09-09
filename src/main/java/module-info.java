module gr2340.javafx.gr2340 {
    requires javafx.controls;
    requires javafx.fxml;


    opens gr2340.javafx.gr2340 to javafx.fxml;
    exports gr2340.javafx.gr2340;
}