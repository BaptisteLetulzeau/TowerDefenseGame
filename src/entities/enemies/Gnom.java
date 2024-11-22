package entities.enemies;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import java.util.List;

public class Gnom extends Enemies {

    private static final int FRAME_WIDTH = 192;
    private static final int FRAME_HEIGHT = 190;
    private static final int COLUMNS = 10;
    private static final int FRAMES_IN_SECOND_LINE = 6;
    private int currentFrame = 0;
    private List<Point2D> waypoints;
    private double speed = 4.5;
    private Image spriteSheet;
    private int currentWaypointIndex = 0;
    private double startXPercent;
    private double startYPercent;
    private Pane gamePane;

    public Gnom(List<Point2D> waypoints, double startXPourcent, double startYPourcent, Pane gamePane) {
        super("assets/images/enemies/Gnom.png", waypoints, 120);
        this.waypoints = waypoints;
        this.gamePane = gamePane;
        this.spriteSheet = new Image(getClass().getResource("/assets/images/enemies/Gnom.png").toExternalForm());

        setImage(this.spriteSheet);
        setFitWidth(FRAME_WIDTH);
        setFitHeight(FRAME_HEIGHT);
        setViewport(new Rectangle2D(0, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));

        // Calcul des positions de départ en pourcentage du jeu pane
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
        // Si on a atteint le dernier waypoint, on arrête le mouvement
        if (hasReachedFinalWaypoint()) {
            return;
        }

        // Récupérer le waypoint actuel
        Point2D waypointPercent = waypoints.get(currentWaypointIndex);

        // Calculer la position du waypoint en fonction des dimensions de gamePane
        double targetX = waypointPercent.getX() * gamePane.getWidth();
        double targetY = waypointPercent.getY() * gamePane.getHeight();

        // Calculer la distance entre la position actuelle et le waypoint
        double deltaX = targetX - getLayoutX();
        double deltaY = targetY - getLayoutY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Si la distance est inférieure à la vitesse, on considère qu'on est arrivé à destination
        if (distance < speed) {
            currentWaypointIndex++; // On passe au prochain waypoint

            // Si tous les waypoints ont été parcourus, on arrête de se déplacer
            if (currentWaypointIndex >= waypoints.size()) {
                return;
            }
        } else {
            // Calcul de la direction normalisée (unit vector)
            double directionX = deltaX / distance;
            double directionY = deltaY / distance;

            // Déplacement du Gnom
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
