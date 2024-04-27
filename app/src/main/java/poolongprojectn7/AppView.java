package poolongprojectn7;

import javafx.scene.layout.VBox;
import poolongprojectn7.pianoroll.PianoRoll;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class AppView extends VBox {

    public AppView(AppModel model, Runnable handler) {

        // Creating toolbar
        ToolBar ToolBar = new ToolBar();
        Button Button = new Button("Test");
        ToolBar.getItems().add(Button);
        Button Button2 = new Button("Button 2");
        ToolBar.getItems().add(Button2);
        PianoRoll Piano = new PianoRoll();

        VBox vBox = new VBox(ToolBar, Piano);

        getChildren().addAll(vBox);
    }
}
