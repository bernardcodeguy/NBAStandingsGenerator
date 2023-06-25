package fx.app.nbastandingsgenerator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppManager extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppManager.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("NBA Standing Generator");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}