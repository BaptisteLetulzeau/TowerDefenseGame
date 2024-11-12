package entities.enemies;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;
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
    private double speed = 1;
    private static final int centerY = 500;
    private static final double DISTANCE_THRESHOLD = 5.0;
    private Image spriteSheet;

    public Knight(List<Point2D> waypoints) {
        super("assets/images/enemies/Warrior.png", waypoints);
        this.waypoints = waypoints;
        this.spriteSheet = new Image("assets/images/enemies/Warrior.png");

        setImage(subImage(spriteSheet, 0, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));
        setLayoutX(0);
        setLayoutY(centerY);

        System.out.println("Image width: " + spriteSheet.getWidth());
        System.out.println("Image height: " + spriteSheet.getHeight());

        setFitWidth(FRAME_WIDTH);
        setFitHeight(FRAME_HEIGHT);
        //setViewport(new Rectangle2D(0, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));

        startAnimation();
    }

    private void startAnimation() {
        animation = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            move();
            updateFrame();
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    public void move() {
        if (hasReachedFinalWaypoint()) {
            stopAnimation();
            System.out.println("Knight a atteint le point final.");
            return;
        }

        double newX = getLayoutX() + speed;
        setLayoutX(newX);
        setLayoutY(centerY);
        System.out.println(newX);
    }

    private void updateFrame() {
        int col = currentFrame % COLUMNS;
        int row = 1;

        setViewport(new Rectangle2D(col * FRAME_WIDTH, row * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));

        currentFrame = (currentFrame + 1) % FRAMES_IN_SECOND_LINE;
    }

    private Image subImage(Image source, int x, int y, int width, int height) {
        WritableImage image = new WritableImage(width, height);
        image.getPixelWriter().setPixels(0, 0, width, height, source.getPixelReader(), x, y);
        return image;
    }

    private boolean hasReachedFinalWaypoint() {
        Point2D finalWaypoint = waypoints.getLast();
        double distanceToFinalWaypoint = finalWaypoint.distance(getLayoutX(), getLayoutY());

        return distanceToFinalWaypoint <= DISTANCE_THRESHOLD;
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
