package poolongprojectn7;

import java.io.IOException;

import javax.sound.midi.*;

class MelodyTest {

    public static void createAndSaveMidi(Pattern pattern) {
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
            // High notes
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

            // Low notes
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
            
            // Save the pattern
            pattern.savePattern("/home/vic_pabo/Documents/test.mid");

        } catch (InvalidNoteException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void playSequence(Pattern pattern) {
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

    public static void main(String[] args) {
        try {
            // Play a new sequence
            Pattern pattern = new Pattern();    
            createAndSaveMidi(pattern);
            playSequence(pattern);

            // Play a sequence saved in the past
            pattern = new Pattern("/home/vic_pabo/Documents/test.mid");
            playSequence(pattern);
        } catch (InvalidMidiDataException | IOException e) {
            e.printStackTrace();
        }
    }
}
