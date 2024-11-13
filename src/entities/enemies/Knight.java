package entities.enemies;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
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
    private double speed = 2.0;
    private Image spriteSheet;
    private static final int centerY = -200;

    public Knight(List<Point2D> waypoints) {
        super("assets/images/enemies/Warrior.png", waypoints);
        this.waypoints = waypoints;
        this.spriteSheet = new Image(getClass().getResource("/assets/images/enemies/Warrior.png").toExternalForm());

        setImage(this.spriteSheet);
        setFitWidth(FRAME_WIDTH);
        setFitHeight(FRAME_HEIGHT);
        setViewport(new Rectangle2D(0, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));
    }

    private void move() {
        if (hasReachedFinalWaypoint()) {
            return;
        }

        double newX = getLayoutX() + speed;
        setLayoutX(newX);
        setLayoutY(centerY);
        System.out.println(getLayoutX());
    }

    @Override
    public void update() {
        move();
        updateFrame();
    }

    private boolean hasReachedFinalWaypoint() {
        Point2D finalWaypoint = waypoints.getLast();
        double distanceToFinalWaypoint = finalWaypoint.distance(getLayoutX(), getLayoutY());

        return distanceToFinalWaypoint <= 5;
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

    public void stopAnimation() {
        if (animation != null) {
            animation.stop();
        }
    }
}
