package poolongprojectn7;

public class Note {

    // Integer from 0 to 10
    private int octave;
    // Integer from 0 to 11
    private int noteIndex;
    // Integer from 0 to 127
    private int midiNoteNumber;
    // Integer from 0 to 127
    private int velocity;
    private long durationTicks;
    /* For potential futur amelioration. */
    @SuppressWarnings("unused")
    private int noteOffVelocity;

    public Note(int octave, int noteIndex, long durationTicks) {
        this.octave = octave;
        this.noteIndex = noteIndex;
        this.durationTicks = durationTicks;
        setMidiNoteNumber(octave, noteIndex);
    }

    public int getOctave() {
        return this.octave;
    }

    public void setOctave(int octave) {
        this.octave = octave;
        setMidiNoteNumber(this.octave, this.noteIndex);
    }

    public int getNoteIndex() {
        return this.noteIndex;
    }

    public void setNoteIndex(int noteIndex) {
        this.noteIndex = noteIndex;
        setMidiNoteNumber(this.octave, this.noteIndex);
    }

    public int getMidiNoteNumber() {
        return this.midiNoteNumber;
    }

    private void setMidiNoteNumber(int octave, int noteIndex) {
        this.midiNoteNumber = octave * 12 + noteIndex;
    }

    public int getVelocity() {
        return this.velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public long getDurationTicks() {
        return this.durationTicks;
    }

    public void setDurationTicks(long durationTicks) {
        this.durationTicks = durationTicks;
    }

}
