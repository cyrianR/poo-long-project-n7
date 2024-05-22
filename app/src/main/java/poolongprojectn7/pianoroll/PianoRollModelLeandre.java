package poolongprojectn7.pianoroll;


public class PianoRollModelLeandre {

    private Integer currentOctave = 4;
    private double[][] actives;

    public PianoRollModelLeandre(){
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 52; j++){
                this.actives[i][j] = 0.0;
            }
        }
    }
    
    public void setActive(int i, int j, double d){
        actives[i][j] = d;
    }

    public double getActive(int i, int j){
        return actives[i][j];
    }

    public int getOctave(){
        return currentOctave;
    }

    public void setOctave(int i){
        currentOctave = i;
    }
}
