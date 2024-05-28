package poolongprojectn7;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.midi.*;
import java.io.File;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Represents a pattern used by a piano roll to
 * manipulate the musical data in the piano roll.
 * This is a part of the piano roll model.
 * 
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
    /* Path to the midi file of the pattern */
    private String path;
    /* Link between notes and the moment when they are called */
    private Map<Long, List<Note>> noteMap;
    /* Sequencer used to play the sequence. */
    private Sequencer sequencer = null;
    private long positionTick  = 0;

    /**
     * Create a new pattern.
     */
    public Pattern() {
        this.path = null;
        this.patternLength = 0;
        this.instrument = 0;
        this.noteMap = new HashMap<>();
        try {
            this.sequence = initSequence();
            this.sequencer = MidiSystem.getSequencer();
            this.sequencer.open();
            this.sequencer.setSequence(this.sequence);
            /* If the specified sequence's divisionType is not valid */
        } catch (InvalidMidiDataException | MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load a pattern from a midi file passed in argument.
     * 
     * @param filePath the path of the file
     * @param fileName the name of the file
     * @throws InvalidMidiDataException if the File does not point to valid MIDI
     *                                  file data recognized by the system
     * @throws IOException              if an I/O exception occurs
     *                                  Example: Pattern pattern = new
     *                                  Patten("/home/usr/Music/", "test");
     */
    public Pattern(String filePath, String fileName) throws InvalidMidiDataException, IOException {
        this.path = filePath + fileName + ".mid";
        this.sequence = MidiSystem.getSequence(new java.io.File(this.path));
        this.sequenceTracks = this.sequence.getTracks();
        this.patternLength = 0;
        this.noteMap = new HashMap<>();
        this.noteMap = getNoteMap(this.sequenceTracks);
        setInstrument(getInstrument(this.sequence));
        updatePatternLength();
        try {
            this.sequencer = MidiSystem.getSequencer();
            this.sequencer.open();
            this.sequencer.setSequence(this.sequence);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initiate the sequence of the pattern.
     * 
     * @return the initialised sequence
     * @throws InvalidMidiDataException if the specified sequence's divisionType is
     *                                  not valid
     */
    private Sequence initSequence() throws InvalidMidiDataException {
        Sequence sequence = null;
        sequence = new Sequence(Sequence.PPQ, TICK_RESOLUTION);
        sequenceTracks[CHANNEL] = sequence.createTrack();
        return sequence;
    }

    /**
     * Add a note to the noteMap and creates a new ArrayList if the key does not
     * exist.
     * 
     * @param note      the note
     * @param startTick the start tick of the note
     */
    private void addNoteToMap(Note note, Long startTick) {
        this.noteMap.computeIfAbsent(startTick, k -> new ArrayList<>()).add(note);
    }

    /**
     * Remove a note to the noteMap if it exists.
     * 
     * @param note      the note
     * @param startTick the start tick of the note
     */
    private void removeNoteToMap(Note note, Long startTick) {
        List<Note> notes = this.noteMap.get(startTick);
        /* Remove the note If there is a note with the given startTick */
        if (notes != null) {
            notes.remove(note);
            /* Remove the startTick entry of the map if the list becomes empty */
            if (notes.isEmpty()) {
                this.noteMap.remove(startTick);
            }
        }

    }

    /**
     * Add a note to the pattern at a certain time.
     * 
     * @param note      note to be added
     * @param startTick starting time of the note in ticks
     * @throws InvalidNoteException if the note attributs are incorrect
     */
    public void addNote(Note note, long startTick) throws InvalidNoteException {
        if (note.getNoteIndex() > NOTE_INDEX_MAX) {
            throw new InvalidNoteException("Midi note number or note velocity is invalid.");
        }
        /* Add the note to the note map */
        addNoteToMap(note, startTick);

        int midiNote = note.getMidiNoteNumber();
        int velocity = note.getVelocity();
        long duration = note.getDurationTicks();
        long endTick = startTick + duration;

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
        MidiEvent noteOnEvent = new MidiEvent(noteOnMessage, startTick);
        MidiEvent noteOffEvent = new MidiEvent(noteOffMessage, endTick);

        /* Add note events to the corresponding track */
        sequenceTracks[CHANNEL].add(noteOnEvent);
        sequenceTracks[CHANNEL].add(noteOffEvent);

        updatePatternLength();
    }

    /**
     * Remove a specific note from the pattern.
     * 
     * @param note      the note to be removed
     * @param startTick the starting tick of the note
     */
    private void removeNote(Note note, long startTick) {
        /* Remove the note to the note map */
        removeNoteToMap(note, startTick);

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
                        && event.getTick() == startTick
                        && shortMessage.getCommand() == ShortMessage.NOTE_ON) {
                    this.sequenceTracks[CHANNEL].remove(event);
                } else if (shortMessage.getData1() == midiNote
                        && event.getTick() == startTick + duration
                        && shortMessage.getCommand() == ShortMessage.NOTE_OFF) {
                    this.sequenceTracks[CHANNEL].remove(event);
                }
            }
        }
        /* Remove the end of track event if present */
        for (int i = 0; i < noteTrack.size(); i++) {
            MidiEvent event = noteTrack.get(i);
            if (event.getTick() == startTick + duration) {
                this.sequenceTracks[CHANNEL].remove(event);
                break;
            }
        }
        updatePatternLength();
    }

    /**
     * Remove a specific note from the pattern.
     * 
     * @param midiNoteNumber the midi note number of the note to be removed
     * @param startTick      the starting tick of the note
     */
    public void removeNote(int midiNoteNumber, long startTick) {
        List<Note> notes = noteMap.get(startTick);
        if (notes != null) {
            /*
             * Create an iterator to avoid ConcurrentModificationException
             * by modifying the notes list
             */
            Iterator<Note> iterator = notes.iterator();
            while (iterator.hasNext()) {
                Note note = iterator.next();
                /* Remove the note from the iterator and the note itself */
                if (note.getMidiNoteNumber() == midiNoteNumber) {
                    iterator.remove();
                    removeNote(note, startTick);
                }
            }
            /* Remove the startTick entry if there are no notes anymore at that time */
            if (notes.isEmpty()) {
                this.noteMap.remove(startTick);
            }
        }
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
     * 
     * @return the sequence of the pattern
     */
    public Sequence getSequence() {
        return this.sequence;
    }

    /**
     * Obtains the pattern length.
     * 
     * @return the pattern length
     */
    public long getPatternLength() {
        return this.patternLength;
    }

    /**
     * Obtains the instrument of the pattern.
     * 
     * @return the instrument of the pattern
     */
    public int getInstrument() {
        return this.instrument;
    }

    /**
     * Obtains the instrument used in the sequence.
     * 
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
     * 
     * @param instrument the number if the instrument
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
     * 
     * @return the number of the channel of the pattern
     */
    public int getChannel() {
        return this.CHANNEL;
    }

    /**
     * Obtains the note map.
     * 
     * @return the note map
     */
    public Map<Long, List<Note>> getNoteMap() {
        return this.noteMap;
    }

    /**
     * Obtains the note map from a sequence of tracks.
     * 
     * @param sequenceTracks the sequence of tracks
     * @return the note map
     */
    private Map<Long, List<Note>> getNoteMap(Track[] sequenceTracks) {
        Map<Long, List<Note>> notes = new HashMap<>();
        HashMap<Integer, List<Long>> activeNotes = new HashMap<>();

        Track noteTrack = sequenceTracks[CHANNEL];

        /* Browse through the events of the track */
        for (int i = 0; i < noteTrack.size(); i++) {
            MidiEvent event = noteTrack.get(i);
            MidiMessage message = event.getMessage();
            if (message instanceof ShortMessage) {
                ShortMessage shortMessage = (ShortMessage) message;

                int key = shortMessage.getData1();
                int velocity = shortMessage.getData2();
                long tick = event.getTick();

                /* Save the startTick if the note is activated */
                if (shortMessage.getCommand() == ShortMessage.NOTE_ON && velocity > 0) {
                    activeNotes.computeIfAbsent(key, k -> new ArrayList<>()).add(tick);
                }
                /* Get the startTick and save the note otherwise */
                else if (shortMessage.getCommand() == ShortMessage.NOTE_OFF ||
                        (shortMessage.getCommand() == ShortMessage.NOTE_ON && velocity == 0)) {
                    List<Long> startTicks = activeNotes.get(key);
                    if (startTicks != null && !startTicks.isEmpty()) {
                        Long startTick = startTicks.remove(0);
                        if (startTicks.isEmpty()) {
                            activeNotes.remove(key);
                        }
                        if (startTick != null) {
                            notes.computeIfAbsent(startTick, k -> new ArrayList<>())
                                    .add(new Note(key / 12, key % 12, velocity, tick - startTick));
                        }
                    }
                }
            }
        }
        return notes;
    }

    /**
     * Save the pattern's sequence in a midi file.
     * 
     * @param filePath the path of the file
     * @param fileName the name of the file
     * @throws IOException if the given file path is incorrect
     *                     Example: patten.save("/home/usr/Music/", "test");
     */
    public void save(String filePath, String fileName) throws IOException {
        this.path = filePath + fileName + ".mid";
        MidiSystem.write(this.sequence, 1, new java.io.File(this.path));
    }

    /**
     * Export the pattern to a .wav file.
     * 
     * @param filePath the path of the file
     * @param fileName the name of the file
     *                 Example: patten.export("/home/usr/Music/", "test");
     */
    public void export(String filePath, String fileName) {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(this.path));
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, new File(filePath + fileName + ".wav"));
            stream.close();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the pattern.
     */
    public void close() {
        sequencer.close();
    }

    /** Play the notes in the pattern from position zero. */
    public void play() {
        if (sequencer.isRunning()) {
            sequencer.stop();
        }
        sequencer.start();
        sequencer.setTickPosition(0);
        this.positionTick = 0;
    }

    /** Pause and restart playing at a certain position. */
    public void pause() {
        if (sequencer.isRunning()) {
            this.positionTick = sequencer.getTickPosition();
            sequencer.stop();
        } else {
            sequencer.start();
            sequencer.setTickPosition(positionTick);
        }
    }

    /** Stop the playing of the notes and restart to position zero. */
    public void stop() {
        if (sequencer.isRunning()) {
            sequencer.stop();
            this.positionTick = 0;
        }
    }

}
