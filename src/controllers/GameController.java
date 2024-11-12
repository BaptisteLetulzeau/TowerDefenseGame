package controllers;

import entities.enemies.Enemies;
import entities.enemies.Knight;
//import entities.enemies.Skeleton;
import entities.towers.ArrowTower;
import game.Path;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;

public class GameController {
    private final Pane gamePane;
    private final List<Enemies> enemies;
    private final Path path;

    public GameController(Pane gamePane) {
        this.gamePane = gamePane;
        this.enemies = new ArrayList<>();
        this.path = new Path();

        Knight knight = new Knight(path.getWaypoints());
        enemies.add(knight);

        gamePane.getChildren().addAll(knight);
    }

    public void update() {
        for (Enemies enemy : enemies) {
            if (enemy instanceof Knight) {
                ((Knight) enemy).move();
            }
        }
    }
}
