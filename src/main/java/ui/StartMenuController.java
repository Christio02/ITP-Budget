package ui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class StartMenuController {

    @FXML
    public void initialize() {

    }

    @FXML
    private void loadNewBudget(ActionEvent event) throws Exception {
        Utility.changeToScene(getClass(), event,"hello-view.fxml");
    }
}
