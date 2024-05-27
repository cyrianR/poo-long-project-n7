package poolongprojectn7.pianoroll;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class PianoRollView extends GridPane{
    
    private PianoRollModel model;
    private Rectangle[][] rectangles;

    public PianoRollView(PianoRollModel model) {
        this.model = model;
        this.rectangles = new Rectangle[12][52];
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 52; j++){
                Color bg = i%2 == 0 ? Color.WHITE : Color.GRAY;
                rectangles[i][j] = new Rectangle(30,30,bg);
                this.add(rectangles[i][j], j, i);
            }
        }
    }

    public void update(int i, int j){
        Color bg = i%2 == 0 ? Color.WHITE : Color.GRAY;
        bg = model.isNoteActive(model.getMidiNoteNumberFromRow(i), j) ? Color.GREEN : bg;
        this.rectangles[i][j].setFill(bg);
    }

    public void updateAll() {
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 52; j++){
                update(i, j);
            }
        }
    }


}
