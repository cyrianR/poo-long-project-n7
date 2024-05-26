package poolongprojectn7;

import javax.sound.midi.*;
import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

/** Test program of the Pattern Class.
  */
class PatternTest {

    /* The file path where the temporary MIDI file will be stored */
    private static String filePath = System.getProperty("user.dir") + "/src/test/testMidi/";

    @BeforeAll
    static void setUp() {
        File tempFolder = new File(filePath);
        tempFolder.mkdirs();
    }

    @AfterAll
    static void closeUp() {
        File tempFolder = new File(filePath);
        File[] files = tempFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        tempFolder.delete();
    }

    @Test
    public void testEmptyConstructorAndGetters() throws InvalidMidiDataException {
        Pattern pattern = new Pattern();
        assertNotNull(pattern.getSequence());
        assertEquals(0, pattern.getPatternLength());
    }

    @Test
    public void testMidiConstructorAndGetters() throws InvalidMidiDataException, IOException {
        Pattern patternToSave = new Pattern();
        Note la = new Note(4, 9, 100, 500);
        Note si = new Note(4, 11, 100, 100);
        
        try {
        patternToSave.addNote(la, 0);
        patternToSave.addNote(si, 300);
        patternToSave.save(filePath, "patternsaved.mid");
        } catch (InvalidNoteException | IOException e) {
            e.printStackTrace();
        }
        
        Pattern pattern = new Pattern(filePath, "patternsaved.mid");
       
        assertNotNull(pattern.getSequence());
        assertEquals(500, pattern.getPatternLength());
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
        Track track = sequence.getTracks()[pattern.getChannel()];

        boolean CorrectEventFound = true;
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            System.out.print(event.getTick());
            if ((event.getTick() != noteTickStart) && (event.getTick() != pattern.getPatternLength())) {
                CorrectEventFound = false;
                break;
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
        Track track = sequence.getTracks()[pattern.getChannel()];

        boolean CorrectEventsFound = true;
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            System.out.print(event.getTick());
            if ((event.getTick() != note1TickStart) && (event.getTick() != note2TickStart)
                        && (event.getTick() != note1.getDurationTicks() + note1TickStart)
                        && (event.getTick() != note2.getDurationTicks() + note2TickStart)
                        && (event.getTick() != pattern.getPatternLength())){
                CorrectEventsFound = false;
                break;
            }
        }
        
        assertTrue(CorrectEventsFound, "Correct events not found for pattern 1");
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
        Track track = sequence.getTracks()[pattern.getChannel()];

        boolean CorrectEventsFound = true;
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            System.out.print(event.getTick());
            if ((event.getTick() != note1TickStart) && (event.getTick() != note2TickStart)
                        && (event.getTick() != note1.getDurationTicks() + note1TickStart)
                        && (event.getTick() != note2.getDurationTicks() + note2TickStart)
                        && (event.getTick() != pattern.getPatternLength())){
                CorrectEventsFound = false;
                break;
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

        /* Remove note 1 */
        pattern.removeNote(12 * 3 + 7, note1TickStart);
        //pattern.removeNote(note1, note1TickStart);
        Sequence sequence = pattern.getSequence();

        /* Same track as the notes have the same height */
        Track track = sequence.getTracks()[pattern.getChannel()];

        boolean CorrectEventsFound = true;
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            System.out.print(event.getTick());
            if ((event.getTick() != note2TickStart)
                && (event.getTick() != note2.getDurationTicks() + note2TickStart)
                && (event.getTick() != pattern.getPatternLength())){
                CorrectEventsFound = false;
                break;
            }
        }
        assertTrue(CorrectEventsFound, "Event not suppressed");
    }

    @Test
    public void testRemoveNoteInTrack2() throws InvalidMidiDataException, InvalidNoteException {
        Pattern pattern = new Pattern();
        Note note1 = new Note(3, 7, 100, 5000);
        Note note2 = new Note(3, 7, 100, 390);
        int note1TickStart = 20;
        int note2TickStart = 120;
        pattern.addNote(note1, note1TickStart);
        pattern.addNote(note2, note2TickStart);

        assertEquals(5020, pattern.getPatternLength());

        pattern.removeNote(3 * 12 + 7, note1TickStart);
        assertEquals(510, pattern.getPatternLength());
    
        pattern.removeNote(3 * 12 + 7, note2TickStart);
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

        pattern.removeNote(3 * 12 + 7, 600);

        Sequence sequence = pattern.getSequence();

        Track track = sequence.getTracks()[pattern.getChannel()];

        boolean CorrectEventsFound = true;
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            System.out.print(event.getTick());
            if ((event.getTick() != note1TickStart) && (event.getTick() != note2TickStart)
                        && (event.getTick() != note1.getDurationTicks() + note1TickStart)
                        && (event.getTick() != note2.getDurationTicks() + note2TickStart)
                        && (event.getTick() != pattern.getPatternLength())){
                CorrectEventsFound = false;
                break;
            }
        }

        assertTrue(CorrectEventsFound, "Correct events not found");
    }

}
