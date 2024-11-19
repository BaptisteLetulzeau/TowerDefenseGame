package entities.towers;

import entities.enemies.Enemies;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class ArrowTower extends Towers {

    private static final int FRAME_WIDTH = 192;
    private static final int FRAME_HEIGHT = 190;
    private static final int IDLE_FRAMES = 6;
    private static final int COLUMNS = 8;
    private static final int TOTAL_ATTACK_ROWS = 7;

    private Timeline animation;
    private int currentFrame = 0;
    private boolean isAttacking = false;
    private int currentAttackRow = 0;

    public ArrowTower(double x, double y) {
        super(x, y, "/assets/images/towers/Archer.png", 250);

        setX(x);
        setY(y);

        setFitWidth(FRAME_WIDTH);
        setFitHeight(FRAME_HEIGHT);

        /* Affichage de la première frame de la ligne "idle" par défaut */
        setViewport(new Rectangle2D(0, 0, FRAME_WIDTH, FRAME_HEIGHT));
        startAnimation();
    }

    /**
     * Démarre l'animation de la tour.
     */
    private void startAnimation() {
        animation = new Timeline(new KeyFrame(Duration.millis(100), event -> updateFrame()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    /**
     * Mise à jour de la frame de la tour en fonction de son état (idle ou attaque).
     */
    private void updateFrame() {
        int col, row;

        if (isAttacking) {
            /* Animation d'attaque */
            col = currentFrame % COLUMNS;
            row = currentAttackRow;

            /* Vérification de la ligne d'attaque si elle est valide */
            if (row >= 0 && row < TOTAL_ATTACK_ROWS) {
                setViewport(new Rectangle2D(
                        col * FRAME_WIDTH,
                        row * FRAME_HEIGHT,
                        FRAME_WIDTH,
                        FRAME_HEIGHT
                ));
            } else {
                System.err.println("Erreur : Ligne d'attaque invalide : " + row);
                isAttacking = false;
                return;
            }

            currentFrame++;

            /* Fin de l'animation d'attaque */
            if (currentFrame >= COLUMNS) {
                isAttacking = false;
                currentFrame = 0;
            }
        } else {
            /* Animation "idle" (6 premières colonnes de la ligne 0) */
            col = currentFrame % IDLE_FRAMES;
            row = 0;

            setViewport(new Rectangle2D(
                    col * FRAME_WIDTH,
                    row * FRAME_HEIGHT,
                    FRAME_WIDTH,
                    FRAME_HEIGHT
            ));

            currentFrame = (currentFrame + 1) % IDLE_FRAMES;
        }
    }

    /**
     * Déclenche l'animation d'attaque en direction d'un ennemi donné.
     */
    public synchronized void triggerAttackAnimation(Enemies enemy) {
        if (!isAttacking) {
            isAttacking = true;
            currentFrame = 0;

            /* Calcul de la direction de l'ennemi */
            double dx = enemy.getLayoutX() - this.getX();
            double dy = enemy.getLayoutY() - this.getY();
            double angle = Math.toDegrees(Math.atan2(dy, dx));

            /* Déterminer la ligne d'attaque en fonction de l'angle */
            currentAttackRow = determineAttackRow(angle);

            System.out.println("Angle : " + angle + ", Ligne d'attaque : " + currentAttackRow);
        }
    }

    /**
     * Détermine la ligne à utiliser pour l'animation d'attaque en fonction de l'angle.
     */
    private int determineAttackRow(double angle) {
        /* Détermine la ligne correspondante dans la sprite en fonction de l'angle */
        if (angle >= -22.5 && angle <= 22.5) {
            return 3;
        }
        else if (angle > 22.5 && angle <= 67.5) {
            return 4;
        } else if (angle > 67.5 && angle <= 112.5) {
            return 5;
        } else if (angle > 112.5 && angle <= 157.5) {
            return 6;
        } else if (angle >= -67.5 && angle < -22.5) {
            return 2;
        } else if (angle >= -112.5 && angle < -67.5) {
            return 3;
        } else if (angle >= -157.5 && angle < -112.5) {
            return 2;
        } else {
            System.err.println("Erreur : Angle invalide détecté (" + angle + ")");
            return 0;
        }
    }
}
