package budget.ui;

import budget.core.Calculation;
import budget.utility.FileUtility;
import javafx.scene.input.KeyCode;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ListViewMatchers.hasItems;
import static org.testfx.matcher.control.TableViewMatchers.containsRow;
import static org.testfx.matcher.control.TextMatchers.hasText;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

public class WriteToFileTest extends TestFXBase {

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

    @Test
    public void testCorrectWrittenToFile() {
        waitForFxEvents();

        int amount1 = 2000;
        int amount2 = 3000;
        String food = "Food";

        clickOn(NEW_BUDGET_BTN);
        clickOn("#nameInput");
        write("Test");
        clickOn("OK");
        waitForFxEvents();

        clickOn(SELECTOR_ID);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn(INPUT_ID);
        write("2000");
        moveTo(SUBMIT_BTN_ID);
        clickOn(SUBMIT_BTN_ID);

        moveTo(SELECTOR_ID);
        clickOn(SELECTOR_ID);
        waitForFxEvents();
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn(INPUT_ID);
        write("3000");
        moveTo(SUBMIT_BTN_ID);
        clickOn(SUBMIT_BTN_ID);

        moveTo(SAVE_BTN);
        clickOn(SAVE_BTN);

        moveTo(GO_BACK_BTN);
        clickOn(GO_BACK_BTN);

        waitForFxEvents();
        clickOn(LOAD_BUDGET_BTN);
        waitForFxEvents();
        doubleClickOn("Test");
        waitForFxEvents();

        String clothing = "Clothing";

        verifyThat(TABLE_ID, containsRow(clothing, amount2));
        verifyThat(TABLE_ID, containsRow(food, amount1));
        verifyThat(SUM_ID, hasText("5000"));

        ArrayList<Calculation> calculations = new ArrayList<>();
        Calculation calculation = new Calculation();
        calculations.add(calculation);
        String path = "../../utility/src/main/resources/budget/utility/testBudget.json";

        try {
            FileUtility.writeToFile(calculations, path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeletedBudget() {
        waitForFxEvents();

        int amount1 = 2000;
        String food = "Food";

        clickOn(NEW_BUDGET_BTN);
        clickOn("#nameInput");
        write("Test2");
        clickOn("OK");
        waitForFxEvents();
        clickOn(SELECTOR_ID);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn(INPUT_ID);
        write("2000");
        clickOn(SUBMIT_BTN_ID);
        clickOn(SAVE_BTN);
        clickOn(GO_BACK_BTN);

        waitForFxEvents();
        clickOn(LOAD_BUDGET_BTN);
        waitForFxEvents();
        moveTo("Test2");
        clickOn("Test2");
        moveTo(DEL_BTN);
        clickOn(DEL_BTN);
        waitForFxEvents();
        clickOn("Confirm");
        clickOn(GO_BACK_BTN);

        waitForFxEvents();
        clickOn(LOAD_BUDGET_BTN);
        verifyThat(NAME_VIEW, hasItems(1));
    }

    @AfterAll
    public static void finish() {
        ArrayList<Calculation> calc = new ArrayList<>();
        try {
            FileUtility.writeToFile(calc, "../../utility/src/main/resources/budget/utility/savedBudget.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
