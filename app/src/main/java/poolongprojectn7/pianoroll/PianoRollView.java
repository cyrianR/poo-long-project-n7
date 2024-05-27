package poolongprojectn7.pianoroll;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class PianoRollView extends GridPane{
    
    private PianoRollModel model;
    private Rectangle[][] rectangles;

    /**
     * Create a new PianoRoll View
     * @param model the model we want to display
     */
    public PianoRollView(PianoRollModel model) {
        this.model = model;
        this.rectangles = new Rectangle[12][128];
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 128; j++){
                Color bg = i%2 == 0 ? Color.WHITE : Color.GRAY;
                rectangles[i][j] = new Rectangle(30,30,bg);
                this.add(rectangles[i][j], j, i);
            }
        }
        
    }

    /**
     * Update the view of the PianoRoll. Is called every time a button is used in the PianoRoll.
     * @param i the row of the button pushed
     * @param j the column of the button pushed
     */
    public void update(int i, int j){
        Color bg = i%2 == 0 ? Color.WHITE : Color.GRAY;
        bg = model.isNoteActive(i, j) ? bg : Color.GREEN;
        this.rectangles[i][j].setFill(bg);
    }


}
