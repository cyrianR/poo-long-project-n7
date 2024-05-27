package poolongprojectn7.MixerComponent;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.HashMap;
import java.util.Map;

public class MixerComponent extends HBox {
    private ToggleGroup trackGroup;
    private VBox effectsBox;
    private Map<Integer, Map<String, Double>> trackEffects;
    private Map<Integer, ToggleButton> effectsButtons;

    public MixerComponent() {
        setSpacing(10); // Space between each track
        setPadding(new Insets(10)); // Padding around the mixer

        trackGroup = new ToggleGroup();
        trackEffects = new HashMap<>();
        effectsButtons = new HashMap<>();

        for (int i = 0; i < 11; i++) {
            int trackIndex = i;  // Declare final variable for each iteration
            trackEffects.put(trackIndex, createDefaultEffects());

            VBox trackBox = new VBox(10);
            trackBox.setPadding(new Insets(5));
            trackBox.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
            trackBox.setAlignment(Pos.CENTER); // Centering all elements

            Label trackNumber = new Label(trackIndex == 0 ? "MT" : String.valueOf(trackIndex));
            trackNumber.setStyle("-fx-alignment: center;");

            Slider volumeSlider = new Slider(0, 100, 50);
            volumeSlider.setOrientation(Orientation.VERTICAL);
            volumeSlider.setShowTickLabels(true);
            volumeSlider.setShowTickMarks(true);
            volumeSlider.setMajorTickUnit(50);
            volumeSlider.setMinorTickCount(4);
            volumeSlider.setBlockIncrement(10);
            volumeSlider.setPrefWidth(40); // Making the slider wider

            ToggleButton effectsButton = new ToggleButton("Effects");
            effectsButton.setMinWidth(60);
            effectsButton.setOnAction(event -> {
                if (effectsButton.isSelected()) {
                    effectsButton.setStyle("-fx-background-color: yellow;");
                    showEffects(trackIndex);
                } else {
                    effectsButton.setStyle("-fx-background-color: lightgrey;");
                    resetEffects(trackIndex);
                }
            });
            effectsButtons.put(trackIndex, effectsButton);

            ToggleButton muteButton = new ToggleButton();
            muteButton.setShape(new Circle(10));
            muteButton.setMinSize(20, 20);
            muteButton.setMaxSize(20, 20);
            muteButton.setStyle("-fx-background-color: lightgrey;");
            muteButton.setOnAction(event -> {
                if (muteButton.isSelected()) {
                    muteButton.setStyle("-fx-background-color: black;");
                } else {
                    muteButton.setStyle("-fx-background-color: lightgrey;");
                }
            });

            RadioButton trackSelectButton = new RadioButton();
            trackSelectButton.setToggleGroup(trackGroup);
            trackSelectButton.setOnAction(event -> showEffects(trackIndex));

            Label trackLabel = new Label(trackIndex == 0 ? "MASTER" : "Track " + trackIndex);
            trackLabel.setStyle("-fx-alignment: center;");

            trackBox.getChildren().addAll(trackNumber, volumeSlider, muteButton, effectsButton, trackSelectButton, trackLabel);
            getChildren().add(trackBox);
        }

        effectsBox = new VBox(10);
        effectsBox.setPadding(new Insets(5));
        effectsBox.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        effectsBox.setMinWidth(200);
        effectsBox.setAlignment(Pos.CENTER);
        updateEffectsBox(createDefaultEffects(), false);

        // Ajouter l'étiquette "MIXER"
        Label mixerLabel = new Label("MIXER");
        mixerLabel.setRotate(-90); // Rotation de 90 degrés
        mixerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36)); // Grande taille et en gras

        VBox mixerLabelBox = new VBox(mixerLabel);
        mixerLabelBox.setAlignment(Pos.CENTER);

        // Agencement des composants principaux
        HBox mainBox = new HBox(10, this, effectsBox, mixerLabelBox);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(10));

        getChildren().addAll(effectsBox, mixerLabelBox);
    }

    private Map<String, Double> createDefaultEffects() {
        Map<String, Double> effects = new HashMap<>();
        effects.put("Reverb", 0.0);
        effects.put("Delay", 0.0);
        effects.put("EQ", 0.0);
        effects.put("Gain", 0.0);
        return effects;
    }

    private void showEffects(int trackIndex) {
        if (effectsButtons.get(trackIndex).isSelected()) {
            Map<String, Double> effects = trackEffects.get(trackIndex);
            updateEffectsBox(effects, true);
        } else {
            updateEffectsBox(createDefaultEffects(), false);
        }
    }

    private void resetEffects(int trackIndex) {
        Map<String, Double> effects = createDefaultEffects();
        trackEffects.put(trackIndex, effects);
        if (trackGroup.getSelectedToggle() != null) {
            RadioButton selectedTrackButton = (RadioButton) trackGroup.getSelectedToggle();
            int selectedTrackIndex = Integer.parseInt(selectedTrackButton.getText());
            if (selectedTrackIndex == trackIndex) {
                updateEffectsBox(effects, false);
            }
        }
    }

    private void updateEffectsBox(Map<String, Double> effects, boolean enabled) {
        effectsBox.getChildren().clear();

        Label titleLabel = new Label("Effects Piste");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Separator separator = new Separator();

        effectsBox.getChildren().addAll(titleLabel, separator);

        for (Map.Entry<String, Double> entry : effects.entrySet()) {
            String effectName = entry.getKey();
            double effectValue = entry.getValue();

            Label effectLabel = new Label(effectName);
            effectLabel.setStyle("-fx-font-weight: bold;");

            Slider effectSlider = new Slider(0, 100, effectValue);
            effectSlider.setOrientation(Orientation.HORIZONTAL);
            effectSlider.setShowTickLabels(true);
            effectSlider.setShowTickMarks(true);
            effectSlider.setMajorTickUnit(50);
            effectSlider.setMinorTickCount(4);
            effectSlider.setBlockIncrement(10);
            effectSlider.setDisable(!enabled);

            if (enabled) {
                effectSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                    effects.put(effectName, newVal.doubleValue());
                });
            } else {
                effectSlider.setValue(0);
            }

            HBox effectBox = new HBox(10, effectLabel, effectSlider);
            effectBox.setAlignment(Pos.CENTER);
            effectsBox.getChildren().add(effectBox);
        }
    }
}
