package budget.ui;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Utility class for changing scenes in the JavaFX application.
 */
public final class ChangeScene {

    /**
     * Private constructor to prevent instantiation.
     */
    private ChangeScene() {
        // This class should not be instantiated.
    }

    /**
     * Changes the current scene to the one specified by the FXML file.
     *
     * @param className   The class associated with the FXML file.
     * @param buttonEvent The event that triggers the scene change.
     * @param sceneName   The name of the FXML scene file.
     * @param height    The height of the scene.
     * @param width     The width of the scene.
     * @throws Exception if an error occurs during scene change.
     */

    public static void changeToScene(final Class className, final Event buttonEvent, final String sceneName, final double width, final double height)
            throws Exception {
        Parent root = FXMLLoader.load(className.getResource(sceneName)); // loads FXML for the target scene
        Scene scene = new Scene(root, width, height); // create a new scene from the root
        scene.getStylesheets().add(className.getResource("style/style.css").toExternalForm());
        Stage stage = (Stage) ((Node) buttonEvent.getSource()).getScene().getWindow(); // load window from the scene
        stage.setScene(scene); // set the scene for the stage
        stage.show(); // show the stage
        stage.centerOnScreen();
    }
}
