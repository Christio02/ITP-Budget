/*
package budget.ui;
import budget.ui.BudgetApplication;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.concurrent.TimeoutException;


final String TABLE_ID =  "#table";

final String SELECTOR_ID = "#selector";

final String INPUT_ID = "#input";

final String SUM_ID = "#totalSum";

final String SUBMIT_BTN_ID = "#inputBtn";

final String NEW_BUDGET_BTN = "#newBudgetBtn";

final String SAVE_BTN = "#saveBtn";

 public class WriteToFileTest extends TestFXBase {
    final String NEW_BUDGET_BTN = "#newBudgetBtn";


    @Test
    public void testCorrectWrittenToFile() {
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

        moveTo(SAVE_BTN);
        clickOn(SAVE_BTN);

        String correctJsonFormat = "";

    }






}


 */