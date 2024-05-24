package poolongprojectn7;

import javafx.scene.layout.Region;

public class AppController {

    /** View part of the MVC of the application. */
    private final AppView view;

    /** Build the Interactor of the MVC part of the application. */
    public AppController() {
        AppModel viewModel = new AppModel();
        AppInteractor interactor = new AppInteractor(viewModel);
        view = new AppView(this, viewModel, interactor::handler);
    }

    // Accessors :

    /** Returns the view of the MVC model.
     * @return the view of the MVC
    */
    public AppView getView() {
        return view;
    }

    public void switchToOverview() {
        view.switchToOverview();
    }

    public void switchToCompositionView() {
        view.switchToCompositionView();
    }
}
