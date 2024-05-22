package poolongprojectn7.pianoroll;

import javafx.scene.layout.HBox;

public class PianoRoll extends HBox{

    public PianoRoll() {
        PianoRollModel model = new PianoRollModel();
        PianoRollView view = new PianoRollView(model);
        PianoRollController controller = new PianoRollController(model, view);
        this.getChildren().add(controller);
    }

}
