package budget.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BudgetApplication extends Application {

    /**
     * The width of the application window.
     */
    private static final int WIDTH = 600;
    /**
     * The height of the application window.
     */
    private static final int HEIGHT = 400;
    @Override
    public final void start(final Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BudgetApplication.class.getResource("startmenu-fxml.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        scene.getStylesheets().add(getClass().getResource("style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method to launch the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(final String[] args) {
        launch();
    }
}
