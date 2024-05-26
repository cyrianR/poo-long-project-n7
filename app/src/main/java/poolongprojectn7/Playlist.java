package poolongprojectn7;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents a playlist to the place to arrange and
 * edit Audio to create the song.
 * @version Alpha
 */
public class Playlist {

    /* Number of threads available */
    private final int NB_THREADS = 10;

    /* The sounds of the playlist. */
    private Map<SoundEntry, List<Integer>> soundMap;
    /* The scheduler of the playlist. */
    private ScheduledExecutorService scheduler;

    /**
     * Create a new playlist.
     */
    public Playlist() {
        this.soundMap = new HashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(NB_THREADS);
    }

    /**
     * Add a wav sound to the playlist.
     * @param sound the sound to add
     * @param delay the time in milliseconds the sound begins to be played in the playlist
     */
    public void addSound(SoundEntry sound, int delay) {
        soundMap.computeIfAbsent(sound, k -> new ArrayList<>()).add(delay);
    }

    /**
     * Play all the sounds of the playlist.
     */
    public void playAll() {
        for (Map.Entry<SoundEntry, List<Integer>> entry : soundMap.entrySet()) {
            SoundEntry sound = entry.getKey();
            for (int delay : entry.getValue()) {
                this.scheduler.schedule(() -> sound.play(), delay, TimeUnit.SECONDS);
            }
        }
        /* Schedule the shutdown of the scheduler with the adjusted total duration according to the BPM */
        this.scheduler.schedule(() -> scheduler.shutdown(), getTotalDuration(), TimeUnit.SECONDS);
    }

    /**
     * Get the time duration of the playlist.
     * @return the duration of the playlist
     */
    private long getTotalDuration() {
        return soundMap.values().stream()
            .flatMap(List::stream)
            .mapToInt(Integer::intValue)
            .max()
            .orElse(0);
    }

}
