package poolongprojectn7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args); // launches application
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // NB : Stage = Window
        primaryStage.setTitle("N7 STUDIO");
        primaryStage.setMaximized(true);
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            closeStage(primaryStage);
        });

        // NB : Scene = content of the window
        AppController appController = new AppController();
        Scene scene = new Scene(appController.getView());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void closeStage(Stage stage) {
        stage.close();
    }
}
