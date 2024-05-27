package poolongprojectn7.pianoroll;

import javafx.scene.layout.HBox;

import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;

public class PianoRoll extends HBox{

    private PianoRollModel model;

    public PianoRoll() {
        this.model = new PianoRollModel();
        PianoRollView view = new PianoRollView(model);
        PianoRollController controller = new PianoRollController(this.model, view);
        this.getChildren().add(controller);
    }

    public PianoRoll(String filePath, String fileName) throws InvalidMidiDataException, IOException {
        this.model = new PianoRollModel(filePath, fileName);
        PianoRollView view = new PianoRollView(model);
        PianoRollController controller = new PianoRollController(this.model, view);
        this.getChildren().add(controller);
        view.updateAll();
    }

    public PianoRollModel getModel() {
        return this.model;
    }

}
