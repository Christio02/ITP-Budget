package ui;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobotException;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxRobot;

public class TableTest extends  TestFXBase{
    /* here put fxid to be used in lookup. Format: "#example"
    Or you can use "example" for getting the text of the component
    */

    final String TABLE_ID =  "#table";
    final String CATEGORY_ID = "#category";
    final String AMOUNT_USED_ID = "#amountused";

    final String SELECTOR_ID = "#selector";

    final String INPUT_ID = "#input";

    final String SUM_ID = "#totalSum";

    final String SUBMIT_BTN_ID = "#inputBtn";


//    @Test
//    public void clickOnRandomElement() {
//        assertThrows(FxRobotException.class, () -> {
//            clickOn("#figmaballs");
//        });
//    }

    @Test
    public void checkIfValueWasAddedToCorrectCategory() {
        WaitForAsyncUtils.waitForFxEvents();
        String category = "Food";
        Integer amount = 2000;
        clickOn(SELECTOR_ID);
        clickOn(category);
        clickOn(INPUT_ID);
        write("2000");// type in 2000 in input
        moveTo(SUBMIT_BTN_ID);
        clickOn(SUBMIT_BTN_ID);

        /* get tableview fxid, then use TableViewMatchers for checking that the row "food"
        has correct amount
        */

        WaitForAsyncUtils.waitForFxEvents(); // wait for mouse/key events to be finished
        verifyThat(TABLE_ID, TableViewMatchers.containsRow(category, amount));
        verifyThat(SUM_ID, TextMatchers.hasText("2000"));

    }


}
