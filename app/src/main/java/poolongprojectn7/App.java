package poolongprojectn7;

import javax.sound.midi.*;

import javafx.application.Application;

public class App {
    
    public static void main(String[] args) {
        
        Note note = new Note(5, 0, 100, 1000);
        Pattern pattern = new Pattern(null);
        try {
            pattern.addNote(note, 0);
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(pattern.getSequence());
            sequencer.start();
            while (sequencer.isRunning()) {
                Thread.sleep(1000); // Adjust as needed
            }
            sequencer.close();
        } catch (MidiUnavailableException | InvalidMidiDataException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
