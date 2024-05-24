package poolongprojectn7;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import poolongprojectn7.PlaylistComponent.PlaylistComponent;
import poolongprojectn7.browersComponent.Browser;
import poolongprojectn7.MixerComponent.MixerComponent;
import poolongprojectn7.pianoroll.PianoRoll;
import poolongprojectn7.toolbarcomponent.ToolBarComponent;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeView;

public class AppView extends BorderPane {

    private ToolBar toolBar;
    private PianoRoll piano;
    private PlaylistComponent playlist;
    private TreeView<String> browser;
    private HBox pianoView;
    private MixerComponent mixer;

    /** Constructor view part of the MVC of the application. */
    public AppView(AppController controller, AppModel model, Runnable handler) {
        // Creating toolbar
        this.toolBar = new ToolBarComponent(controller).getToolbar();
        this.piano = new PianoRoll();
        this.playlist = new PlaylistComponent();
        this.browser = new Browser(this).getBrowser();
        this.mixer = new MixerComponent();
        this.pianoView = new HBox(this.browser, this.piano);

        setTop(this.toolBar);
        setLeft(this.browser);
        setCenter(this.playlist);
        setBottom(this.mixer);
    }

    // Method to switch to Overview view
    public void switchToOverview() {
        setCenter(this.playlist);
        if (!getChildren().contains(mixer)) {
            setBottom(this.mixer);
        }
    }

    // Method to switch to Composition View view
    public void switchToCompositionView() {
        setCenter(this.pianoView);
        if (getChildren().contains(mixer)) {
            getChildren().remove(this.mixer);
        }
    }
}
