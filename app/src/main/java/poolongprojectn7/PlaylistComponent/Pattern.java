package poolongprojectn7.PlaylistComponent;

import java.util.ArrayList;
import java.util.List;

public class Pattern {
    private String name;
    private List<Note> notes;

    public Pattern() {
        this.name = "New Pattern";
        this.notes = new ArrayList<>();
    }

    public Pattern(String name) {
        this.name = name;
        this.notes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return name;
    }
}
