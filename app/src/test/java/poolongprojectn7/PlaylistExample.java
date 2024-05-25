package poolongprojectn7;

import javax.swing.JOptionPane;

class PlaylistTest {

    /* The file path where the temporary MIDI file will be stored */
    private static String filePath = System.getProperty("user.dir") + "/src/test/testSound";
    
    public void examplePlaylist() {
        Playlist playlist = new Playlist();
        SoundEntry musique = new SoundEntry(filePath + "music.wav");
        musique.setVolume(40);
        playlist.addSound(musique, 0);
        SoundEntry soundToAdd = new SoundEntry(filePath + "soundToAdd.wav");
        playlist.addSound(soundToAdd, 5000);
        
        playlist.playAll();

        JOptionPane.showMessageDialog(null, "Clique pour arrêter");
    }

}
