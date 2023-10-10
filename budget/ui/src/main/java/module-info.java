module budget.ui {
    requires budget.core;
    requires budget.utility;
    requires javafx.controls;
    requires javafx.fxml;

    opens budget.ui to javafx.fxml, javafx.graphics, javafx.controls;


}