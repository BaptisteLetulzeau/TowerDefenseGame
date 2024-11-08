package entities.enemies;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

public class Enemies extends ImageView {
    private int currentWaypointIndex;
    private List<Point2D> waypoints;

    public Enemies(String imagePath, List<Point2D> waypoints) {
        setImage(new Image(imagePath));
        setFitWidth(100);
        setFitHeight(100);
        this.waypoints = waypoints;
        currentWaypointIndex = 0;

        setX(waypoints.getFirst().getX());
        setY(waypoints.getFirst().getY());
    }

    public void move() {
        if (currentWaypointIndex < waypoints.size() - 1) {
            Point2D currentPos = new Point2D(getX(), getY());
            Point2D targetPos = waypoints.get(currentWaypointIndex + 1);

            Point2D direction = targetPos.subtract(currentPos).normalize();
            double speed = 2.0;
            setX(getX() + direction.getX() * speed);
            setY(getY() + direction.getY() * speed);

            if (currentPos.distance(targetPos) < speed) {
                currentWaypointIndex++;
            }
        }
    }
}
