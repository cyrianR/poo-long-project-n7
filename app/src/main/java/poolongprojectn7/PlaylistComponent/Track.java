package poolongprojectn7.PlaylistComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Track extends HBox {
    private final int SPACING = 5;          // Spacing between the elements
    private final static List<Color> COLORS = Arrays.asList(Color.LIGHTBLUE, Color.LIGHTGREEN, Color.LIGHTYELLOW, Color.LIGHTPINK, Color.LIGHTCYAN, Color.LIGHTCORAL, Color.LIGHTSEAGREEN, Color.LIGHTSALMON, Color.LIGHTSKYBLUE, Color.LAVENDER); // Colors of track available
    private String name;                    // Name of the track
    private Color color;                    // Color of the track
    private boolean isMuted = false;        // Is this track muted ?
    private Node trackName;                 // Node containing the name of the track
    private HBox identification;            // Box containing the identification of the track (name + color)
    private VBox parameters;                // Box containing the parameters of the track

    // Creation of the track
    public Track(int number) {
        super();

        this.name = "Track " + String.valueOf(number + 1);
        this.color = Track.COLORS.get(number % 2);

        MenuButton menuColor = new MenuButton();
        ImageView colorIcon = createImageView("Color.png");
        menuColor.setGraphic(colorIcon);
        List<MenuItem> items = new ArrayList<MenuItem>();
        for (int i = 0; i < Track.COLORS.size(); i++) {
            MenuItem item = new MenuItem("               ");
            int r = ((int) Math.round(Track.COLORS.get(i).getRed() * 255));
            int g = ((int) Math.round(Track.COLORS.get(i).getGreen() * 255));
            int b = ((int) Math.round(Track.COLORS.get(i).getBlue() * 255));
            item.setStyle(String.format("-fx-background-color: rgb(%d,%d,%d)", r, g, b));
            item.setOnAction(handlerColorButton);
            items.add(item);
        }
        items.forEach(menuColor.getItems()::add);
        createHyperlinkName();
        this.identification = new HBox(menuColor, this.trackName);
        this.identification.setSpacing(SPACING);
        this.identification.setAlignment(Pos.CENTER_LEFT);
        Button playSolo = createButtonWithImage("Play.png", "Start");
        ToggleButton muteButton = createToggleButtonWithImage("Mute.png", "Mute");
        muteButton.setOnAction(handlerMute);
        HBox actions = new HBox(muteButton, playSolo);
        actions.setSpacing(SPACING);
        this.parameters = new VBox(this.identification, actions);
        this.parameters.setSpacing(SPACING);

        this.getChildren().add(this.parameters);
        this.setBackground(new Background(new BackgroundFill(this.color, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setPadding(new Insets(5,5,5,5));
        this.setSpacing(SPACING);
    }

    // Method to know if the track is muted or not
    public boolean isTrackMuted() {
        return isMuted;
    }

    // Method to handle a modification of the name of the track
    EventHandler<ActionEvent> handlerModifiedName = event -> {
        this.identification.getChildren().remove(this.trackName);
        this.name = ((TextField) event.getSource()).getText();
        createHyperlinkName();
        this.identification.getChildren().add(this.trackName);
    };

    // Method to handle actions on track name
    EventHandler<ActionEvent> handlerName = event -> {
        this.identification.getChildren().remove(this.trackName);
        this.trackName = new TextField(this.name);
        ((TextField) this.trackName).setOnAction(handlerModifiedName);
        this.identification.getChildren().add(this.trackName);
    };

    // Method to handle actions on Mute button
    EventHandler<ActionEvent> handlerMute = event -> {
        this.isMuted = !this.isMuted;
    };

    // Method to handle actions on Color button
    EventHandler<ActionEvent> handlerColorButton = event -> {
        String styleItem = ((MenuItem) event.getSource()).getStyle();
        String[] colors = styleItem.split("[()]")[1].split(",");
        int red = Integer.valueOf(colors[0]);
        int green = Integer.valueOf(colors[1]);
        int blue = Integer.valueOf(colors[2]);
        this.setBackground(new Background(new BackgroundFill(Color.rgb(red, green, blue, 1), CornerRadii.EMPTY, Insets.EMPTY)));
    };

    // Method to create an ImageView from the name of an image file
    private ImageView createImageView(String imageName) {
        Image image = new Image(getClass().getResourceAsStream("/icons/" + imageName));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(12); // Ajustement of the height of the icon.
        imageView.setFitWidth(12); // Ajustement of the width of the icon.
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

    // Method to set the hyperlink of the name of the track
    private void createHyperlinkName() {
        this.trackName = new Hyperlink(this.name);
        ((Hyperlink) this.trackName).setTextFill(Color.BLACK);
        ((Hyperlink) this.trackName).setOnAction(handlerName);
    }
}