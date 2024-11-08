package controllers;

import entities.enemies.Enemies;
import entities.enemies.MiniBear;
import entities.enemies.Skeleton;
import game.Path;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final Pane gamePane;
    private final List<Enemies> enemies;
    private final Path path;

    public GameController(Pane gamePane) {
        this.gamePane = gamePane;
        this.enemies = new ArrayList<>();
        this.path = new Path();

        spawnEnemies();
        startGameLoop();
    }

    private void spawnEnemies() {
        MiniBear miniBear = new MiniBear(path.getWaypoints());
        Skeleton skeleton = new Skeleton((path.getWaypoints()));
        enemies.add(miniBear);

        gamePane.getChildren().add(miniBear);
    }

    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        gameLoop.start();
    }

    private void update() {
        for (Enemies enemy : enemies) {
            enemy.move();
        }
    }
}
