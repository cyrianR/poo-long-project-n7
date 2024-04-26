package poolongprojectn7;

import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class AppView extends VBox {

    public appView(appModel model, Runnable handler) {

        // Creating toolbar
        ToolBar toolBar = new ToolBar();
        Button button = new Button("Test");
        toolBar.getItems().add(button);
        Button button2 = new Button("Button 2");
        toolBar.getItems().add(button2);

        VBox vBox = new VBox(toolBar);

        getChildren().addAll(vBox);
    }
}
