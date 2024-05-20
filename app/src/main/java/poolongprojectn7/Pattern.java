package poolongprojectn7;

import java.io.IOException;

import javax.sound.midi.*;

/**
 * Represents a pattern used by a piano roll to
 * manipulate the musical data in the piano roll.
 * This is a part of the piano roll model.
 * @author Cyrian Ragot and Victor Barilly
 * @version Alpha
 */
public class Pattern {
    /* Maximum index of the note index. */
    private final int NOTE_INDEX_MAX = 11;
    /* Number of tracks in a pattern, there is one track for each musical note. */
    private final int NUM_TRACKS = 128;
    /* Resolution of the pattern sequence in ticks. */
    private final int TICK_RESOLUTION = 300;
    /* Working channel for all notes. */
    private final int CHANNEL = 0;

    /* Sequence of the pattern. */
    private Sequence sequence;
    /* Array of the tracks present in the sequence. */
    private Track[] sequenceTracks = new Track[NUM_TRACKS];
    /* Total length of the pattern, expressed in ticks. */
    private long patternLength;
    /* Instrument used in the pattern */
    private int instrument;

    /**
     * Create a new pattern.
     */
    public Pattern() {
        try {
            this.sequence = initSequence();
        } catch (InvalidMidiDataException e) {
            /* If the specified sequence's divisionType is not valid */
            e.printStackTrace();
        }
        this.patternLength = 0;
        this.instrument = 0;    
    }

    /**
     * Load a pattern from a midi file passed in argument.
     * @param path path to the midi file containing the midi sequence
     * @throws InvalidMidiDataException if the File does not point to valid MIDI file data recognized by the system
     * @throws IOException if an I/O exception occurs
     */
    public Pattern(String path) throws InvalidMidiDataException, IOException {
        this.sequence = MidiSystem.getSequence(new java.io.File(path));
        this.sequenceTracks = this.sequence.getTracks();
        this.patternLength = 0;
        setInstrument(getInstrument(this.sequence));
        updatePatternLength();
    }

    /**
     * Initiate the sequence of the pattern.
     * @return the initialised sequence
     * @throws InvalidMidiDataException if the specified sequence's divisionType is not valid
     */
    private Sequence initSequence() throws InvalidMidiDataException {
        Sequence sequence = null;
        sequence = new Sequence(Sequence.PPQ, TICK_RESOLUTION);
        sequenceTracks[CHANNEL] = sequence.createTrack();
        return sequence;
    }

    /**
     * Add a note to the pattern at a certain time.
     * @param note note to be added
     * @param tickStart starting time of the note in ticks
     * @throws InvalidNoteException if the note attributs are incorrect
     */
    public void addNote(Note note, long tickStart) throws InvalidNoteException {
        if (note.getNoteIndex() > NOTE_INDEX_MAX) {
            throw new InvalidNoteException("Midi note number or note velocity is invalid.");
        }
        
        int midiNote = note.getMidiNoteNumber();
        int velocity = note.getVelocity();
        long duration = note.getDurationTicks();
        long endTick = tickStart + duration;

        /* MidiMessage for note-on and note-off event */
        MidiMessage noteOnMessage;
        MidiMessage noteOffMessage;
        try {
            noteOnMessage = new ShortMessage(ShortMessage.NOTE_ON, CHANNEL, midiNote, velocity);
            noteOffMessage = new ShortMessage(ShortMessage.NOTE_OFF, CHANNEL, midiNote, 0);
        } catch (InvalidMidiDataException e) {
            throw new InvalidNoteException("Midi note number or note velocity is invalid.");
        }

        /* MidiEvent for note-on and note-off messages */
        MidiEvent noteOnEvent = new MidiEvent(noteOnMessage, tickStart);
        MidiEvent noteOffEvent = new MidiEvent(noteOffMessage, endTick);
        
        /* Add note events to the corresponding track */
        sequenceTracks[CHANNEL].add(noteOnEvent);
        sequenceTracks[CHANNEL].add(noteOffEvent);

        updatePatternLength();
    }

    /**
     * Remove a specific note from the pattern.
     * @param note the note to be removed
     * @param tickStart the starting tick of the note
     */
    public void removeNote(Note note, long tickStart) {
        int midiNote = note.getMidiNoteNumber();
        long duration = note.getDurationTicks();
        
        Track noteTrack = this.sequenceTracks[CHANNEL];

        /* Browse through the events of the track associated to the note */
        for (int i = 0; i < noteTrack.size(); i++) {
            MidiEvent event = noteTrack.get(i);
            MidiMessage message = event.getMessage();
            if (message instanceof ShortMessage) {
                ShortMessage shortMessage = (ShortMessage) message;
                /* Remove the note start */
                if (shortMessage.getData1() == midiNote
                        && event.getTick() == tickStart
                        && shortMessage.getCommand() == ShortMessage.NOTE_ON) {
                    this.sequenceTracks[CHANNEL].remove(event);
                } else if (shortMessage.getData1() == midiNote
                        && event.getTick() == tickStart + duration
                        && shortMessage.getCommand() == ShortMessage.NOTE_OFF) {

                    this.sequenceTracks[CHANNEL].remove(event);
                }
            }
        }
        /* Remove the end of track event if present */
        for (int i = 0; i < noteTrack.size(); i++) {
            MidiEvent event = noteTrack.get(i);
            if (event.getTick() == tickStart + duration) {
                this.sequenceTracks[CHANNEL].remove(event);
                break;
            }
        }
        updatePatternLength();
    }

    /**
     * Save the pattern's sequence in a midi file.
     * @param path the midi file path to save the pattern's sequence (.mid)
     * @throws IOException if the given file path is incorrect
     */
    public void savePattern(String path) throws IOException {
        MidiSystem.write(sequence, 1, new java.io.File(path));
    }

    /**
     * Update the pattern length attribut.
     */
    private void updatePatternLength() {
        long maxEventTick = 0;

        Track noteTrack = this.sequenceTracks[CHANNEL];

        for (int i = 0; i < noteTrack.size(); i++) {
            MidiEvent event = noteTrack.get(i);
            long eventTick = event.getTick();
            if (eventTick > maxEventTick) {
                maxEventTick = eventTick;
            }
        }

        this.patternLength = maxEventTick;
    }

    /**
     * Obtains the sequence of the pattern.
     * @return the sequence of the pattern
     */
    public Sequence getSequence() {
        return this.sequence;
    }

    /**
     * Obtains the pattern length.
     * @return the pattern length
     */
    public long getPatternLength() {
        return this.patternLength;
    }

    /**
     * Obtains the instrument of the pattern.
     * @return the instrument of the pattern
     */
    public int getInstrument() {
        return this.instrument;
    }

    /**
     * Obtains the instrument used in the sequence.
     * @param sequence the sequence
     * @return the instrument of the pattern
     */
    private int getInstrument(Sequence sequence) {
        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == ShortMessage.PROGRAM_CHANGE) {
                        return sm.getData1();
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Change the instrument of the pattern
     * @param instrument the new instrument
     * @throws InvalidMidiDataException if MIDI data is not valid
     */
    public void setInstrument(int instrument) throws InvalidMidiDataException {
        this.instrument = instrument;
        ShortMessage instrumentChange = new ShortMessage();
        try {
            instrumentChange.setMessage(ShortMessage.PROGRAM_CHANGE, CHANNEL, instrument, 0);
        } catch (InvalidMidiDataException e) {
            throw new InvalidMidiDataException("Invalid MIDI data for instrument change.");
        }
        for (Track track : this.sequenceTracks) {
            if (track != null) {
                MidiEvent instrumentChangeEvent = new MidiEvent(instrumentChange, 0);
                track.add(instrumentChangeEvent);
            }
        }
    }

    /**
     * Obtains the number of the channel of the pattern.
     * @return the number of the channel of the pattern
     */
    public int getChannel() {
        return this.CHANNEL;
    }

}
