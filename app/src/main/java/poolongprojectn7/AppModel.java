package poolongprojectn7;

public class AppModel {

    public enum View {OVERVIEW, COMPOSITION};

    private View currentView;
    private String selectedTrack;
    private String[] trackSaves = new String[10];

    public AppModel() {
        for (int i = 0; i < 10; i++) {
            trackSaves[i] = "Track ".concat(Integer.toString(i + 1));
            this.selectedTrack = trackSaves[0]; // Default track is "Track 1"
        }
    }

    // precondition : 0 <= index < 10
    public void setCurrentPattern(int index) {
        index = Math.abs(index) % 10; // For robustness
        if (index == 0) {
            index = 10;
        }
        this.selectedTrack = trackSaves[index - 1];
    }

    public String getSelectedTrack() {
        return this.selectedTrack;
    }

    public View getCurrentView() {
        return this.currentView;
    }

    public void setCurrentView(View view) {
        this.currentView = view;
    }

}
