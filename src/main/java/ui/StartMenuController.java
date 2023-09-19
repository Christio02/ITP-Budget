package ui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import utility.ChangeScene;

public class StartMenuController {

    @FXML
    public void initialize() {

    }

    @FXML
    private void loadNewBudget(ActionEvent event) throws Exception {
        ChangeScene.changeToScene(getClass(), event,"hello-view.fxml");
    }

    @FXML
    private void loadPrevBudget(ActionEvent event) throws Exception {
        ChangeScene.changeToScene(getClass(), event,"hello-view.fxml");
    }
}
