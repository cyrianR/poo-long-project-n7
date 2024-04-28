package poolongprojectn7;

import javax.sound.midi.*;

import javafx.application.Application;

public class App {
    
    public static void main(String[] args) {
        
        Note note1 = new Note(5, 5, 100, 500);
        Note note2 = new Note(5, 5, 100, 500);

        try {
            Pattern pattern = new Pattern();
            pattern.addNote(note1, 1200);
            pattern.addNote(note2, 5000);
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(pattern.getSequence());
            sequencer.start();
            while (sequencer.isRunning()) {
                Thread.sleep(500);
            }
            sequencer.close();

            pattern.removeNote(note1, 1200);
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

        /* try {
            Sequence sequence = new Sequence(Sequence.PPQ, 300);
            Track track1 = sequence.createTrack();
            // MidiMessage for note-on and note-off event
            MidiMessage noteOnMessage = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 100);
            MidiMessage noteOffMessage = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 0);

            //  MidiEvent for note-on and note-off messages
            MidiEvent noteOnEvent1 = new MidiEvent(noteOnMessage, 1000);
            MidiEvent noteOffEvent1 = new MidiEvent(noteOffMessage, 2000);
        
            // Add note events to the corresponding track
            track1.add(noteOnEvent1);
            track1.add(noteOffEvent1);

            Track[] tracks = sequence.getTracks();
            Track track = tracks[0];
            
            //  MidiEvent for note-on and note-off messages
            MidiEvent noteOnEvent2 = new MidiEvent(noteOnMessage, 3000);
            MidiEvent noteOffEvent2 = new MidiEvent(noteOffMessage, 4000);
        
            // Add note events to the corresponding track
            track.add(noteOnEvent2);
            track.add(noteOffEvent2);

            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(sequence);
            sequencer.start();
            while (sequencer.isRunning()) {
                Thread.sleep(500);
            }

            sequencer.close();


        } catch (InvalidMidiDataException | MidiUnavailableException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } */
        

        
    }
}
