package budget.ui;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import java.util.concurrent.TimeoutException;

public abstract class TestFXBase extends ApplicationTest {


    /*
    This code launches a test version of the application, so similar to javafx methods
     */
    @BeforeEach
    public void setUpClass() throws Exception {
        ApplicationTest.launch(BudgetApplication.class);

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @AfterEach
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage(); // hide stage so it can be shown for next test
        /*
        For making sure that keys or mouse do not get stuck5
         */
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    /**
     *
     */
    public <T extends Node> T find(final String query) {
        // Used find java fx GUI component
        return (T) lookup(query).queryAll().iterator().next();
    }
}

