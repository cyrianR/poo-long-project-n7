package poolongprojectn7;

import javafx.scene.layout.Region;

public class appController {

    private final Region view;

    public appController() {
        appModel viewModel = new appModel();
        appInteractor interactor = new appInteractor(viewModel);
        view = new appView(viewModel, interactor::handler);
    }

    // Accessors :

    public Region getView() {
        return view;
    }
}
