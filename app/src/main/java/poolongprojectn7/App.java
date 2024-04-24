package poolongprojectn7;

import javafx.application.Application;
import javafx.stage.*;

public class App extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        PianoRoll p = new PianoRoll();
        p.start(stage);
    }
}
