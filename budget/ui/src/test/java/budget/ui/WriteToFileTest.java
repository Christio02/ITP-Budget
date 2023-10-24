
package budget.ui;
import budget.core.Calculation;
import budget.utility.FileUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.matcher.control.ListViewMatchers;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.testfx.api.FxAssert.verifyThat;


public class WriteToFileTest extends TestFXBase {


     private  final String TABLE_ID =  "#table";
     private final String SELECTOR_ID = "#selector";

     private final String INPUT_ID = "#input";

     private final String SUBMIT_BTN_ID = "#inputBtn";

     private final String NEW_BUDGET_BTN = "#newBudgetBtn";

     private final String SAVE_BTN = "#saveBtn";

     private final String GO_BACK_BTN = "#returnMenuBtn";
     private final String LOAD_BUDGET_BTN = "#loadBudgetBtn";

     private  final String SUM_ID = "#totalSum";

     private final String DEL_BTN = "#deleteBtn";

     private final String NAME_VIEW = "#nameCalc";
    private final String CURRENT_DIR = System.getProperty("user.dir");

    /**
     * File path for serialization.
     */


     @BeforeEach
     public void setUp() {
         DataSingleton data = DataSingleton.getInstance();
         data.clearMap();
     }
     @Test
     public void testCorrectWrittenToFile() {


         WaitForAsyncUtils.waitForFxEvents();
         Integer amount1 = 2000;
         Integer amount2 = 3000;
         String food = "Food";

         clickOn(NEW_BUDGET_BTN);
         clickOn("#nameInput");

         write("Test");
         clickOn("OK");
         WaitForAsyncUtils.waitForFxEvents();
         clickOn(SELECTOR_ID);
         type(KeyCode.DOWN);
         type(KeyCode.ENTER);
         clickOn(INPUT_ID);
         write("2000"); // type in 2000 in input

         moveTo(SUBMIT_BTN_ID);
         clickOn(SUBMIT_BTN_ID);

         moveTo(SELECTOR_ID);
         clickOn(SELECTOR_ID);
         WaitForAsyncUtils.waitForFxEvents();
         type(KeyCode.DOWN);
         type(KeyCode.DOWN);
         type(KeyCode.DOWN);
         type(KeyCode.ENTER);

         clickOn(INPUT_ID);
         write("3000");

         moveTo(SUBMIT_BTN_ID);
         clickOn(SUBMIT_BTN_ID);

         // save to file
         moveTo(SAVE_BTN);
         clickOn(SAVE_BTN);

         // go back to menu
         moveTo(GO_BACK_BTN);
         clickOn(GO_BACK_BTN);

         // now should load previous budget
         WaitForAsyncUtils.waitForFxEvents();
         clickOn(LOAD_BUDGET_BTN);
         WaitForAsyncUtils.waitForFxEvents();
         doubleClickOn("Test");
         WaitForAsyncUtils.waitForFxEvents();
         String clothing = "Clothing";

         verifyThat(TABLE_ID, TableViewMatchers.containsRow(clothing, amount2));
         verifyThat(TABLE_ID, TableViewMatchers.containsRow(food, amount1));

         verifyThat(SUM_ID, TextMatchers.hasText("5000"));
         Map<String, Calculation> fileMap = new HashMap<>();
         Calculation calc = new Calculation();
         fileMap.put("overwrite", calc);
         String path = "/../utility/src/main/resources/budget/utility/testBudget.json";

         try {
             FileUtility.writeToFile(fileMap, path);
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
     }

     @Test
     public void testDeletedBudget() {
         WaitForAsyncUtils.waitForFxEvents();
         Integer amount1 = 2000;
         String food = "Food";

         clickOn(NEW_BUDGET_BTN);
         clickOn("#nameInput");

         write("Test2");
         clickOn("OK");
         WaitForAsyncUtils.waitForFxEvents();
         clickOn(SELECTOR_ID);
         type(KeyCode.DOWN);
         type(KeyCode.ENTER);
         clickOn(INPUT_ID);
         write("2000");
         clickOn(SUBMIT_BTN_ID);
         clickOn(SAVE_BTN);
         clickOn(GO_BACK_BTN);

         WaitForAsyncUtils.waitForFxEvents();
         clickOn(LOAD_BUDGET_BTN);
         WaitForAsyncUtils.waitForFxEvents();

         moveTo("Test2");
         clickOn("Test2");
         moveTo(DEL_BTN);
         clickOn(DEL_BTN);
         WaitForAsyncUtils.waitForFxEvents();
         clickOn("Confirm");
         clickOn(GO_BACK_BTN);

         WaitForAsyncUtils.waitForFxEvents();
         clickOn(LOAD_BUDGET_BTN);
         verifyThat(NAME_VIEW, ListViewMatchers.hasItems(1));


     }

     @AfterEach
    public void end() {
         DataSingleton data = DataSingleton.getInstance();
         data.clearMap();
     }

 }

