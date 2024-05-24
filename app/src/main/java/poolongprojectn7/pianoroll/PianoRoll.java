package poolongprojectn7.pianoroll;

import javafx.scene.layout.HBox;

public class PianoRoll extends HBox{

    private PianoRollModel model;

    public PianoRoll() {
        this.model = new PianoRollModel();
        PianoRollView view = new PianoRollView(model);
        PianoRollController controller = new PianoRollController(this.model, view);
        this.getChildren().add(controller);
    }

    public PianoRollModel getModel() {
        return this.model;
    }

}
