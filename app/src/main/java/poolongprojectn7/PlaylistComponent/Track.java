package poolongprojectn7.PlaylistComponent;

import java.util.List;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Track extends HBox {
    private final int SPACING = 5;          // Spacing between the elements
    private String name;                    // Name of the track
    private Color color;                    // Color of the track
    private List<Color> colors;             // Available colors
    private boolean isMuted = false;        // Is this track muted ?
    private GridPane colorsGridPane = null; // Color pane
    private boolean isColorsPaneOn = false; // Is the color pane displayed
    private Node trackName;                 // Node containing the name of the track
    private HBox identification;            // Box containing the identification of the track (name + color)
    private VBox parameters;                // Box containing the parameters of the track

    // Creation of the track
    public Track(List<Color> c, int number) {
        super();
        this.name = "Track " + String.valueOf(number + 1);
        this.colors = c;
        this.color = this.colors.get(number % 2);
        
        Button changeColor = createButtonWithImage("Color.png", "Change color");
        changeColor.setOnAction(handlerColor);
        createHyperlinkName();
        this.identification = new HBox(changeColor, this.trackName);
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
        this.setBackground(((Button) event.getSource()).getBackground());
        this.getChildren().remove(this.colorsGridPane);
        this.isColorsPaneOn = !this.isColorsPaneOn;
    };
    
    // Method to handle actions on Set Color button
    EventHandler<ActionEvent> handlerColor = event -> {
        if (this.colorsGridPane == null) {
            this.colorsGridPane = new GridPane();
            for (int i = 0; i < colors.size(); i++) {
                int row = ((float) (i + 1) / (float) colors.size()) <= 0.5 ? 0 : 1;
                int column = i - row * (int) Math.ceil(colors.size()/2);
                Button squareColor = new Button();
                squareColor.setBackground(new Background(new BackgroundFill(colors.get(i), CornerRadii.EMPTY, Insets.EMPTY)));
                squareColor.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                squareColor.setOnAction(handlerColorButton);
                this.colorsGridPane.add(squareColor, column, row);
            }
            this.colorsGridPane.setHgap(5);
            this.colorsGridPane.setVgap(5);
        }
        if (this.isColorsPaneOn) {
            this.getChildren().remove(this.colorsGridPane);
        } else {
            this.getChildren().add(this.colorsGridPane);
        }
        this.isColorsPaneOn = !this.isColorsPaneOn;
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