package poolongprojectn7.PlaylistComponent;

import java.io.Serializable;

public class Note implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int row;
    private int column;

    public Note(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
