package poolongprojectn7;

import javax.sound.midi.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Test program of the Pattern Class.
 * @TODO change initializations with other instruments
  */
class PatternTest {

    @Test
    public void testConstructorAndGetters() {
        Pattern pattern = new Pattern();
        assertNotNull(pattern.getSequence());
        assertEquals(0, pattern.getPatternLength());
    }

    @Test
    public void testAddNote() throws InvalidMidiDataException {
        Pattern pattern = new Pattern();
        Note note = new Note(3, 7, 100, 500);
        pattern.addNote(note, 20);
        pattern.addNote(note, 120);
        assertEquals(620, pattern.getPatternLength());
    }    
}
