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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sound.midi.InvalidMidiDataException;

public class AppView extends VBox {

    /* The file path where the temporary MIDI file will be stored */
    private static String exportFilePath = System.getProperty("user.dir") + "/src/exports/tracks/";

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
        Pattern pattern = new Pattern();
        this.browser = new Browser(this, pattern).getBrowser();
        this.piano = new PianoRoll();
        this.playlist = new PlaylistComponent();
        this.pianoView = new HBox(this.browser, this.piano);

        this.getChildren().addAll(this.toolBar, this.playlist);

        if (!Files.exists(Paths.get(exportFilePath))) {
            new File(exportFilePath).mkdirs();
        }
    }

    public AppModel getModel() {
        return this.model;
    }

    // Method to switch to Overview view
    public void switchToOverview() {
        // Export current track pattern to midi
        try {
            String name = this.model.getSelectedTrack();
            System.out.println(name);
            this.piano.getModel().getPattern().save(exportFilePath, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.getChildren().removeAll(this.playlist, this.pianoView);
        this.getChildren().addAll(this.playlist);
    }

    // Method to switch to Composition View view
    public void switchToCompositionView() {
        File f = new File(exportFilePath + this.model.getSelectedTrack() + ".mid");
        this.pianoView.getChildren().remove(this.piano);
        if(f.exists() && !f.isDirectory()) { 
            // Import current track pattern
            try {
                this.piano = new PianoRoll(exportFilePath, this.model.getSelectedTrack());
            }
            catch (InvalidMidiDataException | IOException e ) {
                this.piano = new PianoRoll();
            }
        }
        else {
            this.piano = new PianoRoll();
        }
        this.pianoView.getChildren().add(this.piano);
        this.getChildren().removeAll(this.pianoView, this.playlist);
        this.getChildren().addAll(this.pianoView);
    }
}
