package poolongprojectn7;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import poolongprojectn7.PlaylistComponent.PlaylistComponent;
import poolongprojectn7.browersComponent.Browser;
import poolongprojectn7.pianoroll.PianoRoll;
import poolongprojectn7.toolbarcomponent.ToolBarComponent;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeView;

public class AppView extends VBox {

    private AppModel model;
    private ToolBar toolBar;
    private PianoRoll piano;
    private PlaylistComponent playlist;
    private TreeView<String> browser;
    private HBox pianoView;

    /** Constructor view part of the MVC of the application. */
    public AppView(AppModel model, Runnable handler) {
        this.model = model;

        // Creating toolbar
        this.toolBar = new ToolBarComponent(this).getToolbar();
        this.piano = new PianoRoll();
        this.playlist = new PlaylistComponent();
        this.browser = new Browser(this).getBrowser();
        this.pianoView = new HBox(this.browser, this.piano);

        this.getChildren().addAll(this.toolBar, this.playlist);
    }

    public AppModel getModel() {
        return this.model;
    }

    // Method to switch to Overview view
    public void switchToOverview() {
        this.getChildren().removeAll(this.playlist, this.pianoView);
        // this.getChildren().removeAll(this.piano, this.playlist, this.browser);
        this.getChildren().addAll(this.playlist);
    }

    // Method to switch to Composition View view
    public void switchToCompositionView() {
        this.getChildren().removeAll(this.pianoView, this.playlist);
        // this.getChildren().removeAll(this.piano, this.playlist, this.browser);
        this.getChildren().addAll(this.pianoView);
    }
}
