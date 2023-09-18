package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BudgetApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BudgetApplication.class.getResource("startmenu-fxml.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add(getClass().getResource("style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}