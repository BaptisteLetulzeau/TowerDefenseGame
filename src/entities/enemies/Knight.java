package entities.enemies;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.List;

public class Knight extends Enemies {

    private static final int FRAME_WIDTH = 125;
    private static final int FRAME_HEIGHT = 190;
    private static final int COLUMNS = 6;
    private static final int ROWS = 8;
    private static final int TOTAL_FRAMES = COLUMNS * ROWS;
    private int currentFrame = 0;
    private Timeline animation;
    private List<Point2D> waypoints;
    private int currentWaypointIndex = 0;
    private double speed = 2.0;

    public Knight(List<Point2D> waypoints) {
        super("assets/images/enemies/Warrior.png", waypoints);
        this.waypoints = waypoints;

        setFitWidth(FRAME_WIDTH);
        setFitHeight(FRAME_HEIGHT);
        setViewport(new Rectangle2D(0, 0, FRAME_WIDTH, FRAME_HEIGHT));

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

            // Vérifier si le personnage est proche du waypoint
            if (position.distance(target) < speed) {
                currentWaypointIndex++;
            }
        }
    }

    private void startAnimation() {
        animation = new Timeline(new KeyFrame(Duration.millis(40), event -> {
            updateFrame();
            move();
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private void updateFrame() {
        // Calculer l'index de la colonne et de la ligne pour la frame actuelle
        int col = currentFrame % COLUMNS;
        int row = currentFrame / COLUMNS;

        // Mettre à jour le viewport pour afficher la frame actuelle
        setViewport(new Rectangle2D(col * FRAME_WIDTH, row * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));

        // Passer à la frame suivante
        currentFrame = (currentFrame + 1) % TOTAL_FRAMES;
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
