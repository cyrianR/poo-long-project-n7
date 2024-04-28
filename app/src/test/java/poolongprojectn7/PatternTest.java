package poolongprojectn7;

import javax.sound.midi.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

/** Test program of the Pattern Class.
  */
class PatternTest {

    @Test
    public void testEmptyConstructorAndGetters() throws InvalidMidiDataException {
        Pattern pattern = new Pattern();
        assertNotNull(pattern.getSequence());
        assertEquals(0, pattern.getPatternLength());
    }

    @Test
    public void testMidiConstructorAndGetters() throws InvalidMidiDataException, IOException {
        Pattern pattern = new Pattern("/home/vic_pabo/Documents/test.mid");
        
        assertNotNull(pattern.getSequence());
        assertEquals(0, pattern.getPatternLength());
    }

    @Test
    public void testAddNoteModifiesPatternLength1() throws InvalidMidiDataException, InvalidNoteException {
        Pattern pattern = new Pattern();
        Note note1 = new Note(3, 7, 100, 500);
        Note note2 = new Note(3, 7, 100, 500);
        pattern.addNote(note1, 20);
        pattern.addNote(note2, 120);
        assertEquals(620, pattern.getPatternLength());
    }

    @Test
    public void testAddNoteModifiesPatternLength2() throws InvalidMidiDataException, InvalidNoteException {
        Pattern pattern = new Pattern();
        Note note1 = new Note(3, 7, 100, 500);
        Note note2 = new Note(3, 7, 100, 390);
        pattern.addNote(note1, 20);
        pattern.addNote(note2, 120);
        assertEquals(520, pattern.getPatternLength());
    }  

    @Test
    public void testAddNoteModifiesPatternLength3() throws InvalidMidiDataException, InvalidNoteException {
        Pattern pattern = new Pattern();
        Note note1 = new Note(3, 7, 100, 500);
        Note note2 = new Note(3, 7, 100, 500);
        pattern.addNote(note1, 20);
        pattern.addNote(note2, 20);
        assertEquals(520, pattern.getPatternLength());
    }  

    @Test
    public void testAddNoteModifiesPatternLength4() throws InvalidMidiDataException, InvalidNoteException {
        Pattern pattern = new Pattern();
        Note note1 = new Note(3, 7, 100, 500);
        Note note2 = new Note(3, 7, 100, 390);
        pattern.addNote(note1, 20);
        pattern.addNote(note2, 20);
        assertEquals(520, pattern.getPatternLength());
    }  

    @Test
    public void testAddNoteInTrack1() throws InvalidMidiDataException, InvalidNoteException {
        Pattern pattern = new Pattern();
        Note note = new Note(3, 7, 100, 500);
        int noteTickStart = 20;
        pattern.addNote(note, noteTickStart);
        Sequence sequence = pattern.getSequence();
        Track track = sequence.getTracks()[note.getMidiNoteNumber()];

        boolean CorrectEventFound = false;
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            System.out.print(event.getTick());
            if ((event.getTick() != noteTickStart) && (event.getTick() != pattern.getPatternLength())) {
                CorrectEventFound = false;
                break;
            } else {
                CorrectEventFound = true;
            }
        }
        assertTrue(CorrectEventFound, "Correct event not found");
    }

    @Test
    public void testAddNoteInTrack2() throws InvalidMidiDataException, InvalidNoteException {
        Pattern pattern = new Pattern();
        Note note1 = new Note(3, 7, 100, 500);
        Note note2 = new Note(2, 5, 100, 390);
        int note1TickStart = 20;
        int note2TickStart = 120;
        pattern.addNote(note1, note1TickStart);
        pattern.addNote(note2, note2TickStart);

        Sequence sequence = pattern.getSequence();
        Track track1 = sequence.getTracks()[note1.getMidiNoteNumber()];
        Track track2 = sequence.getTracks()[note2.getMidiNoteNumber()];

        boolean CorrectEventFound1 = false;
        for (int i = 0; i < track1.size(); i++) {
            MidiEvent event = track1.get(i);
            System.out.print(event.getTick());
            if ((event.getTick() != note1TickStart) && (event.getTick() != note1.getDurationTicks() + note1TickStart) && (event.getTick() != pattern.getPatternLength())) {
                CorrectEventFound1 = false;
                break;
            } else {
                CorrectEventFound1 = true;
            }
        }

        boolean CorrectEventFound2 = false;
        for (int i = 0; i < track2.size(); i++) {
            MidiEvent event = track2.get(i);
            System.out.print(event.getTick());
            if ((event.getTick() != note2TickStart) && (event.getTick() != note2.getDurationTicks() + note2TickStart) && (event.getTick() != pattern.getPatternLength())) {
                CorrectEventFound2 = false;
                break;
            } else {
                CorrectEventFound2 = true;
            }
        }
        assertTrue(CorrectEventFound1, "Correct events not found for pattern 1");
        assertTrue(CorrectEventFound2, "Correct events not found for pattern 2");
    }

    @Test
    public void testAddNoteInTrack3() throws InvalidMidiDataException, InvalidNoteException {
        Pattern pattern = new Pattern();
        Note note1 = new Note(3, 7, 100, 500);
        Note note2 = new Note(3, 7, 100, 390);
        int note1TickStart = 20;
        int note2TickStart = 120;
        pattern.addNote(note1, note1TickStart);
        pattern.addNote(note2, note2TickStart);

        Sequence sequence = pattern.getSequence();

        // Same track because the notes have the same height
        Track track = sequence.getTracks()[note1.getMidiNoteNumber()];

        boolean CorrectEventsFound = false;
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            System.out.print(event.getTick());
            if ((event.getTick() != note1TickStart) && (event.getTick() != note2TickStart)
                        && (event.getTick() != note1.getDurationTicks() + note1TickStart)
                        && (event.getTick() != note2.getDurationTicks() + note2TickStart)
                        && (event.getTick() != pattern.getPatternLength())){
                CorrectEventsFound = false;
                break;
            } else {
                CorrectEventsFound = true;
            }
        }

        assertTrue(CorrectEventsFound, "Correct events not found");
    }

    @Test
    public void testRemoveNoteInTrack1() throws InvalidMidiDataException, InvalidNoteException {
        Pattern pattern = new Pattern();
        Note note1 = new Note(3, 7, 100, 500);
        Note note2 = new Note(3, 7, 100, 390);
        int note1TickStart = 20;
        int note2TickStart = 120;
        pattern.addNote(note1, note1TickStart);
        pattern.addNote(note2, note2TickStart);

        pattern.removeNote(note1, note1TickStart);
        Sequence sequence = pattern.getSequence();

        // Same track because the notes have the same height
        Track track = sequence.getTracks()[note1.getMidiNoteNumber()];

        boolean CorrectEventsFound = false;
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            System.out.print(event.getTick());
            if ((event.getTick() != note2TickStart)
                && (event.getTick() != note2.getDurationTicks() + note2TickStart)
                && (event.getTick() != pattern.getPatternLength())){
                CorrectEventsFound = false;
                break;
            } else {
                CorrectEventsFound = true;
            }
        }
        assertTrue(CorrectEventsFound, "Event not suppressed");
    }

    @Test
    public void testRemoveNoteInTrack2() throws InvalidMidiDataException, InvalidNoteException {
        Pattern pattern = new Pattern();
        Note note1 = new Note(3, 7, 100, 500);
        Note note2 = new Note(3, 7, 100, 390);
        int note1TickStart = 20;
        int note2TickStart = 120;
        pattern.addNote(note1, note1TickStart);
        pattern.addNote(note2, note2TickStart);

        pattern.removeNote(note1, note1TickStart);
        pattern.removeNote(note2, note2TickStart);

        assertEquals(0, pattern.getPatternLength());
    }

    @Test
    public void testRemoveNoteInTrack3() throws InvalidMidiDataException, InvalidNoteException {
        Pattern pattern = new Pattern();
        Note note1 = new Note(3, 7, 100, 500);
        Note note2 = new Note(3, 7, 100, 390);
        int note1TickStart = 20;
        int note2TickStart = 120;
        pattern.addNote(note1, note1TickStart);
        pattern.addNote(note2, note2TickStart);

        pattern.removeNote(note1, 600);

        Sequence sequence = pattern.getSequence();

        Track track = sequence.getTracks()[note1.getMidiNoteNumber()];

        boolean CorrectEventsFound = false;
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            System.out.print(event.getTick());
            if ((event.getTick() != note1TickStart) && (event.getTick() != note2TickStart)
                        && (event.getTick() != note1.getDurationTicks() + note1TickStart)
                        && (event.getTick() != note2.getDurationTicks() + note2TickStart)
                        && (event.getTick() != pattern.getPatternLength())){
                CorrectEventsFound = false;
                break;
            } else {
                CorrectEventsFound = true;
            }
        }

        assertTrue(CorrectEventsFound, "Correct events not found");
    }

}
