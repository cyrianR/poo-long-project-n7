package poolongprojectn7.pianoroll;

import javafx.geometry.*;

import java.util.*;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import poolongprojectn7.Pattern;
import javafx.event.*;

public class PianoRoll extends HBox{
    private final Background WHITE_BACKGROUD = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background BLACK_BACKGROUD = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background GREEN_BACKGROUD = new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background GRAY_BACKGROUD = new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY));
    private final Border BLACK_BORDER = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    private final Border WHITE_BORDER = new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    private final Border GRAY_BORDER = new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    private final static String[] ids = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
    private static final Map<String,Integer> notes = new HashMap<String,Integer>();
    private Integer octave = 4;
    private GridPane partition = new GridPane();
    private Pattern pattern;

    public PianoRoll(Pattern pattern) {
        this.pattern = pattern;
        Group root = new Group();
        for(int i = 0; i < 12; i++){
            notes.put(ids[i],i);
            root.getChildren().add(newButton(50, 30 * i, ids[i]));
        }
        StackPane piano = new StackPane(root);
        HBox octaves = new HBox();
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 50; j++){
                Button square = new Button();
                square.setPrefSize(30, 30);
                Background bg = i%2 == 0 ? WHITE_BACKGROUD : GRAY_BACKGROUD;
                Border border = i%2 == 0 ? GRAY_BORDER : WHITE_BORDER;
                square.setBackground(bg);
                square.setBorder(border);
                square.setOnAction(handlerPartition);
                partition.add(square, j, i);
            }
            if (i%4==0){
                Pane number = new Pane();
                number.getChildren().add(new TextField("" + i));
                number.setPrefSize(120, 30);
                octaves.getChildren().add(number);
                octaves.getChildren().add(new Separator(Orientation.VERTICAL));                
            }
        }
        HBox octaveHBox = new HBox(newRoundButton("-"), new Text("Octave"), newRoundButton("+"));
        octaveHBox.prefHeight(50);
        octaveHBox.prefWidth(120);
        VBox pianoAndOctave = new VBox(octaveHBox, piano);
        VBox partitionEtOctaves = new VBox(octaves, partition);
        HBox hbox = new HBox(pianoAndOctave, new Separator(Orientation.VERTICAL), partitionEtOctaves);
        this.getChildren().add(hbox);
    }

    private Button newButton(double x, double y, String text){
        Button button = new Button();
        button.setText(text);
        if(text.length() > 1){
            button.setTextFill(Color.WHITE);
            button.setBackground(BLACK_BACKGROUD);
            button.setBorder(WHITE_BORDER);
        }else{
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

    EventHandler<ActionEvent> handlerNotes = event -> {
        Button source = (Button) event.getSource();
        System.out.println(source.getText());
    };

    EventHandler<ActionEvent> handlerOctave = event -> {
        Button source = (Button) event.getSource();
        if(source.getText().equals("-")){
            octave--;
        }else{
            octave++;
        }
        System.out.println(octave);
    };

    EventHandler<ActionEvent> handlerPartition = event -> {
        Button source = (Button) event.getSource();
        Integer row = GridPane.getRowIndex(source);
        int r = row == null ? 0 : row;
        if (source.getBackground() == GREEN_BACKGROUD){
            Background bg = r%2 == 0 ? WHITE_BACKGROUD : GRAY_BACKGROUD;
            source.setBackground(bg);
        }else{
            source.setBackground(GREEN_BACKGROUD);
        }

    };
}
