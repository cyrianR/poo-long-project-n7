package poolongprojectn7.pianoroll;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;

import poolongprojectn7.InvalidNoteException;
import poolongprojectn7.Note;
import poolongprojectn7.Pattern;

/**
 * Model of the piano roll component.
 * @version Alpha
 */
public class PianoRollModel {

    /* Fixed notes velocity. */
    final int VELOCITY = 100;
    /* Maximum octave for a note. */
    final int MAX_OCTAVE = 10;
    /* Maximum index for a note. */
    final int MAX_NOTE_INDEX = 11;
    /* Piano vertical length. */
    final int PIANO_LENGTH = 128;
    /* Piano horizontal length. Corresponds to the maximum midi value for a note. */
    final int PIANO_HEIGHT = (MAX_NOTE_INDEX + 1) * (MAX_OCTAVE + 1);
    /* Fixed note tick length. */
    final int NOTE_TICK_LENGTH = 500;

    /* Pattern of real notes. */
    private Pattern pattern;
    /* Current showed octave. */
    private int currentOctave = 4;
    /* Active notes in the pattern. */
    private boolean[][] activeNotes;

    /**
     * Create a new piano roll model.
     */
    public PianoRollModel() {
        this.activeNotes = new boolean[PIANO_HEIGHT + 1][PIANO_LENGTH + 1];
        this.pattern = new Pattern();
        initNonActiveNotes();
    }

    /**
     * Created a new piano roll model based on an imported pattern.
     * @param filePath path to the midi file corresponding to the desired pattern
     * @param fileName midi file name corresponding to the desired pattern
     * @throws InvalidMidiDataException if the File does not point to valid MIDI file data recognized by the system
     * @throws IOException if an I/O exception occurs
     */
    public PianoRollModel(String filePath, String fileName) throws InvalidMidiDataException, IOException {
        this.activeNotes = new boolean[PIANO_HEIGHT + 1][PIANO_LENGTH + 1];
        this.pattern = new Pattern(filePath, fileName);
        initNonActiveNotes();
        initImportedNotes();
    }

    /**
     * Initiate all notes as inactive.
     * Used to set up an empty piano roll.
     */
    private void initNonActiveNotes() {
        for(int i = 0; i <= PIANO_HEIGHT; i++){
            for(int j = 0; j < PIANO_LENGTH; j++){
                this.activeNotes[i][j] = false;
            }
        }
    }

    /**
     * Set imported notes as active.
     */
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

    /**
     * Compute the midi note number from the actual displayed row position.
     * @param i actual displayed row position
     * @return midi note number
     */
    public int getMidiNoteNumberFromRow(int i) {
        return (this.currentOctave + 1) * (MAX_NOTE_INDEX + 1) - i;
    }
    
    /**
     * Change a note state.
     * @param i real row of the note
     * @param j real column of the note
     */
    public void changeNoteState(int i, int j){

        if (isNoteActive(i, j)) {
            this.pattern.removeNote(this.getMidiNoteNumberFromRow(i), j * NOTE_TICK_LENGTH);
        } else {
            Note note = new Note(this.currentOctave, i % (MAX_NOTE_INDEX + 1), VELOCITY, NOTE_TICK_LENGTH);
            try {
                this.pattern.addNote(note, j * NOTE_TICK_LENGTH);
            } catch (InvalidNoteException e) {
                e.printStackTrace();
            }
        }
        this.activeNotes[i][j] = !this.activeNotes[i][j];
    }

    /**
     * Test whether the note at the given index is active.
     * @param i real row of the note
     * @param j real column of the note
     * @return true when the note at the given index is active
     */
    public boolean isNoteActive(int i, int j){
        return this.activeNotes[i][j];
    }

    /**
     * Obtain the current octave.
     * @return the current octave
     */
    public int getOctave(){
        return this.currentOctave;
    }

    /**
     * Set the current octave to the given value.
     * An octave will not be set outside of the range [0 MAX_OCTAVE]
     * @param i new octave
     */
    public void setOctave(int i){
        if (i <= MAX_OCTAVE && i >= 0) {
            this.currentOctave = i;
        }
    }

    /**
     * Obtains the piano roll pattern.
     * @return piano roll pattern
     */
    public Pattern getPattern() {
        return this.pattern;
    }
}
