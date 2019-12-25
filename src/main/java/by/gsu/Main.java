package by.gsu;

import by.gsu.repository.ConnectionHolder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;

@Slf4j
public class Main extends Application {

    private static final String TITLE = "Daily planner";
    private static final int DEFAULT_WIDTH = 850;
    private static final int DEFAULT_HEIGHT = 567;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent mainLayout = FXMLLoader.load(getClass().getResource("/layout/main.fxml"));

        Scene scene = new Scene(mainLayout, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        URL resource = getClass().getResource("/styles/main.css");
        scene.getStylesheets().add(String.valueOf(resource));

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/icon.png")));
        primaryStage.show();
    }

    @Override
    public void stop() {
        ConnectionHolder.closeConnection();
    }
}
