

module gr.javafx.gr {
    requires javafx.controls;
    requires javafx.fxml;
    exports gr2340;
    opens gr2340 to javafx.fxml;
}

