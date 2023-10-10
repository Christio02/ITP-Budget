module budget.utility {
    requires budget.core;
    requires com.fasterxml.jackson.databind;

    exports budget.utility;
    opens budget.utility to com.fasterxml.jackson.databind, budget.ui;
}