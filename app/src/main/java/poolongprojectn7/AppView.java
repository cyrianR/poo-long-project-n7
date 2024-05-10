package poolongprojectn7;

import javafx.scene.layout.VBox;
import poolongprojectn7.PlaylistComponent.PlaylistComponent;
import poolongprojectn7.pianoroll.PianoRoll;
import poolongprojectn7.toolbarcomponent.ToolBarComponent;
import javafx.scene.control.ToolBar;

public class AppView extends VBox {

    private ToolBar toolBar;
    private PianoRoll piano;
    private PlaylistComponent playlist;

    /** Constructor view part of the MVC of the application. */
    public AppView(AppModel model, Runnable handler) {

        // Creating toolbar
        this.toolBar = new ToolBarComponent(this).getToolbar();
        this.piano = new PianoRoll();
        this.playlist = new PlaylistComponent();

        this.getChildren().addAll(this.toolBar, this.playlist);
    }

    // Method to switch to Overview view
    public void switchToOverview() {
        this.getChildren().removeAll(this.piano, this.playlist);
        this.getChildren().addAll(this.playlist);
    }

    // Method to switch to Composition View view
    public void switchToCompositionView() {
        this.getChildren().removeAll(this.piano, this.playlist);
        this.getChildren().addAll(this.piano);
    }
}
