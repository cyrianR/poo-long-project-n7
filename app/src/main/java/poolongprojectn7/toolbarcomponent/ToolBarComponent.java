package poolongprojectn7.toolbarcomponent;

import javafx.geometry.Pos;            //Import pour aligner les éléments dans un layout.
import javafx.scene.control.*;         //Import des composants de l'interface utilisateur de JavaFX.
import javafx.scene.layout.HBox;       //Import du type de layout horizontal pour JavaFX.
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ToolBarComponent extends ToolBar {
    private ToolBar toolbar;            // attribut de type ToolBar qui est la barre d'outils
 
    public ToolBarComponent() {                 // construction de la barre d'outils
        this.toolbar = new ToolBar();    // initialisation

        // Menu Fichiers avec ses options
        MenuButton menuFiles = new MenuButton("Files");    //menu deroulant avec comme nom files
        ImageView fileIcon = createImageView("File.png"); // Création de l'icône pour le bouton Files.
        menuFiles.setGraphic(fileIcon); // Assignation de l'icône au bouton Files.
        menuFiles.getItems().addAll(                            // ajout des elements du menu deroulant
            new MenuItem("New project"),
            new MenuItem("Open project"),
            new MenuItem("Save project"),
            new MenuItem("Export project")
        );

        // Boutons de contrôle de lecture avec icônes.
        Button play = createButtonWithImage("Play.png", "Start");
        Button pause = createButtonWithImage("Pause.png", "Pause");
        Button stop = createButtonWithImage("Stop.png", "Stop");

        // Slider pour le BPM
        Slider bpmSlider = new Slider(50, 250, 120);  //initialiser le slider avec les bornes et la valeur par defaut 120
        bpmSlider.setShowTickLabels(true);       //affichage des graduations pour chaque valeur significative           
        bpmSlider.setShowTickMarks(true);        //affichage des valeurs significatives
        bpmSlider.setMajorTickUnit(50);          //definition du pas majeur du slider = 50
        bpmSlider.setMinorTickCount(4);          // definition des sous pas du slider = 4
        bpmSlider.setSnapToTicks(true);          // positionnement du curseur sur la graduation la plus proche sur le slider
        Label bpmLabel = new Label("BPM:");        // nommer le slider par BPM
        HBox bpmControl = new HBox(bpmLabel, bpmSlider);   //attacher le nom au slider dans une layout horizental
        bpmControl.setAlignment(Pos.CENTER_LEFT);         //ajuster lalignement du label avec le slider

        // Timer label
        Label timerLabel = new Label("Timer: ");

        // affichage du temps, initialisé à 00:00
        Label timeLabel = new Label("00:00");
        HBox timerBox = new HBox(timerLabel, timeLabel); // Layout horizontal pour le timer
        timerBox.setAlignment(Pos.CENTER_LEFT); // Alignement du contenu du timer à gauche

        // Bouton Métronome avec icône.
        ToggleButton metronomeToggle = createToggleButtonWithImage("Metronom.png", "Metronome"); // Bouton à bascule pour le métronome.

        // Boutons pour changer de vue

        // ToggleGroup pour les ToggleButtons "Overview" et "Composition view" pour qu'un seul des deux soit active a la fois
        ToggleGroup toggleGroup = new ToggleGroup();

        // Bouton "Overview" de type ToggleButton avec icone, activé par défaut
        ToggleButton overviewButton = createToggleButtonWithImage("Overview.png", "Overview"); // Bouton pour la vue d'ensemble.
        overviewButton.setSelected(true); // Activé par défaut
        overviewButton.setToggleGroup(toggleGroup);   //changement dactivation si composition view est active

        // Bouton "Composition view" de type ToggleButton avec icone
        ToggleButton compositionButton = createToggleButtonWithImage("Composition view.png", "Composition view"); // Bouton pour la vue de composition.
        compositionButton.setToggleGroup(toggleGroup);  //changement dactivation si composition view est active


        // Ajout de tous les contrôles définis à la barre d'outils.
        toolbar.getItems().addAll(menuFiles, play, pause, stop, bpmControl, timerBox, metronomeToggle, overviewButton, compositionButton);
    
    }

    // Méthode pour créer une ImageView à partir d'un nom de fichier d'image.
    private ImageView createImageView(String imageName) {
        Image image = new Image(getClass().getResourceAsStream("/icons/" + imageName));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20); // Ajustement de la hauteur de l'icône.
        imageView.setFitWidth(20); // Ajustement de la largeur de l'icône.
        return imageView;
    }

    // Méthode pour créer un bouton avec une icône et une infobulle.
    private Button createButtonWithImage(String imageName, String tooltipText) {
        Button button = new Button();
        button.setGraphic(createImageView(imageName)); // Assignation de l'icône au bouton.
        button.setTooltip(new Tooltip(tooltipText)); // Assignation de l'infobulle au bouton.
        return button;
    }

    // Méthode pour créer un ToggleButton avec une icône et une infobulle.
    private ToggleButton createToggleButtonWithImage(String imageName, String tooltipText) {
        ToggleButton toggleButton = new ToggleButton();
        toggleButton.setGraphic(createImageView(imageName)); // Assignation de l'icône au bouton à bascule.
        toggleButton.setTooltip(new Tooltip(tooltipText)); // Assignation de l'infobulle au bouton à bascule.
        return toggleButton;
    }

    public ToolBar getToolbar() {
        return toolbar;
    }
}

