package entities.enemies;

import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.List;

public class Gnom extends Enemies {

    //192 190
    private static final int FRAME_WIDTH = 192;
    private static final int FRAME_HEIGHT = 190;
    private static final int COLUMNS = 10;
    private static final int FRAMES_IN_SECOND_LINE = 6;
    private int currentFrame = 0;
    private List<Point2D> waypoints;
    private double speed = 4.5;
    private Image spriteSheet;
    private double startX;
    private double startY;
    private int currentWaypointIndex = 0;

    public Gnom(List<Point2D> waypoints, double startX, double startY) {
        super("assets/images/enemies/Gnom.png", waypoints, 120);
        this.waypoints = waypoints;
        this.spriteSheet = new Image(getClass().getResource("/assets/images/enemies/Gnom.png").toExternalForm());

        setImage(this.spriteSheet);
        setFitWidth(FRAME_WIDTH);
        setFitHeight(FRAME_HEIGHT);
        setViewport(new Rectangle2D(0, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));

        this.startX = startX;
        this.startY = startY;
        setLayoutX(startX);
        setLayoutY(startY);
    }

    private void move() {
        if (hasReachedFinalWaypoint()) {
            return;
        }

        Point2D currentWaypoint = waypoints.get(currentWaypointIndex);
        double targetX = currentWaypoint.getX();
        double targetY = currentWaypoint.getY();

        double deltaX = targetX - getLayoutX();
        double deltaY = targetY - getLayoutY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance < speed) {
            currentWaypointIndex++;
            if (currentWaypointIndex >= waypoints.size()) {
                return;
            }
            currentWaypoint = waypoints.get(currentWaypointIndex);
            targetX = currentWaypoint.getX();
            targetY = currentWaypoint.getY();
        }

        double directionX = deltaX / distance;
        double directionY = deltaY / distance;

        setLayoutX(getLayoutX() + directionX * speed);
        setLayoutY(getLayoutY() + directionY * speed);
    }

    @Override
    public void update() {
        move();
        updateFrame();
    }

    public boolean hasReachedFinalWaypoint() {
        return currentWaypointIndex >= waypoints.size();
    }

    private void updateFrame() {
        int col = currentFrame % COLUMNS;
        int row = 1;

        setViewport(new Rectangle2D(col * FRAME_WIDTH, row * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));
        currentFrame = (currentFrame + 1) % FRAMES_IN_SECOND_LINE;
    }
}
