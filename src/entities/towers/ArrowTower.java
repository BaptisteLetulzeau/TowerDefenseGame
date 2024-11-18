package entities.towers;

import entities.enemies.Enemies;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class ArrowTower extends Towers {

    private static final int FRAME_WIDTH = 192;   // Largeur d'une frame
    private static final int FRAME_HEIGHT = 190;  // Hauteur d'une frame
    private static final int IDLE_FRAMES = 6;    // Nombre de frames pour "idle" (6 premières colonnes)
    private static final int COLUMNS = 8;        // Nombre de colonnes dans le sprite sheet
    private static final int TOTAL_ATTACK_ROWS = 7; // Nombre de lignes pour l'attaque

    private Timeline animation;
    private int currentFrame = 0;
    private boolean isAttacking = false;
    private int currentAttackRow = 0; // Ligne active pour l'attaque

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
        animation = new Timeline(new KeyFrame(Duration.millis(100), event -> updateFrame()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private void updateFrame() {
        int col, row;

        if (isAttacking) {
            // Gestion de l'animation d'attaque
            col = currentFrame % COLUMNS;  // Colonne actuelle
            row = currentAttackRow;        // Ligne correspondant à la direction de l'attaque

            setViewport(new Rectangle2D(
                    col * FRAME_WIDTH,
                    row * FRAME_HEIGHT,
                    FRAME_WIDTH,
                    FRAME_HEIGHT
            ));

            currentFrame++;

            // Fin de l'animation d'attaque
            if (currentFrame >= COLUMNS) {
                isAttacking = false;  // Retour à l'état "idle"
                currentFrame = 0;
            }
        } else {
            // Gestion de l'animation "idle" (6 premières colonnes de la ligne 0)
            col = currentFrame % IDLE_FRAMES;  // Reste dans les 6 premières colonnes
            row = 0;  // Ligne 0 pour "idle"

            setViewport(new Rectangle2D(
                    col * FRAME_WIDTH,
                    row * FRAME_HEIGHT,
                    FRAME_WIDTH,
                    FRAME_HEIGHT
            ));

            currentFrame = (currentFrame + 1) % IDLE_FRAMES;
        }
    }

    public void triggerAttackAnimation(Enemies enemy) {
        if (!isAttacking) {
            isAttacking = true;
            currentFrame = 0;

            // Calcul de la direction de l'ennemi
            double dx = enemy.getLayoutX() - this.getX();
            double dy = enemy.getLayoutY() - this.getY();
            double angle = Math.toDegrees(Math.atan2(dy, dx));

            // Déterminer la ligne d'attaque en fonction de l'angle
            currentAttackRow = determineAttackRow(angle);
        }
    }

    private int determineAttackRow(double angle) {
        if (angle >= -22.5 && angle <= 22.5) {
            return 3; // Droite
        } else if (angle > 22.5 && angle <= 67.5) {
            return 4; // Diagonale bas-droite
        } else if (angle > 67.5 && angle <= 112.5) {
            return 5; // Bas
        } else if (angle > 112.5 && angle <= 157.5) {
            return 6; // Diagonale bas-gauche
        } else if (angle >= -67.5 && angle < -22.5) {
            return 2; // Diagonale haut-droite
        } else if (angle >= -112.5 && angle < -67.5) {
            return 1; // Haut
        } else {
            return 7; // Diagonale haut-gauche
        }
    }
}
