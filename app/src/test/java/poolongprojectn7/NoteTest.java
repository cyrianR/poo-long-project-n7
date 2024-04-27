package poolongprojectn7;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
    
}
