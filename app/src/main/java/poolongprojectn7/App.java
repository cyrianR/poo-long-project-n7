package poolongprojectn7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        Tool_Bar toolBar = new Tool_Bar();
        BorderPane root = new BorderPane();
        root.setTop(toolBar.getToolbar());

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("N7 Music Studio");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}