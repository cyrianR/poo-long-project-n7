package poolongprojectn7.pianoroll;

import javafx.scene.layout.HBox;
import poolongprojectn7.InvalidNoteException;
import poolongprojectn7.Note; // temporaire tkt

import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;

public class PianoRoll extends HBox{

    private PianoRollModel model;

    public PianoRoll() {
        this.model = new PianoRollModel();
        // debut test tkt
        try {
			this.model.getPattern().addNote(new Note(1, 1, 1, 1), 27);
		} catch (InvalidNoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ceci est un test
        PianoRollView view = new PianoRollView(model);
        PianoRollController controller = new PianoRollController(this.model, view);
        this.getChildren().add(controller);
    }

    public PianoRoll(String filePath, String fileName) throws InvalidMidiDataException, IOException {
        this.model = new PianoRollModel(filePath, fileName);
        PianoRollView view = new PianoRollView(model);
        PianoRollController controller = new PianoRollController(this.model, view);
        this.getChildren().add(controller);
    }


    public PianoRollModel getModel() {
        return this.model;
    }

}
