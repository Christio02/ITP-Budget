module gr2340.utility {
    requires gr2340.core;
    requires com.fasterxml.jackson.databind;

    exports budget.utility;
    opens budget.utility to com.fasterxml.jackson.databind, gr2340.ui;
}