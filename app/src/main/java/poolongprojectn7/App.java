package poolongprojectn7;

import javax.sound.midi.*;

import javafx.application.Application;

public class App {
    
    public static void main(String[] args) {
        
        Note note1 = new Note(5, 0, 100, 500);
        Note note2 = new Note(5, 5, 100, 500);

        Pattern pattern = new Pattern(null);
        try {
            pattern.addNote(note1, 1200);
            pattern.addNote(note2, 5000);
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(pattern.getSequence());
            Sequence seq = sequencer.getSequence();
            Track tracks[] = seq.getTracks();
            Track trackToTest = tracks[note1.getMidiNoteNumber()];
            System.out.println(trackToTest.size());
            System.out.println(((ShortMessage)trackToTest.get(0).getMessage()).getCommand());
            System.out.println(((ShortMessage)trackToTest.get(1).getMessage()).getCommand());
            System.out.println(trackToTest.get(2).getMessage());
            sequencer.start();
            while (sequencer.isRunning()) {
                System.out.println(sequencer.getTickPosition());
                Thread.sleep(500);
            }
            sequencer.close();
        } catch (MidiUnavailableException | InvalidMidiDataException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
