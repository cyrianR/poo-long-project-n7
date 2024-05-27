package poolongprojectn7.pianoroll;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import poolongprojectn7.InvalidNoteException;
import poolongprojectn7.Note;
import poolongprojectn7.Pattern;
import javafx.event.*;
import javax.sound.midi.*;

public class PianoRollController extends ScrollPane{

    protected final Background WHITE_BACKGROUD = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
    protected final Background BLACK_BACKGROUD = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
    protected final Background GREEN_BACKGROUD = new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
    protected final Background GRAY_BACKGROUD = new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY));
    protected final Background TRANSPARENT_BACKGROUD = new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY));
    protected final Border BLACK_BORDER = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    protected final Border WHITE_BORDER = new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    protected final Border GRAY_BORDER = new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    private final String[] NOTE_LETTERS = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};

    private PianoRollModel model;
    private PianoRollView view;
    private StackPane partition;
    private final GridPane buttonPartition = new GridPane();


    /**
     * Create a controller
     * @param model The model of the PianoRoll
     * @param view The view of the PianoRoll
     */
    public PianoRollController(PianoRollModel model, PianoRollView view){
        this.model = model;
        this.view = view;
        partition = new StackPane(view,buttonPartition);
        Group root = new Group();
        for(int i = 0; i < model.MAX_NOTE_INDEX + 1; i++){
            root.getChildren().add(newButton(50, 30 * i, NOTE_LETTERS[i]));
        }
        StackPane piano = new StackPane(root);
        HBox octaves = new HBox(new Separator(Orientation.VERTICAL));
        for(int i = 0; i < model.MAX_NOTE_INDEX + 1; i++){
            for(int j = 0; j < model.PIANO_LENGTH; j++){
                Button square = new Button();
                square.setPrefSize(30, 30);
                Border border = i%2 == 0 ? GRAY_BORDER : WHITE_BORDER;
                square.setBackground(TRANSPARENT_BACKGROUD);
                square.setBorder(border);
                square.setOnAction(handlerPartition);
                buttonPartition.add(square, j, i);
                if (j%4==0 && i == 0){
                    Pane panel = new Pane();
                    Text number = new Text("" + (j/4 + 1));
                    number.setFont(new Font(14));
                    number.setWrappingWidth(114);
                    number.setTextAlignment(TextAlignment.CENTER);
                    panel.getChildren().add(number);
                    panel.setPrefSize(120, 30);
                    octaves.getChildren().add(number);               
                    octaves.getChildren().add(new Separator(Orientation.VERTICAL));
                }
            }
        }
        HBox octaveHBox = new HBox(newRoundButton("-"), new Text("Octave"), newRoundButton("+"));
        octaveHBox.prefHeight(50);
        octaveHBox.prefWidth(120);
        partition.setLayoutX(1);
        partition.setLayoutY(30);   
        Group partitionEtOctaves = new Group(octaves, partition);
        VBox pianoAndOctave = new VBox(octaveHBox, piano);
        HBox hbox = new HBox(pianoAndOctave, new Separator(Orientation.VERTICAL), partitionEtOctaves);
        this.setMaxWidth(ScrollPane.USE_PREF_SIZE);
        this.setContent(hbox);
    }

    /**
     * Create a new squarre button displaying a text at a disired position.
     * @param x the x coordinate of the button
     * @param y the y coordinate of the button
     * @param text the text we want to display
     * @return the button
     */
    private Button newButton(double x, double y, String text){
        Button button = new Button();
        button.setText(text);
        if (text.length() > 1) {
            button.setTextFill(Color.WHITE);
            button.setBackground(BLACK_BACKGROUD);
            button.setBorder(WHITE_BORDER);
        } else {
            button.setTextFill(Color.BLACK);
            button.setBackground(WHITE_BACKGROUD);
            button.setBorder(BLACK_BORDER);
        }
        button.setAlignment(Pos.BASELINE_RIGHT);
        button.setOnAction(handlerNotes);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefSize(120, 30);
        return button;
    }

    /**
     * Create a new round button
     * @param text le text to be displayed in the button
     * @return the button
     */
    private Button newRoundButton(String text){
        Button button = new Button();
        button.setShape(new Circle(30));
        button.setText(text);
        button.setTextFill(Color.BLACK);
        button.setBackground(WHITE_BACKGROUD);
        button.setBorder(BLACK_BORDER);
        button.setOnAction(handlerOctave);
        return button;
    }


    /**
     * The handler that play the note that has been intercated with.
     */
    EventHandler<ActionEvent> handlerNotes = event -> {
        Button source = (Button) event.getSource();
        String noteName = source.getText();
        int noteIndex = java.util.Arrays.asList(NOTE_LETTERS).indexOf(noteName);

        Pattern pattern = new Pattern();
        Note note = new Note(model.getOctave(), noteIndex, 100, 20);
        try {
            pattern.addNote(note, 0);
            pattern.setInstrument(this.model.getPattern().getInstrument());
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(pattern.getSequence());
            sequencer.start();
            while (sequencer.isRunning()) {
                Thread.sleep(300);
            }
            sequencer.close();
        } catch (MidiUnavailableException | InvalidMidiDataException | InterruptedException | InvalidNoteException e) {
            e.printStackTrace();
        }
    };

    /**
     * The handler that increase or decrease the octave selected.
     */
    EventHandler<ActionEvent> handlerOctave = event -> {
        Button source = (Button) event.getSource();
        if (source.getText().equals("-")) {
            model.setOctave(model.getOctave() - 1);
        } else {
            model.setOctave(model.getOctave() + 1);
        }
        view.updateAll();
    };

    /**
     * The handler that update the model and the view of the PianoRoll.
     */
    EventHandler<ActionEvent> handlerPartition = event -> {
        Button source = (Button) event.getSource();
        Integer row = GridPane.getRowIndex(source);
        int r = row == null ? 0 : row;
        Integer column = GridPane.getColumnIndex(source);
        int c = column == null ? 0 : column;
        model.changeNoteState(model.getMidiNoteNumberFromRow(r), c);
        view.update(r,c); 
    };
}
