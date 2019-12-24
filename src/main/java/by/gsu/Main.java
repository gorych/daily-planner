package by.gsu;

import by.gsu.model.Note;
import by.gsu.repository.ConnectionHolder;
import by.gsu.repository.NoteRepository;
import by.gsu.repository.impl.NoteRepositoryImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Optional;

@Slf4j
public class Main extends Application {

    private static final String TITLE = "Daily planner";
    private static final int DEFAULT_WIDTH = 850;
    private static final int DEFAULT_HEIGHT = 568;

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

        NoteRepository noteRepository = new NoteRepositoryImpl();
        Optional<Note> byId = noteRepository.findById(1);
    }

    @Override
    public void stop() {
        ConnectionHolder.closeConnection();
    }
}
