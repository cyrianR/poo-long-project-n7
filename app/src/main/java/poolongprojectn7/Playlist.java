package poolongprojectn7;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents a playlist to the place to arrange and
 * edit Audio to create the song.
 * @version Alpha
 */
public class Playlist {

    /* The sounds of the playlist. */
    private Map<SoundEntry, Integer> soundMap;
    /* The scheduler of the playlist. */
    private ScheduledExecutorService scheduler;

    /**
     * Create a new playlist.
     */
    public Playlist() {
        this.soundMap = new HashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    /**
     * Add a wav sound to the playlist.
     * @param sound the sound to add
     * @param delay the time the sound begins to be played in the playlist
     */
    public void addSound(SoundEntry sound, int delay) {
        this.soundMap.put(sound, delay);
    }

    /**
     * Play all the sounds of the playlist.
     */
    public void playAll() {
        for (Map.Entry<SoundEntry, Integer> entry : soundMap.entrySet()) {
            scheduler.schedule(entry.getKey()::play, entry.getValue(), TimeUnit.MILLISECONDS);
        }
        scheduler.schedule(() -> scheduler.shutdown(), getTotalDuration(), TimeUnit.MILLISECONDS);
    }

    /**
     * Get the time duration of the playlist.
     * @return the duration of the playlist
     */
    private long getTotalDuration() {
        return soundMap.values().stream().mapToInt(Integer::intValue).sum();
    }

}
