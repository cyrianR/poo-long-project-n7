package poolongprojectn7;

/**
 * Represents a note that can be added to a Pattern.
 * This is a part of the piano roll model.
 * @version Alpha
 */
public class Note {

    /* Integer corresponding to an octave, 0 is the lower octave and 10 the higher. */
    private int octave;
    /*
     * Integer corresponding to a musical note (0-11).
     * 0 is equivalent to a C note, 1 for a C# and so on.
     */
    private int noteIndex;
    /* Midi note number (0-127), depends on octave and noteIndex. */
    private int midiNoteNumber;
    /* Velocity of the note (0-127). */
    private int velocity;
    /* Duration of the note in ticks. One tick is 1/TICK_RESOLUTION of a quarter note. */
    private long durationTicks;

    /**
     * Create a note object.
     * @param octave octave of the note (0-10)
     * @param noteIndex musical note index (0-11) 
     * @param velocity velocity of the note (0-127)
     * @param durationTicks duration of the note expressed in ticks
     */
    public Note(int octave, int noteIndex, int velocity, long durationTicks) {
        this.octave = octave;
        this.noteIndex = noteIndex;
        this.velocity = velocity;
        this.durationTicks = durationTicks;
        setMidiNoteNumber(octave, noteIndex);
    }

    /**
     * Sets the midi note number.
     * @param octave 
     * @param noteIndex
     */
    private void setMidiNoteNumber(int octave, int noteIndex) {
        this.midiNoteNumber = octave * 12 + noteIndex;
    }

    /**
     * Obtains the octave of the note.
     * @return the octave of the note
     */
    public int getOctave() {
        return this.octave;
    }

    /**
     * Sets the octave of the note.
     * @param octave the new octave to be set
     */
    public void setOctave(int octave) {
        this.octave = octave;
        setMidiNoteNumber(this.octave, this.noteIndex);
    }

    /**
     * Obtains the musical note index.
     * @return the musical note index
     */
    public int getNoteIndex() {
        return this.noteIndex;
    }

    /**
     * Sets the musical note index.
     * @param noteIndex the new musical note index to be set.
     */
    public void setNoteIndex(int noteIndex) {
        this.noteIndex = noteIndex;
        setMidiNoteNumber(this.octave, this.noteIndex);
    }

    /**
     * Obtains the midi note number.
     * @return the midi note number
     */
    public int getMidiNoteNumber() {
        return this.midiNoteNumber;
    }

    /**
     * Obtains the velocity of the note.
     * @return the velocity of the note
     */
    public int getVelocity() {
        return this.velocity;
    }

    /**
     * Sets the velocity of the note.
     * @param velocity the new velocity for the note
     */
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    /**
     * Obtains the duration time of the note in ticks.
     * @return the duration time of the note in ticks
     */
    public long getDurationTicks() {
        return this.durationTicks;
    }

    /**
     * Sets the duration time of the note in ticks.
     * @param durationTicks the new duration time of the note in ticks
     */
    public void setDurationTicks(long durationTicks) {
        this.durationTicks = durationTicks;
    }

    /*
     * Get the note as follows: (octave, index, velocity, duration).
     */
    public String toString() {
        return "(" + this.octave + ", " + this.noteIndex
        + ", " + this.velocity + ", " + this.durationTicks + ")";
    }

}
