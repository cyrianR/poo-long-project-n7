package poolongprojectn7;

import javafx.scene.layout.Region;

public class AppController {

    private final Region view;

    public AppController() {
        AppModel viewModel = new AppModel();
        AppInteractor interactor = new AppInteractor(viewModel);
        view = new AppView(viewModel, interactor::handler);
    }

    // Accessors :

    public Region getView() {
        return view;
    }
}
