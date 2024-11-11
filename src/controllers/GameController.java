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

        List<Point2D> waypoints = new ArrayList<>();
        waypoints.add(new Point2D(1300, 900));

        Knight knight = new Knight(waypoints);

        gamePane.getChildren().addAll(knight);
    }
}
