package poolongprojectn7.pianoroll;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class PianoRollViewLeandre extends GridPane{
    
    private PianoRollModelLeandre model;
    private Rectangle[][] rectangles;

    public PianoRollViewLeandre(PianoRollModelLeandre model) {
        this.model = model;
        this.rectangles = new Rectangle[12][52];
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 52; j++){
                rectangles[i][j] = new Rectangle(30,30,Color.GREEN);
                rectangles[i][j].setOpacity(0.0);
                this.add(rectangles[i][j], j, i);
            }
        }
        
    }

    public void update(){
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 52; j++){
                this.rectangles[i][j].setOpacity(model.getActive(i,j));
            }
        }
    }


}
