package entities.towers;

import entities.enemies.Enemies;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class ArrowTower extends Towers {

    private static final int FRAME_WIDTH = 192;
    private static final int FRAME_HEIGHT = 190;
    private static ArrowTower uniqueTower = null;
    private Timeline animation;
    private int currentFrame = 0;

    public static ArrowTower createArrowTower(double x, double y) {
        if (uniqueTower != null) {
            System.out.println("Une seule tour peut être créée. Impossible d'en créer une autre.");
            return null;
        }

        uniqueTower = new ArrowTower(x, y);
        return uniqueTower;
    }

    public ArrowTower(double x, double y) {
        super(x, y, "/assets/images/towers/Archer.png", 250, 15);

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
        int maxFrames = 6;

        int col = currentFrame % maxFrames;
        int row = currentFrame / maxFrames;

        setViewport(new Rectangle2D(col * FRAME_WIDTH, row * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT));

        currentFrame = (currentFrame + 1) % maxFrames + (row * maxFrames);
    }
}
