package game;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Path {
    private final List<Point2D> waypoints;

    public Path() {
        waypoints = new ArrayList<>();
        setupPath();
    }

    private void setupPath() {
        waypoints.add(new Point2D(0, 500));
        waypoints.add(new Point2D(1000, 500));
    }

    public List<Point2D> getWaypoints() {
        return waypoints;
    }
}
