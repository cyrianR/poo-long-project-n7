package poolongprojectn7;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import poolongprojectn7.pianoroll.PianoRoll;
import poolongprojectn7.toolbarcomponent.ToolBarComponent;
import javafx.scene.control.ToolBar;

public class AppView extends VBox {

    private VBox overview;
    private VBox compositionView;
    private ToolBar toolBar;
    private PianoRoll piano;

    /** Constructor view part of the MVC of the application. */
    public AppView(AppModel model, Runnable handler) {

        // Creating toolbar
        this.toolBar = new ToolBarComponent(this).getToolbar();
        this.piano = new PianoRoll();

        getChildren().addAll(this.toolBar);
    }

    // Method to switch to Overview view
    public void switchToOverview() {
        getChildren().remove(this.piano);
        //getChildren().addAll();
    }

    // Method to switch to Composition View view
    public void switchToCompositionView() {
        getChildren().remove(this.piano);
        getChildren().addAll(this.piano);
    }
}
