module budget.ui {
    requires budget.core;
    requires budget.utility;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires com.github.spotbugs.annotations;

    opens budget.ui to javafx.fxml, javafx.graphics, javafx.controls, com.fasterxml.jackson.databind, budget.utility;


}