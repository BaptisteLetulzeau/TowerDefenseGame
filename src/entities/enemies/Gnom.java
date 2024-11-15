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
    private Timeline animation;
    private List<Point2D> waypoints;
    private double speed = 2.5;
    private Image spriteSheet;
    private static final int centerY = 240;

    public Gnom(List<Point2D> waypoints) {
        super("assets/images/enemies/Gnom.png", waypoints);
        this.waypoints = waypoints;
        this.spriteSheet = new Image(getClass().getResource("/assets/images/enemies/Gnom.png").toExternalForm());

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

        notifyObservers();
        //System.out.println(getLayoutX());
    }

    @Override
    public void update() {
        move();
        updateFrame();
    }

    public boolean hasReachedFinalWaypoint() {
        double finalWaypointX = waypoints.getLast().getX();
        return getLayoutX() >= finalWaypointX;
    }

    private void updateFrame() {
        int col = currentFrame % COLUMNS;
        int row = 1;

        setViewport(new Rectangle2D(col * FRAME_WIDTH, row * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));
        currentFrame = (currentFrame + 1) % FRAMES_IN_SECOND_LINE;
    }
}
