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
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class Track extends VBox {
    private final int SPACING = 5; // Spacing between the elements
    private final static List<Color> COLORS = Arrays.asList(Color.LIGHTBLUE, Color.LIGHTGREEN, Color.LIGHTYELLOW, Color.LIGHTPINK, Color.LIGHTCYAN, Color.LIGHTCORAL, Color.LIGHTSEAGREEN, Color.LIGHTSALMON, Color.LIGHTSKYBLUE, Color.LAVENDER); // Colors of track available
    private String name; // Name of the track
    private Color color; // Color of the track
    private boolean isMuted = false; // Is this track muted?
    private Node trackName; // Node containing the name of the track
    private HBox identification; // Box containing the identification of the track (name + color)
    private VBox parameters; // Box containing the parameters of the track
    private HBox patternsBox; // Box containing the patterns of the track

    // Clipboard data format for patterns
    private static final DataFormat PATTERN_FORMAT = new DataFormat("pattern");

    // List of patterns on the track
    private List<Pattern> patterns = new ArrayList<>();

    // Creation of the track
    public Track(int number) {
        super();

        this.name = "Track " + String.valueOf(number + 1);
        this.color = Track.COLORS.get(number % Track.COLORS.size());

        MenuButton menuColor = new MenuButton();
        ImageView colorIcon = createImageView("Color.png");
        menuColor.setGraphic(colorIcon);
        List<MenuItem> items = new ArrayList<>();
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

        // Box for patterns
        this.patternsBox = new HBox();
        this.patternsBox.setSpacing(SPACING);
        this.patternsBox.setAlignment(Pos.CENTER_LEFT);
        createPatternCells();

        this.getChildren().addAll(this.parameters, this.patternsBox);
        this.setBackground(new Background(new BackgroundFill(this.color, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setSpacing(SPACING);
    }

    // Method to create empty cells for patterns
    private void createPatternCells() {
        for (int i = 0; i < 16; i++) { // Assuming 16 cells per track for simplicity
            Pane cell = new Pane();
            cell.setPrefSize(40, 40);
            cell.setStyle("-fx-border-color: black; -fx-background-color: white;");
            cell.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    createPattern(cell);
                }
            });
            cell.setOnContextMenuRequested(event -> showContextMenu(cell, event.getScreenX(), event.getScreenY()));
            setupDragAndDrop(cell);
            patternsBox.getChildren().add(cell);
        }
    }

    // Method to create a pattern in a cell
    private void createPattern(Pane cell) {
        Pattern pattern = new Pattern();
        patterns.add(pattern);
        cell.setStyle("-fx-background-color: grey;");
        cell.setUserData(pattern);
        setupPatternContextMenu(cell);
    }

    // Method to setup context menu for a pattern
    private void setupPatternContextMenu(Pane cell) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem copyItem = new MenuItem("Copy");
        copyItem.setOnAction(event -> copyPattern(cell));

        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.setOnAction(event -> pastePattern(cell));

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> deletePattern(cell));

        contextMenu.getItems().addAll(copyItem, pasteItem, deleteItem);
        cell.setOnContextMenuRequested(event -> contextMenu.show(cell, event.getScreenX(), event.getScreenY()));
    }

    // Method to show context menu
    private void showContextMenu(Pane cell, double screenX, double screenY) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem copyItem = new MenuItem("Copy");
        copyItem.setOnAction(event -> copyPattern(cell));

        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.setOnAction(event -> pastePattern(cell));

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> deletePattern(cell));

        contextMenu.getItems().addAll(copyItem, pasteItem, deleteItem);
        contextMenu.show(cell, screenX, screenY);
    }

    // Method to copy a pattern
    private void copyPattern(Pane cell) {
        Pattern pattern = (Pattern) cell.getUserData();
        ClipboardContent content = new ClipboardContent();
        content.put(PATTERN_FORMAT, pattern);
        Clipboard.getSystemClipboard().setContent(content);
    }

    // Method to paste a pattern
    private void pastePattern(Pane cell) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasContent(PATTERN_FORMAT)) {
            Pattern pattern = (Pattern) clipboard.getContent(PATTERN_FORMAT);
            cell.setUserData(pattern);
            cell.setStyle("-fx-background-color: grey;");
        }
    }

    // Method to delete a pattern
    private void deletePattern(Pane cell) {
        patterns.remove(cell.getUserData());
        cell.setUserData(null);
        cell.setStyle("-fx-background-color: white;");
    }

    // Method to setup drag and drop for patterns
    private void setupDragAndDrop(Pane cell) {
        cell.setOnDragDetected(event -> {
            if (cell.getUserData() != null) {
                Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.put(PATTERN_FORMAT, cell.getUserData());
                db.setContent(content);
                event.consume();
            }
        });

        cell.setOnDragOver(event -> {
            if (event.getGestureSource() != cell && event.getDragboard().hasContent(PATTERN_FORMAT)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        cell.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasContent(PATTERN_FORMAT)) {
                Pattern pattern = (Pattern) db.getContent(PATTERN_FORMAT);
                Pane sourceCell = (Pane) event.getGestureSource();
                sourceCell.setUserData(null);
                sourceCell.setStyle("-fx-background-color: white;");
                cell.setUserData(pattern);
                cell.setStyle("-fx-background-color: grey;");
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
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
        button.setGraphic(createImageView(imageName)); // Binding the icon to the button.
        button.setTooltip(new Tooltip(tooltipText)); // Binding the tooltip to the button.
        return button;
    }

    // Method to create a toggle button with an icon and a tooltip.
    private ToggleButton createToggleButtonWithImage(String imageName, String tooltipText) {
        ToggleButton toggleButton = new ToggleButton();
        toggleButton.setGraphic(createImageView(imageName)); // Binding the icon to the toggle button.
        toggleButton.setTooltip(new Tooltip(tooltipText)); // Binding the tooltip to the toggle button.
        return toggleButton;
    }

    // Method to set the hyperlink of the name of the track
    private void createHyperlinkName() {
        this.trackName = new Hyperlink(this.name);
        ((Hyperlink) this.trackName).setTextFill(Color.BLACK);
        ((Hyperlink) this.trackName).setOnAction(handlerName);
    }
}
