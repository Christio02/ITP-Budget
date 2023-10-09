module gr2340.core {
    exports core;

    opens core to com.fasterxml.jackson.databind, gr2340.ui, gr2340.utility;
}