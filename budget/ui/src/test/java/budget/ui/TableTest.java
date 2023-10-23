package budget.ui;
import budget.core.Calculation;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

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

    @BeforeEach
    public void setUp() {
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


    @Test
    public void checkIfValueWasAddedToCorrectCategory() {

        WaitForAsyncUtils.waitForFxEvents();
        Integer amount = 2000;
        String food = "Food";
        clickOn(NEW_BUDGET_BTN);
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameInput");

        write("Test");
        clickOn("OK");

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

