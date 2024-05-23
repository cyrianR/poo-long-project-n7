package poolongprojectn7;

public class AppModel {

    private Pattern selectedTrackPattern;
    private Pattern[] trackPatterns = new Pattern[10];

    public AppModel() {
        for (int i = 0; i < 10; i++) {
            this.trackPatterns[i] = new Pattern();
            this.selectedTrackPattern = trackPatterns[0]; // Default track is "Track 1"
        }
    }

    // precondition :
    public void setCurrentPattern(int index) {
        index = Math.abs(index) % 10; // For robustness
        System.out.println(index);
        this.selectedTrackPattern = trackPatterns[index];
    }
}
