package poolongprojectn7;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JOptionPane;

class PlaylistTest {
    @Test
    public void testReadAndWrite() {
        Playlist playlist = new Playlist();
        SoundEntry musique = new SoundEntry("/home/vic_pabo/Downloads/cee.wav");
        musique.setVolume(40);
        playlist.addSound(musique, 0);
        SoundEntry bruh = new SoundEntry("/home/vic_pabo/Downloads/bruh.wav");
        playlist.addSound(bruh, 5000);
        
        playlist.playAll();

        JOptionPane.showMessageDialog(null, "Clique pour arrêter");
    }

}
