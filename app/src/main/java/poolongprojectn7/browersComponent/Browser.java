package poolongprojectn7.browersComponent;

import poolongprojectn7.AppView;
import java.util.HashMap;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;

import poolongprojectn7.Pattern;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class Browser extends TreeView<String> {

    private Pattern pattern;
    private TreeView<String> browser;
    private AppView view;
    // liste de filtre des clicks
    private HashMap<String, Integer> instrumentHashMap = new HashMap<String, Integer>();


    @SuppressWarnings("unchecked")
    public Browser(AppView view) {
        this.view = view;
        this.pattern = this.view.getPianoRoll().getModel().getPattern();
        // Creation of the tree
        TreeItem<String> root = new TreeItem<>("root");
        this.browser = new TreeView<>(root);
        this.browser.setShowRoot(true);

        // Branches (instrument families)
        TreeItem<String> bank_0 = new TreeItem<>("Pianos");

        // Tree contruction
        root.getChildren().addAll(bank_0);
        // pianos.getChildren().addAll(piano, bPiano);
        HashMap<String, Integer> instruMap = createDefaultLeafNodes(bank_0);
        
        browser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String selectedItem = newValue.getValue();
            if (this.instrumentHashMap.containsKey(selectedItem)) {
                try {
                    this.pattern.setInstrument(this.instrumentHashMap.get(selectedItem));
                } catch (InvalidMidiDataException e) {
                   e.printStackTrace();
                }
            }
        });

    }

    public TreeView<String> getBrowser() {
        return this.browser;
    }

    /**
	 * Initializes default instruments in browser.
	 */
	@SuppressWarnings("unchecked")
    private HashMap<String, Integer> createDefaultLeafNodes(TreeItem parent) {
        try {
            // TODO : a modif (instruments de base)
            Instrument[] instrumentsList = MidiSystem.getSynthesizer().getAvailableInstruments();
            int ind = 0;
            // TODO : FIN
            for (Instrument instrument : instrumentsList) {
                String name = instrument.getName();
                this.instrumentHashMap.put(name, ind);
                parent.getChildren().addAll(new TreeItem<>(name));
                ind++;
            }
        }
        catch (Exception e) {
        }
        return instrumentHashMap;
    }

    public void updateBrowser() {
        this.pattern = this.view.getPianoRoll().getModel().getPattern();
    }

}
