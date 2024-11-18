package entities.towers;

import entities.enemies.Enemies;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.Set;

public class ArrowTower extends Towers {

    private static final int FRAME_WIDTH = 192;
    private static final int FRAME_HEIGHT = 190;
    private static final int COLUMNS = 8;
    private static final int IDLE_FRAMES = 5;
    private static final int ROWS_START_ATTACK = 3;

    private static ArrowTower uniqueTower = null;  // Seule tour autorisée
    private static final Set<Point2D> occupiedPositions = new HashSet<>(); // Positions occupées

    private Timeline animation;
    private int currentFrame = 0;
    private boolean isAttacking = false;
    private int currentAttackRow = 0; // Ligne active pour l'attaque

    private static final double POSITION_TOLERANCE = 10.0; // Tolérance pour les coordonnées de position

    // Méthode de création de la tour avec gestion de la duplication
    public static ArrowTower createArrowTower(double x, double y) {
        if (uniqueTower != null) {
            System.out.println("Une seule tour peut être créée. Impossible d'en créer une autre.");
            return null; // Retourne null si la tour existe déjà
        }

        // Crée la tour si elle n'existe pas encore
        uniqueTower = new ArrowTower(x, y);
        return uniqueTower;
    }

    // Vérifie si la position est occupée en tenant compte de la tolérance
    private static boolean isPositionOccupied(Point2D position) {
        for (Point2D occupiedPosition : occupiedPositions) {
            if (Math.abs(position.getX() - occupiedPosition.getX()) < POSITION_TOLERANCE &&
                    Math.abs(position.getY() - occupiedPosition.getY()) < POSITION_TOLERANCE) {
                return true;
            }
        }
        return false;
    }

    public ArrowTower(double x, double y) {
        super(x, y, "/assets/images/towers/Archer.png", 250);

        setX(x);
        setY(y);

        setFitWidth(FRAME_WIDTH);
        setFitHeight(FRAME_HEIGHT);

        setViewport(new Rectangle2D(0, 0, FRAME_WIDTH, FRAME_HEIGHT));

        startAnimation();
    }

    private void startAnimation() {
        animation = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            updateFrame();
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private void updateFrame() {
        int col, row;

        if (isAttacking) {
            // Animation pour l'attaque : Utilise 8 colonnes et 7 lignes
            col = currentFrame % COLUMNS;  // Calcul de la colonne
            row = currentAttackRow;        // L'attaque utilise la ligne d'animation calculée

            setViewport(new Rectangle2D(
                    col * FRAME_WIDTH,
                    row * FRAME_HEIGHT,
                    FRAME_WIDTH,
                    FRAME_HEIGHT
            ));

            currentFrame++;

            // Vérifier si l'animation est terminée
            if (currentFrame >= COLUMNS) { // Fin de l'animation d'attaque
                isAttacking = false;  // Revenir à l'état d'attente
                currentFrame = 0;  // Réinitialiser la frame d'animation
            }
        } else {
            // Animation pour l'attente : Utilise 6 colonnes et une seule ligne (ligne 0)
            col = currentFrame % IDLE_FRAMES;  // Calcul de la colonne
            row = 0;  // Toujours utiliser la ligne 0 pour l'état "idle"

            setViewport(new Rectangle2D(
                    col * FRAME_WIDTH,
                    row * FRAME_HEIGHT,
                    FRAME_WIDTH,
                    FRAME_HEIGHT
            ));

            // Mise à jour de l'index de la frame pour l'animation "idle"
            currentFrame = (currentFrame + 1) % IDLE_FRAMES;
        }
    }


    // Déclenche l'animation d'attaque
    public void triggerAttackAnimation(Enemies enemy) {
        if (!isAttacking) {
            isAttacking = true;
            currentFrame = 0;

            // Calcul de la direction de l'ennemi
            double dx = enemy.getLayoutX() - this.getX();
            double dy = enemy.getLayoutY() - this.getY();
            double angle = Math.toDegrees(Math.atan2(dy, dx));

            // Mapper l'angle à une ligne d'animation
            currentAttackRow = determineAttackRow(angle);
        }
    }

    private int determineAttackRow(double angle) {
        // Déterminer la ligne en fonction de l'angle
        if (angle >= -22.5 && angle <= 22.5) {
            return 5; // Droite
        } else if (angle > 22.5 && angle <= 67.5) {
            return 4; // Diagonale bas-droite
        } else if (angle > 67.5 && angle <= 112.5) {
            return 7; // Bas
        } else if (angle > 112.5 && angle <= 157.5) {
            return 6; // Diagonale bas-gauche
        } else if (angle >= -67.5 && angle < -22.5) {
            return 3; // Diagonale haut-droite
        } else if (angle >= -112.5 && angle < -67.5) {
            return 3; // Haut
        } else {
            return 3;
        }
    }
}
