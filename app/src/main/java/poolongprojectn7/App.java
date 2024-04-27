package poolongprojectn7;

import javax.sound.midi.*;

import javafx.application.Application;

public class App {
    
    public static void main(String[] args) {
        
        /* Note note = new Note(5, 0, 10000);
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
        } */

        try {
            Sequence sequence = new Sequence(Sequence.PPQ, 300);
            Track track = sequence.createTrack();
            // MidiMessage for note-on and note-off event
            MidiMessage noteOnMessage = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 100);
            MidiMessage noteOffMessage = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60);

            // MidiEvent for note-on and note-off messages
            MidiEvent noteOnEvent = new MidiEvent(noteOnMessage, 0);
            MidiEvent noteOffEvent = new MidiEvent(noteOffMessage, 1000);
        
            // Add note events to the corresponding track
            track.add(noteOnEvent);
            track.add(noteOffEvent);

            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(sequence);
            sequencer.start();
            while (sequencer.isRunning()) {
                Thread.sleep(1000); // Adjust as needed
            }
            sequencer.close();
        } catch (InvalidMidiDataException | InterruptedException | MidiUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
