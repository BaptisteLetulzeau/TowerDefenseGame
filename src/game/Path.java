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
        waypoints.add(new Point2D(-150, 420));
        waypoints.add(new Point2D(130, 420));
        waypoints.add(new Point2D(130, 140));
        waypoints.add(new Point2D(400, 140));
        waypoints.add(new Point2D(400, 550));
        waypoints.add(new Point2D(750, 550));
        waypoints.add(new Point2D(750, 350));
        waypoints.add(new Point2D(1100, 350));
    }

    public List<Point2D> getWaypoints() {
        return waypoints;
    }
}
