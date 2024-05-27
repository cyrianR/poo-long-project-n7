package poolongprojectn7.toolbarcomponent;

import javafx.geometry.Pos;            // Import tools to align elements of a layout
import javafx.scene.control.*;         // Import JavaFX's user interface components.
import javafx.scene.layout.HBox;       // Import JavaFX's horizontal layout.
import poolongprojectn7.AppModel;
import poolongprojectn7.AppView;

import javax.swing.text.View;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javafx.event.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class ToolBarComponent extends ToolBar {
    private static final String SOUND_METRONOM_NAME = "metronome.wav";
    private ToolBar toolbar;            // Attribute of ToolBar type wich is the tool bar of the application
    private AppView view;
    private AppModel appModel;
    private int bpm;
    private boolean metronomeActivated = false;
    private Clip metronomeSound = null;
    
    @SuppressWarnings("unchecked")
    public ToolBarComponent(AppView view, AppModel appModel) {                 // Creation of the tool bar
        this.toolbar = new ToolBar();                       // Initialisation
        this.view = view;
        this.appModel = appModel;

        // Menu Files with its options
        MenuButton menuFiles = new MenuButton("Files");            // Drop-down menu named "Files"
        ImageView fileIcon = createImageView("File.png");     // Creation of the icon for the Files button.
        menuFiles.setGraphic(fileIcon);                                 // Binding the icon and the Files button.
        menuFiles.getItems().addAll(                                    // Add of the elements to the "Files" menu
            new MenuItem("New project"),
            new MenuItem("Open project"),
            new MenuItem("Save project"),
            new MenuItem("Export project")
        );

        // Track Selecter
        ChoiceBox trackSelecter = new ChoiceBox<String>();            // Drop-down menu named "Tracks"
        for (int i = 1; i <= 10; i++) {                               // Adding all 10 tracks to the menu
            trackSelecter.getItems().add("Track ".concat(Integer.toString(i)));
        }
        // Update selected track on selection
        trackSelecter.setOnAction(e -> {
            int trackSelected = Integer.parseInt(((String) trackSelecter.getValue()).split(" ")[1]);
            this.view.getModel().setCurrentPattern(trackSelected);
        });

        // Buttons to control the playing of the music
        Button play = createButtonWithImage("Play.png", "Start");
        play.setOnAction(handlerPlayButton);
        Button pause = createButtonWithImage("Pause.png", "Pause");
        pause.setOnAction(handlerPauseButton);
        Button stop = createButtonWithImage("Stop.png", "Stop");
        stop.setOnAction(handlerStopButton);

        // Slider for the BPM
        Slider bpmSlider = new Slider(50, 250, 120);  // Initialisation of the slider with the bounds and the default value 120
        bpmSlider.setShowTickLabels(true);       // Dispalying the graduations for each significative value           
        bpmSlider.setShowTickMarks(true);        // Displaying the significative values
        bpmSlider.setMajorTickUnit(50);          // Definition of the main step of thr slider = 50
        bpmSlider.setMinorTickCount(4);          // Definition of the secondary step of thr slider = 4
        bpmSlider.setSnapToTicks(true);          // Postionning the cursor on the nearest graduation of the slider
        bpmSlider.setOnMouseReleased(handlerSlider);    // Binding the slider with the function that manages the changes on it
        this.bpm = (int) bpmSlider.getValue();          // Setting the initial value of the BPM
        Label bpmLabel = new Label("BPM:");       // Naming the slider "BPM"
        HBox bpmControl = new HBox(bpmLabel, bpmSlider);   // Binding the name to the slider in an horizontal layout
        bpmControl.setAlignment(Pos.CENTER_LEFT);         // Ajust je alignment of the name with the slider

        // Timer label
        Label timerLabel = new Label("Timer: ");

        // Displaying the time, initialized to 00:00
        Label timeLabel = new Label("00:00");
        HBox timerBox = new HBox(timerLabel, timeLabel);    // Horizontal layout for the timer
        timerBox.setAlignment(Pos.CENTER_LEFT);             // Alignment of the content of the timer to the left

        // Metronome button with icon
        ToggleButton metronomeToggle = createToggleButtonWithImage("Metronom.png", "Metronome"); // Toggle button for the metronome.
        metronomeToggle.setOnAction(handlerMetronome);

        // Buttons to change the view of the application

        // ToggleGroup for thes ToggleButtons "Overview" and "Composition view" in order to only have one clicked at the time
        ToggleGroup toggleGroup = new ToggleGroup();

        // Button "Overview" of type ToggleButton with an icon, clicked by default
        ToggleButton overviewButton = createToggleButtonWithImage("Overview.png", "Overview"); // Button for the overview.
        overviewButton.setSelected(true);             // Clicked by default
        overviewButton.setOnAction(handlerOverview);        // Function associated to a click on this button
        overviewButton.setToggleGroup(toggleGroup);         // Binding the button with his group

        // Button "Composition view" of type ToggleButton with an icon
        ToggleButton compositionButton = createToggleButtonWithImage("Composition view.png", "Composition view"); // Button for the composition view.
        compositionButton.setOnAction(handlerCompositionView);        // Function associated to a click on this button
        compositionButton.setToggleGroup(toggleGroup);                // Binding the button with his group


        // Adding all the controls to the tool bar
        toolbar.getItems().addAll(menuFiles, trackSelecter, play, pause, stop, bpmControl, timerBox, metronomeToggle, overviewButton, compositionButton);
    }

    // Method to handle actions from the Overview button
    EventHandler<ActionEvent> handlerOverview = event -> {
        this.view.switchToOverview();
    };
    
    // Method to handle actions from the Metronome button
    EventHandler<ActionEvent> handlerMetronome = event -> {
        this.metronomeActivated = !this.metronomeActivated;
        playMetronome(ToolBarComponent.SOUND_METRONOM_NAME);
    };

    // Method to handle actions from the slider
    EventHandler<MouseEvent> handlerSlider = event -> {
        Slider slider = (Slider) event.getSource();
        this.bpm = (int) slider.getValue();
        if (this.metronomeActivated){
            this.metronomeSound.close();
            playMetronome(ToolBarComponent.SOUND_METRONOM_NAME);
        }
    };

    // Method to handle actions from the Composition View button
    EventHandler<ActionEvent> handlerCompositionView = event -> {
        this.view.switchToCompositionView();
    };

    /** Handler for play button. */
    EventHandler<ActionEvent> handlerPlayButton = event -> {
        if (this.appModel.getCurrentView() == AppModel.View.COMPOSITION) {
            view.getPianoRoll().getModel().getPattern().play();
        } else {

        }
    };

    /** Handler for pause button. */
    EventHandler<ActionEvent> handlerPauseButton = event -> {
        if (this.appModel.getCurrentView() == AppModel.View.COMPOSITION) {
            view.getPianoRoll().getModel().getPattern().pause();
        } else {
            
        }
    };

    /** Handler for stop button. */
    EventHandler<ActionEvent> handlerStopButton = event -> {
        if (this.appModel.getCurrentView() == AppModel.View.COMPOSITION) {
            view.getPianoRoll().getModel().getPattern().stop();
        } else {

        }
    };

    // Method to create an ImageView from the name of an image file
    private ImageView createImageView(String imageName) {
        Image image = new Image(getClass().getResourceAsStream("/icons/" + imageName));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20); // Ajustement of the height of the icon.
        imageView.setFitWidth(20); // Ajustement of the width of the icon.
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

    public void playMetronome(String soundName) {
        if (this.metronomeActivated){
            InputStream inputStream = getClass().getResourceAsStream("/sounds/" + soundName);
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
                AudioFormat audioFormat = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);                    Clip audioClip = (Clip) AudioSystem.getLine(info);
                audioClip.open(audioStream);
                this.metronomeSound = audioClip;
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            double timeOfAFrame = 1/this.metronomeSound.getFormat().getFrameRate();
            this.metronomeSound.setLoopPoints(this.metronomeSound.getFrameLength() - (int) Math.round(((double) 60 / this.bpm)/timeOfAFrame) - 1, this.metronomeSound.getFrameLength() - 1);
            this.metronomeSound.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            this.metronomeSound.close();
        }
    }

    public ToolBar getToolbar() {
        return toolbar;
    }
}

