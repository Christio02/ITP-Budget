module budget.core {
    requires com.fasterxml.jackson.annotation;
    exports budget.core;

    opens budget.core to com.fasterxml.jackson.databind;
}