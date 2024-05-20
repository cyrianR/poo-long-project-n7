package poolongprojectn7;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class SoundEntry {

    /* The default volume of a wav sound */
    private final int DEFAULT_VOLUME = 100;

    /* The path of the wav sound. */
    private String filePath;
    /* 
     * The volume of the wav sound (0-100).
     * 0 is equivalent to a muted sound, 100 to the original sound.
     */
    private int volume;
    /* The audio clip to be played */
    private Clip audioClip;

    /**
     * Create a new sound entry from a given .wav file path and delay.
     * @param filePath the file path
     */
    public SoundEntry(String filePath) {
        this.filePath = filePath;
        this.volume = DEFAULT_VOLUME;
    }

    /**
     * Obtains the file path of the sound.
     * @return the file path of the sound
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Change the volume of the sound.
     * @param volume the new volume of the sound
     */
    public void setVolume(int volume) {
        if ((volume < 0) || (volume > DEFAULT_VOLUME)) {
            throw new IllegalArgumentException("Volume must be between 0 and 100");
        }
        this.volume = volume;
        if (audioClip != null) {
            adjustVolume();
        }
    }

    /**
     * Obtains the volume of the sound.
     * @return the volume of the sound
     */
    public int getVolume() {
        return this.volume;
    }

    /**
     * Adjusts the volume of the audio clip.
     */
    private void adjustVolume() {
        FloatControl volumeControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(20.0f * (float) Math.log10( volume / (float) DEFAULT_VOLUME ));
    }

    /**
     * Play the sound.
     */
    public void play() {
        try {
            File audioFile = new File(this.filePath);
            AudioInputStream stream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(stream);
            adjustVolume();
            audioClip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
