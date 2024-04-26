package poolongprojectn7;


import javafx.geometry.Pos;            //Import pour aligner les éléments dans un layout.
import javafx.scene.control.*;         //Import des composants de l'interface utilisateur de JavaFX.
import javafx.scene.layout.HBox;       //Import du type de layout horizontal pour JavaFX.

public class Tool_Bar {
    private ToolBar toolbar;            // attribut de type ToolBar qui est la barre d'outils
 
    public Tool_Bar() {                 // construction de la barre d'outils
        this.toolbar = new ToolBar();    // initialisation

        // Menu Fichiers avec ses options
        MenuButton menuFiles = new MenuButton("Files");    //menu deroulant avec comme nom files
        menuFiles.getItems().addAll(                            // ajout des elements du menu deroulant
            new MenuItem("New project"),
            new MenuItem("Open project"),
            new MenuItem("Save project"),
            new MenuItem("Export project")
        );

        // Boutons de contrôle de lecture
        Button play = new Button("Start");
        Button pause = new Button("Pause");
        Button stop = new Button("Stop");

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

        // Metronome button 
        ToggleButton metronomeToggle = new ToggleButton("Metronome");   //trogglebutton pour distinguer l'etat active/desactive

        // Boutons pour changer de vue

        // ToggleGroup pour les ToggleButtons "Overview" et "Composition view" pour qu'un seul des deux soit active a la fois
        ToggleGroup toggleGroup = new ToggleGroup();

        // Bouton "Overview" de type ToggleButton, activé par défaut
        ToggleButton overviewButton = new ToggleButton("Overview");
        overviewButton.setSelected(true); // Activé par défaut
        overviewButton.setToggleGroup(toggleGroup);   //changement dactivation si composition view est active

        // Bouton "Composition view" de type ToggleButton
        ToggleButton compositionButton = new ToggleButton("Composition view");
        compositionButton.setToggleGroup(toggleGroup);  //changement dactivation si composition view est active


        // Ajout de tous les contrôles définis à la barre d'outils.
        toolbar.getItems().addAll(menuFiles, play, pause, stop, bpmControl, timerBox, metronomeToggle, overviewButton, compositionButton);
    }

    public ToolBar getToolbar() {
        return toolbar;
    }
}

