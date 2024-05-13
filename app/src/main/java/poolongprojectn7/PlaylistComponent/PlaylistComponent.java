package poolongprojectn7.PlaylistComponent;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PlaylistComponent extends ScrollPane {

    private final int NB_TRACKS = 10;                           // Number of tracks in the playlist
    private ArrayList<Track> tracks = new ArrayList<Track>();   // List of the tracks of the playlist

    public PlaylistComponent() {
        super();

        VBox content = new VBox();

        // Creation of the tracks of the playlist
        for (int i = 0; i < NB_TRACKS; i++) {
            tracks.add(new Track(i));
        }

        content.getChildren().add(new Text("Playlist"));
        tracks.forEach(content.getChildren()::add);
        content.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        content.setPadding(new Insets(5,5,5,5));

        this.setContent(content); 
    }
    
}
