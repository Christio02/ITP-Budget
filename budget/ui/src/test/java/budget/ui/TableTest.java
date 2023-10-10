package budget.ui;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.api.FxAssert.verifyThat;

public class TableTest extends  TestFXBase{
    /* here put fxid to be used in lookup. Format: "#example"
    Or you can use "example" for getting the text of the component
    */
    final String TABLE_ID =  "#table";

    final String SELECTOR_ID = "#selector";

    final String INPUT_ID = "#input";

    final String SUM_ID = "#totalSum";

    final String SUBMIT_BTN_ID = "#inputBtn";

    final String NEW_BUDGET_BTN = "#newBudgetBtn";


    @Test
    public void checkIfValueWasAddedToCorrectCategory() {
        WaitForAsyncUtils.waitForFxEvents();
        Integer amount = 2000;
        String food = "Food";
        clickOn(NEW_BUDGET_BTN);
        WaitForAsyncUtils.waitForFxEvents();
        clickOn(SELECTOR_ID);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);


        clickOn(INPUT_ID);
        write("2000"); // type in 2000 in input
        moveTo(SUBMIT_BTN_ID);
        clickOn(SUBMIT_BTN_ID);

        /* get tableview fxid, then use TableViewMatchers for checking that the row "food"
        has correct amount
        */

        WaitForAsyncUtils.waitForFxEvents(); // wait for mouse/key events to be finished
        verifyThat(TABLE_ID, TableViewMatchers.containsRow(food, amount));
        verifyThat(SUM_ID, TextMatchers.hasText("2000"));

    }


}

