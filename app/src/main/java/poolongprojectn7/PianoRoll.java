package poolongprojectn7;

import javafx.geometry.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.*;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.event.*;

public class PianoRoll extends Application{
    private final Background WHITE_BACKGROUD = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background BLACK_BACKGROUD = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
    private final Border BLACK_BORDER = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    private final Border WHITE_BORDER = new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        Button exitButton = new Button("Quitter");
        exitButton.setOnAction(exit);
        exitButton.setLayoutX(420);
        exitButton.setLayoutY(0);
        Group root = new Group(exitButton);
        String[] whiteIds = {"do","re","mi","fa","sol","la","si"};
        String[] blackIds = {"DoDiese","reDiese","faDiese","solDiese","laDiese"};
        for(int i = 0; i < 7; i++){
            root.getChildren().add(newWhiteButton(50, 50 + 30 * i, whiteIds[i]));
        }
        root.getChildren().add(newBlackButton(50, 65, blackIds[0]));
        root.getChildren().add(newBlackButton(50, 95, blackIds[1]));
        root.getChildren().add(newBlackButton(50, 155, blackIds[2]));
        root.getChildren().add(newBlackButton(50, 185, blackIds[3]));
        root.getChildren().add(newBlackButton(50, 215, blackIds[4]));
        Scene scene = new Scene(root, 500, 300);
        scene.setFill(Color.GRAY);


        stage.setTitle("PianoRoll");
        stage.setScene(scene);
        stage.show();
    }

    private Button newWhiteButton(double x, double y, String id){
        Button button = new Button();
        button.setId(id);
        button.setTextFill(Color.BLACK);
        button.setBackground(WHITE_BACKGROUD);
        button.setBorder(BLACK_BORDER);
        button.setAlignment(Pos.BASELINE_RIGHT);
        button.setOnAction(handlerNotes);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefSize(300, 30);
        return button;
    }

    private Button newBlackButton(double x, double y, String id){
        Button button = new Button();
        button.setId(id);
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
        System.out.println(source.getId());
    };

    EventHandler<ActionEvent> exit = event -> {
        Platform.exit();
    };
}
