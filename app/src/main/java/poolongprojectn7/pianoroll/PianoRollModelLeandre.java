package poolongprojectn7.pianoroll;

import poolongprojectn7.Pattern;

public class PianoRollModelLeandre {

    final int PIANO_LENGTH = 52;
    final int PIANO_HEIGHT = 12;

    private Pattern pattern;
    private int currentOctave = 4;
    private int[][] activeNotes;

    public PianoRollModelLeandre(){
        
        for(int i = 0; i < PIANO_HEIGHT; i++){
            for(int j = 0; j < PIANO_LENGTH; j++){
                this.activeNotes[i][j] = 0.0;
            }
        }
    }
    
    public void setActiveNote(int i, int j, double d){
        activeNotes[i][j] = d;
    }

    public double getActiveNotes(int i, int j){
        return activeNotes[i][j];
    }

    public int getOctave(){
        return currentOctave;
    }

    public void setOctave(int i){
        if (i <= 10 && i >=0) {
            currentOctave = i;
        }
    }
}
