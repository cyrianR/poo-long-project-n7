package poolongprojectn7.pianoroll;

import javafx.scene.layout.HBox;

public class PianoRoll extends HBox{
    public PianoRoll(){
        PianoRollModelLeandre model = new PianoRollModelLeandre();
        PianoRollViewLeandre view = new PianoRollViewLeandre(model);
        PianoRollControllerLeandre controller = new PianoRollControllerLeandre(model, view);
        this.getChildren().add(controller);
    }

    
}
