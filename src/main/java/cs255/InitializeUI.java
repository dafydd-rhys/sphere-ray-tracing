package cs255;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class InitializeUI extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        final String uiURL = System.getProperty("user.dir") + "\\src\\main\\resources\\cs255\\fxml\\UI.fxml";

        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:///" + uiURL));

        int UI_WINDOW_DIM = 1000;
        Scene scene = new Scene(fxmlLoader.load(), UI_WINDOW_DIM, UI_WINDOW_DIM);
        stage.setTitle("Ray Tracing");
        stage.setResizable(false);
        //stage.setFullScreen(true);
        stage.setMaximized(false);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> System.exit(1));
        stage.show();
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch();
    }

}