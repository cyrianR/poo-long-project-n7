package poolongprojectn7.toolbarcomponent;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import poolongprojectn7.AppController;

public class ToolBarComponent extends ToolBar {
    private ToolBar toolbar; // Attribute of ToolBar type which is the tool bar of the application

    public ToolBarComponent(AppController appController) { // Creation of the tool bar
        this.toolbar = new ToolBar(); // Initialisation


        // Menu Files with its options
        MenuButton menuFiles = new MenuButton("Files"); // Drop-down menu named "Files"
        ImageView fileIcon = createImageView("File.png"); // Creation of the icon for the Files button.
        menuFiles.setGraphic(fileIcon); // Binding the icon and the Files button.
        menuFiles.getItems().addAll( // Add of the elements to the "Files" menu
            new MenuItem("New project"),
            new MenuItem("Open project"),
            new MenuItem("Save project"),
            new MenuItem("Export project")
        );

        // Buttons to control the playing of the music
        Button play = createButtonWithImage("Play.png", "Start");
        Button pause = createButtonWithImage("Pause.png", "Pause");
        Button stop = createButtonWithImage("Stop.png", "Stop");

        // Slider for the BPM
        Slider bpmSlider = new Slider(50, 250, 120); // Initialisation of the slider with the bounds and the default value 120
        bpmSlider.setShowTickLabels(true); // Displaying the graduations for each significative value
        bpmSlider.setShowTickMarks(true); // Displaying the significative values
        bpmSlider.setMajorTickUnit(50); // Definition of the main step of thr slider = 50
        bpmSlider.setMinorTickCount(4); // Definition of the secondary step of thr slider = 4
        bpmSlider.setSnapToTicks(true); // Postionning the cursor on the nearest graduation of the slider
        Label bpmLabel = new Label("BPM:"); // Naming the slider "BPM"
        HBox bpmControl = new HBox(bpmLabel, bpmSlider); // Binding the name to the slider in an horizontal layout
        bpmControl.setAlignment(Pos.CENTER_LEFT); // Ajust the alignment of the name with the slider

        // Timer label
        Label timerLabel = new Label("Timer: ");

        // Displaying the time, initialized to 00:00
        Label timeLabel = new Label("00:00");
        HBox timerBox = new HBox(timerLabel, timeLabel); // Horizontal layout for the timer
        timerBox.setAlignment(Pos.CENTER_LEFT); // Alignment of the content of the timer to the left

        // Metronome button with icon
        ToggleButton metronomeToggle = createToggleButtonWithImage("Metronom.png", "Metronome"); // Toggle button for the metronom.

        // Buttons to change the view of the application

        // ToggleGroup for the ToggleButtons "Overview" and "Composition view" in order to only have one clicked at the time
        ToggleGroup toggleGroup = new ToggleGroup();

        // Button "Overview" of type ToggleButton with an icon, clicked by default
        ToggleButton overviewButton = createToggleButtonWithImage("Overview.png", "Overview"); // Button for the overview.
        overviewButton.setSelected(true); // Clicked by default
        overviewButton.setOnAction(event -> appController.switchToOverview()); // Function associated to a click on this button
        overviewButton.setToggleGroup(toggleGroup); // Binding the button with his group

        // Button "Composition view" of type ToggleButton with an icon
        ToggleButton compositionButton = createToggleButtonWithImage("Composition view.png", "Composition view"); // Button for the composition view.
        compositionButton.setOnAction(event -> appController.switchToCompositionView()); // Function associated to a click on this button
        compositionButton.setToggleGroup(toggleGroup); // Binding the button with his group

        // Adding all the controls to the tool bar
        toolbar.getItems().addAll(menuFiles, play, pause, stop, bpmControl, timerBox, metronomeToggle, overviewButton, compositionButton);
    }

    // Method to create an ImageView from the name of an image file
    private ImageView createImageView(String imageName) {
        Image image = new Image(getClass().getResourceAsStream("/icons/" + imageName));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20); // Adjust the height of the icon.
        imageView.setFitWidth(20); // Adjust the width of the icon.
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

    public ToolBar getToolbar() {
        return toolbar;
    }
}
