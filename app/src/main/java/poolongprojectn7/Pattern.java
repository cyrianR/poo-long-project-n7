package poolongprojectn7;

import java.io.IOException;

import javax.sound.midi.*;

public class Pattern {

    private final int NUM_TRACKS = 128;
    private final int TICK_RESOLUTION = 300;
    private final int CHANNEL = 0;

    private Sequence sequence;
    private Track[] sequenceTracks = new Track[NUM_TRACKS];
    /* Total length of the pattern, expressed in MIDI ticks. */
    private long patternLength = 0;

    public Pattern() throws InvalidMidiDataException {
        this.sequence = initSequence();
    }

    public Pattern(String path) throws InvalidMidiDataException, IOException {
        this.sequence = MidiSystem.getSequence(new java.io.File(path));
        this.sequenceTracks = this.sequence.getTracks(); 
    }

    private Sequence initSequence() throws InvalidMidiDataException {
        Sequence sequence = null;
        sequence = new Sequence(Sequence.PPQ, TICK_RESOLUTION);

        // Adding a track for each musical note present in the NUM_OCTAVE octaves.
        // @TODO : optimisation
        for (int i = 0; i < NUM_TRACKS; i++) {
            sequenceTracks[i] = sequence.createTrack();
        }

        return sequence;
    }

    public void addNote(Note note, long tickStart) throws InvalidMidiDataException {
        int midiNote = note.getMidiNoteNumber();
        int velocity = note.getVelocity();
        long duration = note.getDurationTicks();
        long endTick = tickStart + duration;

        // MidiMessage for note-on and note-off event
        MidiMessage noteOnMessage = new ShortMessage(ShortMessage.NOTE_ON, CHANNEL, midiNote, velocity);
        MidiMessage noteOffMessage = new ShortMessage(ShortMessage.NOTE_OFF, CHANNEL, midiNote, 0);

        // MidiEvent for note-on and note-off messages
        MidiEvent noteOnEvent = new MidiEvent(noteOnMessage, tickStart);
        MidiEvent noteOffEvent = new MidiEvent(noteOffMessage, endTick);
        
        // Add note events to the corresponding track
        sequenceTracks[midiNote].add(noteOnEvent);
        sequenceTracks[midiNote].add(noteOffEvent);

        updatePatternLength();
    }

    public void removeNote(Note note, long tickStart) throws InvalidMidiDataException {
        int midiNote = note.getMidiNoteNumber();
        long duration = note.getDurationTicks();
        
        Track noteTrack = this.sequenceTracks[midiNote];

        // browse through the events of the track associated to the note
        for (int i = 0; i < noteTrack.size(); i++) {
            MidiEvent event = noteTrack.get(i);
            MidiMessage message = event.getMessage();
            if (message instanceof ShortMessage) {
                ShortMessage shortMessage = (ShortMessage) message;
                if (shortMessage.getData1() == midiNote
                        && event.getTick() == tickStart
                        && shortMessage.getCommand() == ShortMessage.NOTE_ON) {
                    // remove note-on event
                    this.sequenceTracks[midiNote].remove(event);
                } else if (shortMessage.getData1() == midiNote
                        && event.getTick() == tickStart + duration
                        && shortMessage.getCommand() == ShortMessage.NOTE_OFF) {
                    // remove note-off event
                    this.sequenceTracks[midiNote].remove(event);
                }
            }
        }

        updatePatternLength();
    }

    public void savePattern(String path) throws IOException {
        MidiSystem.write(sequence, 1, new java.io.File(path));
    }

    /*
     * @TODO : optimisation
     */
    private void updatePatternLength() {
        for (Track track : this.sequenceTracks) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                long eventTick = event.getTick();
                if (eventTick > this.patternLength) {
                    this.patternLength = eventTick;
                }
            }
        }
    }

    public Sequence getSequence() {
        return this.sequence;
    }

    public long getPatternLength() {
        return this.patternLength;
    }

}
