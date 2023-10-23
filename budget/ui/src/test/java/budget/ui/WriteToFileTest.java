
package budget.ui;
import budget.core.Calculation;
import budget.ui.BudgetApplication;
import budget.utility.FileUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

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
    private final String CURRENT_DIR = System.getProperty("user.dir");

    /**
     * File path for serialization.
     */


     @BeforeEach
     public void setUp() {
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
         Calculation overWriteCalc = new Calculation();
         fileMap.put("overwrite", overWriteCalc);
         String userDir = System.getProperty("user.dir");
         String path = userDir + "/../utility/src/main/resources/budget/utility/savedBudget.json";
         File file = new File(path);
         try {
             ObjectMapper mapper = new ObjectMapper();
             mapper.writerWithDefaultPrettyPrinter().writeValue(file, fileMap );
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

 }

