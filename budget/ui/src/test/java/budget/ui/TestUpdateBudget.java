package budget.ui;

import budget.core.Calculation;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ListViewMatchers.hasItems;
import static org.testfx.matcher.control.TableViewMatchers.containsRow;
import static org.testfx.matcher.control.TextMatchers.hasText;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

public class TestUpdateBudget extends TestFXBase {
    private final String TABLE_ID = "#table";
    private final String SELECTOR_ID = "#selector";
    private final String INPUT_ID = "#input";
    private final String SUBMIT_BTN_ID = "#inputBtn";
    private final String NEW_BUDGET_BTN = "#newBudgetBtn";
    private final String SAVE_BTN = "#saveBtn";
    private final String GO_BACK_BTN = "#returnMenuBtn";
    private final String LOAD_BUDGET_BTN = "#loadBudgetBtn";
    private final String SUM_ID = "#totalSum";
    private final String DEL_BTN = "#deleteBtn";
    private final String NAME_VIEW = "#nameCalc";

    private DataSingleton data = DataSingleton.getInstance();
    private  Calculation testCalc = new Calculation("updateTest");
    @BeforeEach
    public void initialize() {
        testCalc.addAmountToCategory(testCalc.getCategory("Food"), 70);
        testCalc.addAmountToCategory(testCalc.getCategory("Transportation"), 30);
        data.sendClearRequest();

    }



    @Test
    public void testUpdatedBudget() {
        String transportation = "Transportation";
        String ent = "Entertainment";
        clickOn(NEW_BUDGET_BTN);
        clickOn("#nameInput");
        write("testUpdate");
        clickOn("OK");
        waitForFxEvents();
        waitForFxEvents();
        clickOn(SELECTOR_ID);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn(INPUT_ID);
        write("3000");
        clickOn(SUBMIT_BTN_ID);
        clickOn(SAVE_BTN);
        clickOn(GO_BACK_BTN);
        waitForFxEvents();
        clickOn(LOAD_BUDGET_BTN);
        waitForFxEvents();
        doubleClickOn("testUpdate");
        verifyThat(TABLE_ID, containsRow(transportation, 3000));
        waitForFxEvents();
        clickOn(SELECTOR_ID);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn(INPUT_ID);
        write("3000");
        clickOn(SUBMIT_BTN_ID);
        clickOn(SAVE_BTN);
        clickOn(GO_BACK_BTN);
        clickOn(LOAD_BUDGET_BTN);
        waitForFxEvents();
        doubleClickOn("testUpdate");
        waitForFxEvents();
        verifyThat(TABLE_ID, containsRow(ent, 3000));
        verifyThat(TABLE_ID, containsRow(transportation, 3000));



    }

}
