package entities.enemies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.List;

public class MiniBear extends Enemies {
    private static final int FRAME_WIDTH = 125;
    private static final int FRAME_HEIGHT = 190;
    private Timeline animation;
    private List<Point2D> waypoints;
    private int currentWaypointIndex = 0;
    private double speed = 0.5;

    public MiniBear(List<Point2D> waypoints) {
        super("assets/images/enemies/miniBear.png", waypoints);
        this.waypoints = waypoints;

        setX(waypoints.get(0).getX());
        setY(waypoints.get(0).getY());

        setFitWidth(FRAME_WIDTH);
        setFitHeight(FRAME_HEIGHT);

        startAnimation();
    }

    @Override
    public void move() {
        if (currentWaypointIndex < waypoints.size()) {
            Point2D target = waypoints.get(currentWaypointIndex);
            Point2D position = new Point2D(getX(), getY());
            Point2D direction = target.subtract(position).normalize();

            double newX = getX() + direction.getX() * speed;
            double newY = getY() + direction.getY() * speed;
            setX(newX);
            setY(newY);

            if (position.distance(target) < speed) {
                currentWaypointIndex++;
            }
        }
    }

    private void startAnimation() {
        animation = new Timeline(new KeyFrame(Duration.millis(40), event -> {
            move();
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    public void stopAnimation() {
        if (animation != null) {
            animation.stop();
        }
    }

    public void attack() {
        System.out.println("MiniBear attaque !");
    }
}
