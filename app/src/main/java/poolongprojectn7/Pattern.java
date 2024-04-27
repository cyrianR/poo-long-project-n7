package poolongprojectn7;

import javax.sound.midi.*;

public class Pattern {

    private final int NUM_TRACKS = 128;
    private final int TICK_RESOLUTION = 300;
    private final int CHANNEL = 0;

    private Instrument instrument;
    private Sequence sequence;
    private Track[] sequenceTracks = new Track[NUM_TRACKS];
    /* Total length of the pattern, expressed in MIDI ticks. */
    private long patternLength = 0;

    public Pattern(Instrument instrument) {
        this.instrument = instrument;
        this.sequence = initSequence();
    }

    private Sequence initSequence() {
        Sequence sequence = null;
        try {
            sequence = new Sequence(Sequence.PPQ, TICK_RESOLUTION);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

        // Adding a track for each musical note present in the NUM_OCTAVE octaves.
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
        MidiMessage noteOffMessage = new ShortMessage(ShortMessage.NOTE_OFF, CHANNEL, midiNote);

        // MidiEvent for note-on and note-off messages
        MidiEvent noteOnEvent = new MidiEvent(noteOnMessage, tickStart);
        MidiEvent noteOffEvent = new MidiEvent(noteOffMessage, endTick);
        
        // Add note events to the corresponding track
        sequenceTracks[midiNote].add(noteOnEvent);
        sequenceTracks[midiNote].add(noteOffEvent);

        updatePatternLength();
    }

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

    public Instrument getInstrument() {
        return this.instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public Sequence getSequence() {
        return this.sequence;
    }

    public long getPatternLength() {
        return this.patternLength;
    }

}
