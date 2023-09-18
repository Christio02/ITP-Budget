

module gr.javafx.gr {
    requires javafx.controls;
    requires javafx.fxml;

    opens ui to javafx.fxml, javafx.graphics;
    opens core to javafx.base;
}

