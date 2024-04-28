package poolongprojectn7;

import java.io.IOException;

import javax.sound.midi.*;

/**
 * Represents a pattern used by a piano roll to
 * manipulate the musical data in the piano roll.
 * This is a part of the piano roll model.
 * @author Ragot Cyrian
 * @version Alpha
 */
public class Pattern {

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
    private long patternLength = 0;

    /**
     * Create a new pattern.
     * @throws InvalidMidiDataException if the specified sequence's divisionType is not valid
     */
    public Pattern() throws InvalidMidiDataException {
        this.sequence = initSequence();
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
    }

    /**
     * Initiate the sequence of the pattern.
     * @return the initialised sequence
     * @throws InvalidMidiDataException if the specified sequence's divisionType is not valid
     */
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

    /**
     * Add a note to the pattern at a certain time.
     * @param note note to be added
     * @param tickStart starting time of the note in ticks
     * @throws InvalidNoteException if the note attributs are incorrect
     */
    public void addNote(Note note, long tickStart) throws InvalidNoteException {
        int midiNote = note.getMidiNoteNumber();
        int velocity = note.getVelocity();
        long duration = note.getDurationTicks();
        long endTick = tickStart + duration;

        // MidiMessage for note-on and note-off event
        MidiMessage noteOnMessage;
        MidiMessage noteOffMessage;
        try {
            noteOnMessage = new ShortMessage(ShortMessage.NOTE_ON, CHANNEL, midiNote, velocity);
            noteOffMessage = new ShortMessage(ShortMessage.NOTE_OFF, CHANNEL, midiNote, 0);
        } catch (InvalidMidiDataException e) {
            throw new InvalidNoteException("Midi note number or note velocity is invalid.");
        }

        // MidiEvent for note-on and note-off messages
        MidiEvent noteOnEvent = new MidiEvent(noteOnMessage, tickStart);
        MidiEvent noteOffEvent = new MidiEvent(noteOffMessage, endTick);
        
        // Add note events to the corresponding track
        sequenceTracks[midiNote].add(noteOnEvent);
        sequenceTracks[midiNote].add(noteOffEvent);

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

}
