package entities.enemies;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import java.util.List;

public class Troll extends Enemies {

    // Dimensions du sprite
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

        // Chargement du sprite sheet
        this.spriteSheet = new Image(getClass().getResource("/assets/images/enemies/Troll.png").toExternalForm());
        setImage(this.spriteSheet);

        // Paramètres du sprite
        setFitWidth(FRAME_WIDTH);
        setFitHeight(FRAME_HEIGHT);

        // Initialisation de la vue sur le sprite
        setViewport(new Rectangle2D(0, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));

        // Calcul des positions en pourcentage pour le placement
        this.startXPercent = startXPourcent;
        this.startYPercent = startYPourcent;
        double startX = gamePane.getWidth() * startXPourcent;
        double startY = gamePane.getHeight() * startYPourcent;
        setLayoutX(startX);
        setLayoutY(startY);

        // Réajustement des positions lors de changements de taille du panneau
        gamePane.widthProperty().addListener((obs, oldVal, newVal) -> updatePosition());
        gamePane.heightProperty().addListener((obs, oldVal, newVal) -> updatePosition());
    }

    private void updatePosition() {
        // Met à jour la position en fonction des dimensions actuelles du jeu
        double startX = gamePane.getWidth() * startXPercent;
        double startY = gamePane.getHeight() * startYPercent;
        setLayoutX(startX);
        setLayoutY(startY);
    }

    private void move() {
        // Si l'ennemi a atteint son dernier point de chemin, on s'arrête
        if (hasReachedFinalWaypoint()) {
            return;
        }

        // Calcul de la position cible en fonction des pourcentages
        Point2D waypointPercent = waypoints.get(currentWaypointIndex);
        double targetX = waypointPercent.getX() * gamePane.getWidth();
        double targetY = waypointPercent.getY() * gamePane.getHeight();

        // Calcul de la distance à parcourir
        double deltaX = targetX - getLayoutX();
        double deltaY = targetY - getLayoutY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Si la distance est inférieure à la vitesse, on passe au point suivant
        if (distance < speed) {
            currentWaypointIndex++;
            if (currentWaypointIndex >= waypoints.size()) {
                return;
            }
        } else {
            // Déplacement en fonction de la direction calculée
            double directionX = deltaX / distance;
            double directionY = deltaY / distance;

            setLayoutX(getLayoutX() + directionX * speed);
            setLayoutY(getLayoutY() + directionY * speed);
        }
    }

    @Override
    public void update() {
        // Met à jour la position et l'animation à chaque appel
        move();
        updateFrame();
    }

    public boolean hasReachedFinalWaypoint() {
        return currentWaypointIndex >= waypoints.size();
    }

    private void updateFrame() {
        // Calcul de la colonne de l'animation (ici on prend la ligne 1 pour la marche)
        int col = currentFrame % COLUMNS;
        int row = 1; // On garde la ligne 1 pour la marche

        // Mise à jour de l'animation du sprite
        if (spriteSheet != null) {
            setViewport(new Rectangle2D(col * FRAME_WIDTH, row * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));
        }

        // Mise à jour du frame pour l'animation
        currentFrame = (currentFrame + 1) % FRAMES_IN_SECOND_LINE;
    }
}
