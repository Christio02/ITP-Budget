module budget.utility {
    requires budget.core;
    requires com.fasterxml.jackson.databind;
    requires spring.core;


    opens budget.utility to com.fasterxml.jackson.databind, budget.ui;
    exports budget.utility;
}