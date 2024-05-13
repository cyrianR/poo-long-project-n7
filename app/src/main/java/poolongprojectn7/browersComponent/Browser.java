package poolongprojectn7.browersComponent;

import poolongprojectn7.AppView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class Browser extends TreeView<String> {

    private TreeView<String> browser;
    private AppView view;

    @SuppressWarnings("unchecked")
    public Browser(AppView view) {
        this.view = view;
        // Creation of the tree
        TreeItem<String> root = new TreeItem<>("root");
        this.browser = new TreeView<>(root);
        this.browser.setShowRoot(true);

        // Branches (instrument families)
        TreeItem<String> percussions = new TreeItem<>("Percussions");
        TreeItem<String> strings = new TreeItem<>("Strings");

        // Leaves (instruments)
        TreeItem<String> guitar = new TreeItem<>("Guitar");
        TreeItem<String> violin = new TreeItem<>("Violin");
        TreeItem<String> drum = new TreeItem<>("Drum");

        // Tree contruction
        root.getChildren().addAll(percussions, strings);
        percussions.getChildren().addAll(drum);
        strings.getChildren().addAll(guitar, violin);

        browser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String selectedItem = newValue.getValue();
            System.out.println(selectedItem);
        });

    }

    public TreeView<String> getBrowser() {
        return this.browser;
    }
   
}
