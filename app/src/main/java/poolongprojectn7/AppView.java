package poolongprojectn7;

import javafx.scene.layout.VBox;
import poolongprojectn7.pianoroll.PianoRoll;
import poolongprojectn7.toolbarcomponent.ToolBarComponent;
import javafx.scene.control.ToolBar;

public class AppView extends VBox {

    public AppView(AppModel model, Runnable handler) {

        // Creating toolbar
        ToolBar toolBar = new ToolBarComponent().getToolbar();
        PianoRoll piano = new PianoRoll();

        VBox vBox = new VBox(toolBar, piano);

        getChildren().addAll(vBox);
    }
}
