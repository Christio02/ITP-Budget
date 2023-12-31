package budget.ui;
import budget.core.Calculation;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.testfx.api.FxAssert.verifyThat;

public class TableTest extends  TestFXBase{
    /* here put fxid to be used in lookup. Format: "#example"
    Or you can use "example" for getting the text of the component
    */
    private final String TABLE_ID =  "#table";

    private final String SELECTOR_ID = "#selector";

    private final String INPUT_ID = "#input";

    private final String SUM_ID = "#totalSum";

    private final String SUBMIT_BTN_ID = "#inputBtn";

    private final String NEW_BUDGET_BTN = "#newBudgetBtn";

    @BeforeEach
    public void setUp() {
        Map<String, Calculation> fileMap = new HashMap<>();
        Calculation overWriteCalc = new Calculation();
        fileMap.put("overwrite", overWriteCalc);
        String userDir = System.getProperty("user.dir");
        String path = userDir + "/../utility/src/main/resources/budget/utility/testBudget.json";
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
        DataSingleton data = DataSingleton.getInstance();
//        data.updateMap(new HashMap<>());
        WaitForAsyncUtils.waitForFxEvents();
        Integer amount = 2000;
        String food = "Food";
        clickOn(NEW_BUDGET_BTN);
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#nameInput");

        write("Test1");
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

