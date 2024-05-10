package poolongprojectn7.PlaylistComponent;

import java.util.List;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Track extends HBox {
    private final int SPACING = 5;      // Spacing between the elements
    private String name;                // Name of the track
    private Color color;                // Color of the track
    private List<Color> colors;         // Available colors
    private boolean isMuted = false;    // Is this track muted ?

    // Creation of the track
    public Track(List<Color> c, int number) {
        super();
        this.name = "Track " + String.valueOf(number + 1);
        this.colors = c;
        this.color = this.colors.get(number % 2);
        
        Button playSolo = createButtonWithImage("Play.png", "Start");
        ToggleButton muteButton = createToggleButtonWithImage("Mute.png", "Mute");
        muteButton.setOnAction(handlerMute);
        HBox actions = new HBox(muteButton, playSolo);
        actions.setSpacing(SPACING);
        VBox parameters = new VBox(new Text(this.name), actions);
        parameters.setSpacing(SPACING);

        this.getChildren().add(parameters);
        this.setBackground(new Background(new BackgroundFill(this.color, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setPadding(new Insets(5,5,5,5));
    }

    // Method to actions on Mute button
    EventHandler<ActionEvent> handlerMute = event -> {
        this.isMuted = !this.isMuted;
    };

    // Method to create an ImageView from the name of an image file
    private ImageView createImageView(String imageName) {
        Image image = new Image(getClass().getResourceAsStream("/icons/" + imageName));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(15); // Ajustement of the height of the icon.
        imageView.setFitWidth(15); // Ajustement of the width of the icon.
        return imageView;
    }

    // Method to create a button with an icon and a tooltip.
    private Button createButtonWithImage(String imageName, String tooltipText) {
        Button button = new Button();
        button.setGraphic(createImageView(imageName));  // Binding the icon to the button.
        button.setTooltip(new Tooltip(tooltipText));    // Binding the tooltip to the button.
        return button;
    }

    // Method to create a toggle button with an icon and a tooltip.
    private ToggleButton createToggleButtonWithImage(String imageName, String tooltipText) {
        ToggleButton toggleButton = new ToggleButton();
        toggleButton.setGraphic(createImageView(imageName));    // Binding the icon to the toggle button.
        toggleButton.setTooltip(new Tooltip(tooltipText));      // Binding the tooltip to the toggle button.
        return toggleButton;
    }
}