package entities.enemies;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;
import java.util.List;

public class Knight extends Enemies {

    //188 180
    //192 190
    private static final int FRAME_WIDTH = 192;
    private static final int FRAME_HEIGHT = 190;
    private static final int COLUMNS = 10;
    private static final int FRAMES_IN_SECOND_LINE = 6;
    private int currentFrame = 0;
    private Timeline animation;
    private List<Point2D> waypoints;
    private int currentWaypointIndex = 0;
    private double speed = 2.0;
    private static final int centerY = 500;

    public Knight(List<Point2D> waypoints) {
        super("assets/images/enemies/Warrior.png", waypoints);
        this.waypoints = waypoints;

        setX(0);
        setY(centerY);

        setFitWidth(FRAME_WIDTH);
        setFitHeight(FRAME_HEIGHT);
        setViewport(new Rectangle2D(0, 0, FRAME_WIDTH, FRAME_HEIGHT));

        startAnimation();
    }

    private void startAnimation() {
        animation = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            updateFrame();
            move();
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private void move() {
        double newX = getX() + speed;
        setX(newX);
        setY(centerY);
    }

    private void updateFrame() {
        int col = currentFrame % COLUMNS;
        int row = 1;
        System.out.println(currentFrame);

        if (currentFrame >= FRAMES_IN_SECOND_LINE) {
            currentFrame = 0;
        }

        setViewport(new Rectangle2D(col * FRAME_WIDTH, row * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));

        currentFrame = (currentFrame + 1) % FRAMES_IN_SECOND_LINE;
    }

    public void stopAnimation() {
        if (animation != null) {
            animation.stop();
        }
    }

    public void attack() {
        System.out.println("Knight attaque !");
    }
}
