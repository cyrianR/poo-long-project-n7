package poolongprojectn7;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import poolongprojectn7.AppModel.View;
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
        this.toolBar = new ToolBarComponent(this, this.model).getToolbar();
        this.piano = new PianoRoll();
        this.playlist = new PlaylistComponent();
        this.browser = new Browser(this).getBrowser();
        this.pianoView = new HBox(this.browser, this.piano);

        this.getChildren().addAll(this.toolBar, this.playlist);

        if (!Files.exists(Paths.get(exportFilePath))) {
            new File(exportFilePath).mkdirs();
        }
    }

    public AppModel getModel() {
        return this.model;
    }

    public PianoRoll getPianoRoll() {
        return this.piano;
    }

    // Method to switch to Overview view
    public void switchToOverview() {
        this.model.setCurrentView(View.OVERVIEW);
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
        this.model.setCurrentView(View.COMPOSITION);
        File f = new File(exportFilePath + this.model.getSelectedTrack() + ".mid");
        if(f.exists() && !f.isDirectory()) { 

            try {
                this.piano = new PianoRoll(exportFilePath, this.model.getSelectedTrack());
            }
            catch (InvalidMidiDataException e ) {
                this.piano = new PianoRoll();
            }
            catch (IOException e ) {
                this.piano = new PianoRoll();
            }
        }
        else {
            this.piano = new PianoRoll();
        }
        this.pianoView = new HBox(this.browser, this.piano);
        this.getChildren().removeAll(this.pianoView, this.playlist);
        this.getChildren().addAll(this.pianoView);
    }
}
