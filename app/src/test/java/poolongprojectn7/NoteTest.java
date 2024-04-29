package poolongprojectn7;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.sound.midi.InvalidMidiDataException;

/** Test program of the Note Class.
  */
class NoteTest {

    @Test
    public void testConstructorAndGetters() {
        Note note = new Note(3, 7, 100, 500);
        assertEquals(3, note.getOctave());
        assertEquals(7, note.getNoteIndex());
        assertEquals(43, note.getMidiNoteNumber());
        assertEquals(100, note.getVelocity());
        assertEquals(500, note.getDurationTicks());
    }

    @Test
    public void testSetNoteIndex() {
        Note note = new Note(3, 7, 100, 500);
        note.setNoteIndex(9);
        assertEquals(9, note.getNoteIndex());
        assertEquals(45, note.getMidiNoteNumber());
    }

    @Test
    public void testSetVelocity() {
        Note note = new Note(3, 7, 100, 500);
        note.setVelocity(80);
        assertEquals(80, note.getVelocity());
    }

    @Test
    public void testSetDurationTicks() {
        Note note = new Note(3, 7, 100, 500);
        note.setDurationTicks(600);
        assertEquals(600, note.getDurationTicks());
    }

    /* Range tests */
    @Test
    public void testValidNoteHighOctave1() throws InvalidMidiDataException { // Limit case
        Note note = new Note(11, 7, 100, 500);
        Pattern pattern = new Pattern();
        assertThrows( InvalidNoteException.class, () -> {
            pattern.addNote(note, 1200);
        });
    }

    @Test
    public void testValidNoteHighOctave2() throws InvalidMidiDataException {
        Note note = new Note(34, 7, 100, 500);
        Pattern pattern = new Pattern();
        assertThrows( InvalidNoteException.class, () -> {
            pattern.addNote(note, 1200);
        });
    }

    @Test
    public void testValidNoteLowOctave() throws InvalidMidiDataException {
        Note note = new Note(-2, 7, 100, 500);
        Pattern pattern = new Pattern();
        assertThrows( InvalidNoteException.class, () -> {
            pattern.addNote(note, 1200);
        });
    }

    @Test
    public void testValidNoteCorrectOctave() throws InvalidMidiDataException {
        Note note1 = new Note(0, 7, 100, 500);
        Note note2 = new Note(1, 7, 100, 500);
        Note note3 = new Note(5, 7, 100, 500);
        Note note4 = new Note(10, 7, 100, 500);
        Pattern pattern = new Pattern();
        assertDoesNotThrow(() -> {
            pattern.addNote(note1, 1200);
            pattern.addNote(note2, 1200);
            pattern.addNote(note3, 1200);
            pattern.addNote(note4, 1200);
        });
    }

    @Test
    public void testValidNoteHighNoteIndex1() throws InvalidMidiDataException { // Limit case
        Note note = new Note(0, 12, 100, 500);
        Pattern pattern = new Pattern();
        assertThrows( InvalidNoteException.class, () -> {
            pattern.addNote(note, 1200);
        });
    }

    @Test
    public void testValidNoteHighNoteIndex2() throws InvalidMidiDataException {
        Note note = new Note(0, 68, 100, 500);
        Pattern pattern = new Pattern();
        assertThrows( InvalidNoteException.class, () -> {
            pattern.addNote(note, 1200);
        });
    }

    @Test
    public void testValidNoteLowNoteIndex() throws InvalidMidiDataException {
        Note note = new Note(0, -1, 100, 500);
        Pattern pattern = new Pattern();
        assertThrows( InvalidNoteException.class, () -> {
            pattern.addNote(note, 1200);
        });
    }

    @Test
    public void testValidNoteCorrectNoteIndex() throws InvalidMidiDataException {
        Note note1 = new Note(0, 0, 100, 500);
        Note note2 = new Note(0, 3, 100, 500);
        Note note3 = new Note(0, 9, 100, 500);
        Note note4 = new Note(0, 11, 100, 500);
        Pattern pattern = new Pattern();
        assertDoesNotThrow(() -> {
            pattern.addNote(note1, 1200);
            pattern.addNote(note2, 1200);
            pattern.addNote(note3, 1200);
            pattern.addNote(note4, 1200);
        });
    }

    @Test
    public void testValidNoteHighMidiNoteNumber1() throws InvalidMidiDataException { // Limit case: 10*12 + 8 = 128
        Note note = new Note(10, 8, 100, 500);
        Pattern pattern = new Pattern();
        assertThrows( InvalidNoteException.class, () -> {
            pattern.addNote(note, 1200);
        });
    }

    @Test
    public void testValidNoteHighMidiNoteNumber2() throws InvalidMidiDataException {
        Note note = new Note(10, 11, 100, 500);
        Pattern pattern = new Pattern();
        assertThrows( InvalidNoteException.class, () -> {
            pattern.addNote(note, 1200);
        });
    }

    @Test
    public void testValidNoteCorrectMidiNoteNumber() throws InvalidMidiDataException {
        Note note1 = new Note(0, 0, 100, 500);
        Note note2 = new Note(3, 8, 100, 500);
        Note note3 = new Note(9, 11, 100, 500);
        Note note4 = new Note(10, 7, 100, 500);
        Pattern pattern = new Pattern();
        assertDoesNotThrow(() -> {
            pattern.addNote(note1, 1200);
            pattern.addNote(note2, 1200);
            pattern.addNote(note3, 1200);
            pattern.addNote(note4, 1200);
        });
    }
    
    @Test
    public void testValidNoteHighVelocity1() throws InvalidMidiDataException { // Limit case
        Note note = new Note(0, 0, 128, 500);
        Pattern pattern = new Pattern();
        assertThrows( InvalidNoteException.class, () -> {
            pattern.addNote(note, 1200);
        });
    }

    @Test
    public void testValidNoteHighVelocity2() throws InvalidMidiDataException {
        Note note = new Note(0, 0, 247, 500);
        Pattern pattern = new Pattern();
        assertThrows( InvalidNoteException.class, () -> {
            pattern.addNote(note, 1200);
        });
    }

    @Test
    public void testValidNoteLowVelocity() throws InvalidMidiDataException {
        Note note = new Note(0, 0, -1, 500);
        Pattern pattern = new Pattern();
        assertThrows( InvalidNoteException.class, () -> {
            pattern.addNote(note, 1200);
        });
    }

    @Test
    public void testValidNoteCorrectVelocity() throws InvalidMidiDataException {
        Note note1 = new Note(0, 0, 0, 500);
        Note note2 = new Note(0, 0, 52, 500);
        Note note3 = new Note(0, 0, 93, 500);
        Note note4 = new Note(0, 0, 127, 500);
        Pattern pattern = new Pattern();
        assertDoesNotThrow(() -> {
            pattern.addNote(note1, 1200);
            pattern.addNote(note2, 1200);
            pattern.addNote(note3, 1200);
            pattern.addNote(note4, 1200);
        });
    }

}
