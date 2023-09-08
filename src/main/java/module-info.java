module itp.gr2340.itpmal {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens itp.gr2340.itpmal to javafx.fxml;
    exports itp.gr2340.itpmal;
}