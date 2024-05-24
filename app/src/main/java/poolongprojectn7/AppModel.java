package poolongprojectn7;

public class AppModel {

    public enum View { OVERVIEW, COMPOSITION};

    private View currentView;

    public AppModel() {
        this.currentView = View.OVERVIEW;
    }

    public void setCurrentView(View view) {
        this.currentView = view;
    }

    public View getCurrentView() {
        return this.currentView;
    }
}
