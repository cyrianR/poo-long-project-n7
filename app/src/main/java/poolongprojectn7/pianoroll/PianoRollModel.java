package poolongprojectn7.pianoroll;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;

import poolongprojectn7.Note;
import poolongprojectn7.Pattern;

public class PianoRollModel {

    final int VELOCITY = 100;
    final int MAX_OCTAVE = 10;
    final int MAX_NOTE_INDEX = 11;
    final int PIANO_LENGTH = 52;
    final int PIANO_HEIGHT = (MAX_NOTE_INDEX + 1) * (MAX_OCTAVE + 1);
    final int NOTE_TICK_LENGTH = 500;

    private Pattern pattern;
    private int currentOctave = 4;
    private boolean[][] activeNotes;

    public PianoRollModel(){
        this.pattern = new Pattern();
        initNonActiveNotes();
    }

    public PianoRollModel(String filePath, String fileName) throws InvalidMidiDataException, IOException {
        this.pattern = new Pattern(filePath, fileName);
        initNonActiveNotes();
        initImportedNotes();
    }

    private void initNonActiveNotes() {
        for(int i = 0; i < PIANO_HEIGHT; i++){
            for(int j = 0; j < PIANO_LENGTH; j++){
                this.activeNotes[i][j] = !this.activeNotes[i][j];
            }
        }
    }

    private void initImportedNotes() {
        Map<Long, List<Note>> map = this.pattern.getNoteMap();
        for(Long noteTickKey : map.keySet()) {
            int j = Math.toIntExact(noteTickKey / NOTE_TICK_LENGTH);
            for(Note note : map.get(noteTickKey)) {
                int i = note.getMidiNoteNumber();
                this.activeNotes[i][j] = !this.activeNotes[i][j];
            }
        }
    }
    
    public void changeNoteState(int i, int j){
        if (isNoteActive(i, j)) {
            this.pattern.removeNote(i, j * NOTE_TICK_LENGTH);
        } else {
            Note note = new Note(this.currentOctave, i % (MAX_NOTE_INDEX + 1), VELOCITY, NOTE_TICK_LENGTH);
            this.pattern.addNote(note, j * NOTE_TICK_LENGTH);
        }
        this.activeNotes[i][j] = !this.activeNotes[i][j];
    }

    public boolean isNoteActive(int i, int j){
        return this.activeNotes[i][j];
    }

    public int getOctave(){
        return this.currentOctave;
    }

    public void setOctave(int i){
        if (i <= 10 && i >= 0) {
            this.currentOctave = i;
        }
    }
}
