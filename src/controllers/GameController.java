package controllers;

import entities.enemies.Dwarf;
import entities.enemies.Enemies;
import entities.enemies.Gnom;
//import entities.enemies.Skeleton;
import entities.enemies.Troll;
import game.Path;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GameController {
    private final Pane gamePane;
    private final List<Enemies> enemies;
    private final Path path;
    private Text gameOverText;

    public GameController(Pane gamePane) {
        this.gamePane = gamePane;
        this.enemies = new ArrayList<>();
        this.path = new Path();

        Gnom knight = new Gnom(path.getWaypoints());
        Troll troll = new Troll(path.getWaypoints());
        Dwarf dwarf = new Dwarf(path.getWaypoints());
        enemies.add(knight);
        enemies.add(troll);
        enemies.add(dwarf);

        gamePane.getChildren().addAll(knight, troll, dwarf);

        gameOverText = new Text("PERDU");
        gameOverText.setFont(new Font("Arial", 50));
        gameOverText.setFill(Color.RED);
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        gameOverText.setVisible(false);
        gamePane.getChildren().add(gameOverText);
    }

    public void update() {
        for (Enemies enemy : enemies) {
            if (enemy instanceof Gnom knight){
                knight.update();

                if (knight.hasReachedFinalWaypoint()) {
                    showGameOver();
                    return;
                }
            }
            else if (enemy instanceof Troll troll){
                troll.update();

                if (troll.hasReachedFinalWaypoint()) {
                    showGameOver();
                    return;
                }
            }
            else if (enemy instanceof Dwarf dwarf){
                dwarf.update();

                if (dwarf.hasReachedFinalWaypoint()) {
                    showGameOver();
                    return;
                }
            }
        }
    }

    private void showGameOver() {
        gameOverText.setVisible(true);
        gameOverText.setX((gamePane.getWidth() - gameOverText.getBoundsInLocal().getWidth()) / 2);
        gameOverText.setY((gamePane.getHeight() - gameOverText.getBoundsInLocal().getHeight()) / 2);

        //gameLoop.stop();
    }
}
