package poolongprojectn7.pianoroll;

import javafx.geometry.*;

import java.util.*;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.event.*;

public class PianoRoll extends HBox {
    private final Background WHITE_BACKGROUD = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background BLACK_BACKGROUD = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background GREEN_BACKGROUD = new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
    private final Border BLACK_BORDER = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    private final Border WHITE_BORDER = new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    private final static String[] ids = {"do","DoDiese","re","reDiese","mi","fa","faDiese","sol","solDiese","la","laDiese","si"};
    private static final Map<String,Integer> notes = new HashMap<String,Integer>();
    private Integer current = 0;
    private GridPane partition = new GridPane();
    
    public PianoRoll() {
        Button exitButton = new Button("Quitter");
        exitButton.setOnAction(exit);
        Group root = new Group();
        for(int i = 0; i < 12; i++){
            notes.put(ids[i],i);
            root.getChildren().add(newButton(50, 30 * i, ids[i]));
        }/*
        root.getChildren().add(newBlackButton(50, 65, blackIds[0]));
        root.getChildren().add(newBlackButton(50, 95, blackIds[1]));
        root.getChildren().add(newBlackButton(50, 155, blackIds[2]));
        root.getChildren().add(newBlackButton(50, 185, blackIds[3]));
        root.getChildren().add(newBlackButton(50, 215, blackIds[4]));*/
        StackPane piano = new StackPane(root);
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 25; j++){
                Button square = new Button();
                square.setPrefSize(30, 30);
                square.setBackground(BLACK_BACKGROUD);
                square.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                partition.add(square, j, i);
            }
        }
        HBox hbox = new HBox(piano,new Separator(Orientation.VERTICAL), partition);
        StackPane menu = new StackPane(exitButton);
        menu.setAlignment(Pos.BASELINE_RIGHT);
        VBox vbox = new VBox(menu, new Separator(Orientation.HORIZONTAL), hbox);

        getChildren().addAll(vbox);
    }

    private Button newButton(double x, double y, String text){
        Button button = new Button();
        button.setText(text);
        if(text.length() > 3){
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
        button.setPrefSize(300, 30);
        return button;
    }

    private Button newBlackButton(double x, double y, String text){
        Button button = new Button();
        button.setText(text);
        button.setTextFill(Color.WHITE);
        button.setBackground(BLACK_BACKGROUD);
        button.setBorder(WHITE_BORDER);
        button.setAlignment(Pos.BASELINE_RIGHT);
        button.setOnAction(handlerNotes);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefSize(250, 30);
        return button;
    }

    EventHandler<ActionEvent> handlerNotes = event -> {
        Button source = (Button) event.getSource();
        Integer i = notes.get(source.getText());
        Button square = new Button();
        square.setPrefSize(30, 30);
        square.setBackground(GREEN_BACKGROUD);
        square.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        partition.add(square, current, i);
        System.out.println(source.getText());
        current++;
    };

    EventHandler<ActionEvent> exit = event -> {
        Platform.exit();
    };
}
