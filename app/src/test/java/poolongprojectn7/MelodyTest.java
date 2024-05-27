package poolongprojectn7;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javax.swing.JOptionPane;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import javax.sound.midi.*;

class MelodyTest {

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

    /**
     * Write and save a melody in a given pattern.
     * @param pattern the pattern to modify
     */
    public void createMelody(Pattern pattern) {
        Note reb_trans = new Note(7, 1, 100, 500);
        Note si_trans = new Note(6, 11, 100, 500);
        Note lab_trans = new Note(6, 8, 100, 500);

        Note si2 = new Note(5, 11, 100, 500);
        Note reb2 = new Note(6, 1, 100, 500);
        Note solb2 = new Note(6, 6, 100, 500);
        Note fa2 = new Note(6, 4, 100, 500);
        Note re2 = new Note(6, 3, 100, 500);

        Note reb0 = new Note(5, 1, 100, 500);
        Note fa0 = new Note(5, 4, 100, 500);
        Note si0 = new Note(4, 11, 100, 500);
        Note la0 = new Note(4, 9, 100, 500);

        try {
            /* High notes */
            pattern.addNote(solb2, 0);
            pattern.addNote(fa2, 50);
            pattern.addNote(fa2, 200);

            pattern.addNote(fa2, 500);
            pattern.addNote(fa2, 650);
            pattern.addNote(fa2, 800);
            pattern.addNote(fa2, 950);
            pattern.addNote(re2, 1100);
            pattern.addNote(re2, 1250);
            pattern.addNote(fa2, 1400);
            pattern.addNote(fa2, 1550);

            pattern.addNote(reb_trans, 1850);
            pattern.addNote(si_trans, 2000);
            pattern.addNote(lab_trans, 2150);

            pattern.addNote(solb2, 2300);
            pattern.addNote(fa2, 2450);
            pattern.addNote(fa2, 2600);

            pattern.addNote(fa2, 2900);
            pattern.addNote(fa2, 3050);
            pattern.addNote(fa2, 3200);
            pattern.addNote(fa2, 3350);
            pattern.addNote(reb2, 3500);
            pattern.addNote(reb2, 3650);
            pattern.addNote(si2, 3800);
            pattern.addNote(si2, 3950);

            /* Low notes */
            pattern.addNote(reb0, 0);
            pattern.addNote(reb0, 50);
            pattern.addNote(reb0, 200);

            pattern.addNote(fa0, 500);
            pattern.addNote(fa0, 650);
            pattern.addNote(fa0, 800);
            pattern.addNote(fa0, 950);
            pattern.addNote(si0, 1100);
            pattern.addNote(si0, 1250);
            pattern.addNote(si0, 1400);
            pattern.addNote(la0, 1550);

            pattern.addNote(reb0, 2300);
            pattern.addNote(reb0, 2450);
            pattern.addNote(reb0, 2600);

            pattern.addNote(fa0, 2900);
            pattern.addNote(fa0, 3050);
            pattern.addNote(fa0, 3200);
            pattern.addNote(fa0, 3350);
            pattern.addNote(si0, 3500);
            pattern.addNote(si0, 3650);
            pattern.addNote(si0, 3800);
            pattern.addNote(la0, 3950);   

        } catch (InvalidNoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Play the sequence of a given pattern.
     * @param pattern the pattern
     */
    public void playSequence(Pattern pattern) {
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(pattern.getSequence());
            sequencer.start();
            while (sequencer.isRunning()) {
                Thread.sleep(500);
            }
            sequencer.close();
        } catch (MidiUnavailableException | InvalidMidiDataException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Write and save a melody in a given pattern.
     * @param pattern the pattern to modify
     */
    public void createKicks(Pattern pattern) {
        Note kick = new Note(4, 5, 100, 500);

        try {
            pattern.addNote(kick, 0);
            for (int i = 0; i < 3800; i = i + 300) {
                pattern.addNote(kick, 200 + i);
            }
        } catch (InvalidNoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadAndWrite() throws InvalidMidiDataException, IOException {
            /* Create and play a new sequence */
            Pattern melody = new Pattern();
            melody.setInstrument(34);
            createMelody(melody);
            /* Save the pattern */
            melody.save(filePath, "test");
            
            /* Play a sequence saved in the past */
            Pattern testReadMelody = new Pattern(filePath, "test");
            playSequence(testReadMelody);
            assertTrue(true);
    }

    @Test
    public void testPlaylist() throws InvalidMidiDataException, IOException {
        /* Create the melody */
        Pattern melody = new Pattern();
        melody.setInstrument(34);
        createMelody(melody);
        melody.save(filePath, "melody"); // The save is mandatory to export
        melody.export(filePath, "melody_wav");
        
        /* Create the kicks */
        Pattern kicks = new Pattern();
        kicks.setInstrument(113);
        createKicks(kicks);
        kicks.save(filePath, "kicks");
        kicks.export(filePath, "kicks_wav");

        /* Test the playlist */
        Playlist playlist = new Playlist();
        SoundEntry musique = new SoundEntry(filePath + "melody_wav.wav");
        SoundEntry soundToAdd = new SoundEntry(filePath + "kicks_wav.wav");
        playlist.addSound(musique, 0);
        playlist.addSound(soundToAdd, 0);
        
        playlist.playAll();
        assertTrue(true);

        /* Unmute this to test */
        //JOptionPane.showMessageDialog(null, "Clic to stop playing");
    }

}
