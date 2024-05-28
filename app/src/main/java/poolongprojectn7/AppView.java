package poolongprojectn7;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import poolongprojectn7.AppModel.View;
import poolongprojectn7.PlaylistComponent.PlaylistComponent;
import poolongprojectn7.browersComponent.Browser;
import poolongprojectn7.pianoroll.PianoRoll;
import poolongprojectn7.toolbarcomponent.ToolBarComponent;
import poolongprojectn7.MixerComponent.MixerComponent;
import java.io.File;

import javafx.geometry.Insets;
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
    private Browser browser;
    private HBox pianoView;
    private MixerComponent mixer; 
    /** Constructor view part of the MVC of the application. */
    public AppView(AppModel model, Runnable handler) {
        this.model = model;

        // Creating toolbar

        this.toolBar = new ToolBarComponent(this, this.model).getToolbar();
        try {
            this.piano = new PianoRoll(exportFilePath, this.model.getSelectedTrack());
        }
        catch (InvalidMidiDataException | IOException e ) {
            this.piano = new PianoRoll();
        }
        this.playlist = new PlaylistComponent();
        this.browser = new Browser(this);
        this.pianoView = new HBox(this.browser, this.piano);
        this.mixer = new MixerComponent(); 

        this.getChildren().addAll(this.toolBar, this.playlist, this.mixer);

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
            File f = new File(exportFilePath + "test" + ".mid");
            if(f.exists() && !f.isDirectory()) {
                f.delete();
            }
            this.piano.getModel().getPattern().save(exportFilePath, this.model.getSelectedTrack());

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.getChildren().removeAll(this.playlist, this.pianoView, this.mixer);
        this.getChildren().addAll(this.playlist, this.mixer);
    }

    // Method to switch to Composition View view
    public void switchToCompositionView() {
        this.model.setCurrentView(View.COMPOSITION);
        
        // Import current track pattern
        try {
            this.piano = new PianoRoll(exportFilePath, this.model.getSelectedTrack());
        }
        catch (InvalidMidiDataException | IOException e ) {
            this.piano = new PianoRoll();
        }
        this.browser.getBrowser().setPrefWidth(300);
        this.piano.setPrefWidth(1500);
        this.pianoView = new HBox(this.browser.getBrowser(), this.piano);
        this.getChildren().removeAll(this.pianoView, this.playlist, this.mixer);

        this.getChildren().addAll(this.pianoView);
    }
}
