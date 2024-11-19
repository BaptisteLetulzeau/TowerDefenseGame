package entities.towers;

import entities.enemies.Enemies;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class KnightMan extends Towers implements Observer {

    private static final int FRAME_WIDTH = 192;
    private static final int FRAME_HEIGHT = 190;
    private static KnightMan uniqueTower = null;
    private boolean isShooting = false;
    private Timeline animation;
    private int currentFrame = 0;

    public static KnightMan createArrowTower(double x, double y) {
        if (uniqueTower != null) {
            System.out.println("Une seule tour peut être créée. Impossible d'en créer une autre.");
            return null;
        }

        uniqueTower = new KnightMan(x, y);
        return uniqueTower;
    }

    public KnightMan(double x, double y) {
        super(x, y, "/assets/images/towers/Warrior.png", 150, 25);

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
            if (isShooting) {
                shoot();
            }
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private void updateFrame() {
        int maxFrames = 6;

        int col = currentFrame % maxFrames;
        int row = currentFrame / maxFrames;

        setViewport(new Rectangle2D(col * FRAME_WIDTH, row * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));

        currentFrame = (currentFrame + 1) % maxFrames + (row * maxFrames);
    }

    private void shoot() {
        if (isShooting) {
            System.out.println("ArrowTower tire !");
        }
    }
}
