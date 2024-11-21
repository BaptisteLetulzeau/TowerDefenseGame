package entities.enemies;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import java.util.List;

public class Troll extends Enemies {

    //192 190
    private static final int FRAME_WIDTH = 192;
    private static final int FRAME_HEIGHT = 190;
    private static final int COLUMNS = 10;
    private static final int FRAMES_IN_SECOND_LINE = 6;
    private int currentFrame = 0;
    private List<Point2D> waypoints;
    private double speed = 3.5;
    private Image spriteSheet;
    private int currentWaypointIndex = 0;
    private double startXPercent;
    private double startYPercent;
    private Pane gamePane;

    public Troll(List<Point2D> waypoints, double startXPourcent, double startYPourcent, Pane gamePane) {
        super("assets/images/enemies/Troll.png", waypoints, 160);
        this.waypoints = waypoints;
        this.gamePane = gamePane;
        this.spriteSheet = new Image(getClass().getResource("/assets/images/enemies/Troll.png").toExternalForm());

        setImage(this.spriteSheet);
        setFitWidth(FRAME_WIDTH);
        setFitHeight(FRAME_HEIGHT);
        setViewport(new Rectangle2D(0, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));

        double startX = gamePane.getWidth() * startXPourcent;
        double startY = gamePane.getHeight() * startYPourcent;
        this.startXPercent = startXPourcent;
        this.startYPercent = startYPourcent;
        setLayoutX(startX);
        setLayoutY(startY);

        gamePane.widthProperty().addListener((obs, oldVal, newVal) -> updatePosition());
        gamePane.heightProperty().addListener((obs, oldVal, newVal) -> updatePosition());
    }

    private void updatePosition() {
        double startX = gamePane.getWidth() * startXPercent;
        double startY = gamePane.getHeight() * startYPercent;
        setLayoutX(startX);
        setLayoutY(startY);
    }

    private void move() {
        if (hasReachedFinalWaypoint()) {
            return;
        }

        Point2D waypointPercent = waypoints.get(currentWaypointIndex);
        double targetX = waypointPercent.getX() * getParent().getLayoutBounds().getWidth();
        double targetY = waypointPercent.getY() * getParent().getLayoutBounds().getHeight();

        double deltaX = targetX - getLayoutX();
        double deltaY = targetY - getLayoutY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance < speed) {
            currentWaypointIndex++;
            if (currentWaypointIndex >= waypoints.size()) {
                return;
            }
        }
        else {
            double directionX = deltaX / distance;
            double directionY = deltaY / distance;

            setLayoutX(getLayoutX() + directionX * speed);
            setLayoutY(getLayoutY() + directionY * speed);
        }
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
