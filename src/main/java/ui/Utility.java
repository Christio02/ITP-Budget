package ui;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Utility {

    // Example call: Utility.changeToScene(class, event, "Myfxml.fxml")
    public static void changeToScene(Class className, Event buttonEvent, String sceneName) throws Exception {
        Parent root = FXMLLoader.load(className.getResource(sceneName)); // loads fxml for main budget scene
        Scene scene = new Scene(root); // creat new scene from root
        Stage stage = (Stage) ((Node) buttonEvent.getSource()).getScene().getWindow(); // load window from scene
        stage.setScene(scene); // set scene for stage
        stage.show(); // show the stage
    }


}
