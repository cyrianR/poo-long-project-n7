package poolongprojectn7;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import poolongprojectn7.PlaylistComponent.PlaylistComponent;
import poolongprojectn7.browersComponent.Browser;
import poolongprojectn7.pianoroll.PianoRoll;
import poolongprojectn7.toolbarcomponent.ToolBarComponent;
import java.io.File;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeView;

public class AppView extends VBox {

    /* The file path where the temporary MIDI file will be stored */
    private static String exportFilePath = System.getProperty("user.dir") + "/src/exports/tracks";

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
        // Export current track pattern to midi
        // this.piano.getModel().getPattern().export(exportFilePath, this.model.getSelectedTrack());

        this.getChildren().removeAll(this.playlist, this.pianoView);
        this.getChildren().addAll(this.playlist);
    }

    // Method to switch to Composition View view
    public void switchToCompositionView() {
        // Import current track pattern or create one
        // File f = new File(exportFilePath + this.model.getSelectedTrack());
        // System.out.println(f.toString());
        // if(f.exists() && !f.isDirectory()) { 
        //     // import

        // }
        // else {
        //     // nouveau
        // }

        this.getChildren().removeAll(this.pianoView, this.playlist);
        this.getChildren().addAll(this.pianoView);
    }
}
