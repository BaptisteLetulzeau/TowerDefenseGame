package game;

import entities.enemies.Enemies;
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
        waypoints.add(new Point2D(0, 0.45));
        waypoints.add(new Point2D(0.1, 0.42));
        waypoints.add(new Point2D(0.1, 0.14));
        waypoints.add(new Point2D(0.3, 0.14));
        waypoints.add(new Point2D(0.3, 0.60));
        waypoints.add(new Point2D(0.57, 0.60));
        waypoints.add(new Point2D(0.57, 0.4));
        waypoints.add(new Point2D(0.9, 0.4));
    }

    public List<Point2D> getWaypoints() {
        return waypoints;
    }
}
