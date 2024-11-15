package controllers;

import entities.enemies.Dwarf;
import entities.enemies.Enemies;
import entities.enemies.Gnom;
import entities.enemies.Troll;
import entities.towers.Towers;
import game.Path;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameController {
    private final Pane gamePane;
    private List<Enemies> activeEnemies;
    private final List<List<Enemies>> waves;
    private final Path path;
    private int currentWaveIndex = 0;
    private Text gameOverText;
    private Timeline waveTimeline;
    private final List<Towers> activeTowers = new ArrayList<>();

    public GameController(Pane gamePane) {
        this.gamePane = gamePane;
        this.activeEnemies = new ArrayList<>();
        this.path = new Path();

        this.waves = createWaves();

        setupGameOverText();

        startWave();
    }

    private List<List<Enemies>> createWaves() {
        List<List<Enemies>> waves = new ArrayList<>();

        List<Enemies> wave1 = new ArrayList<>(List.of(new Gnom(path.getWaypoints()), new Troll(path.getWaypoints()), new Dwarf(path.getWaypoints())));
        List<Enemies> wave2 = new ArrayList<>(List.of(new Troll(path.getWaypoints()), new Troll(path.getWaypoints()), new Dwarf(path.getWaypoints()), new Dwarf(path.getWaypoints())));
        List<Enemies> wave3 = new ArrayList<>(List.of(new Dwarf(path.getWaypoints()), new Dwarf(path.getWaypoints()), new Gnom(path.getWaypoints()), new Dwarf(path.getWaypoints()), new Dwarf(path.getWaypoints())));

        waves.add(wave1);
        waves.add(wave2);
        waves.add(wave3);

        return waves;
    }

    private void startWave() {
        if (currentWaveIndex == 0) {
            showStartMessage();
        }

        if (currentWaveIndex >= waves.size()) {
            System.out.println("Toutes les vagues ont été complétées !");
            return;
        }

        activeEnemies = new ArrayList<>(waves.get(currentWaveIndex));

        currentWaveIndex++;

        waveTimeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> spawnEnemies(activeEnemies)));
        waveTimeline.setCycleCount(activeEnemies.size());
        waveTimeline.play();
    }

    private void spawnEnemies(List<Enemies> currentWave) {
        if (!currentWave.isEmpty()) {
            Enemies enemy = currentWave.removeFirst();

            activeEnemies.add(enemy);
            gamePane.getChildren().add(enemy);
        }
    }

    public void update() {
        Iterator<Enemies> iterator = activeEnemies.iterator();

        while (iterator.hasNext()) {
            Enemies enemy = iterator.next();
            enemy.update();

            if (enemy.hasReachedFinalWaypoint()) {
                showGameOver();
                return;
            }

            if (enemy.isDefeated()) {
                iterator.remove();
                gamePane.getChildren().remove(enemy);
            }
        }

        // Si tous les ennemis de la vague sont éliminés, lancer la prochaine vague
        if (activeEnemies.isEmpty() && currentWaveIndex < waves.size()) {
            startWave();
        }
    }

    private void showGameOver() {
        gameOverText.setVisible(true);
        gameOverText.setX((gamePane.getWidth() - gameOverText.getBoundsInLocal().getWidth()) / 2);
        gameOverText.setY((gamePane.getHeight() - gameOverText.getBoundsInLocal().getHeight()) / 2);
    }

    private void showStartMessage() {
        Text startMessage = new Text("LET'S GOOOOO !");
        startMessage.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        startMessage.setFill(Color.RED);
        startMessage.setTextAlignment(TextAlignment.CENTER);

        gamePane.getChildren().add(startMessage);

        gamePane.widthProperty().addListener((observable, oldValue, newValue) -> {
            startMessage.setX((newValue.doubleValue() - startMessage.getBoundsInLocal().getWidth()) / 2);
        });

        gamePane.heightProperty().addListener((observable, oldValue, newValue) -> {
            startMessage.setY((newValue.doubleValue() - startMessage.getBoundsInLocal().getHeight()) / 2);
        });

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), startMessage);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(2.5);
        scaleTransition.setToY(2.5);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), startMessage);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        scaleTransition.play();
        fadeTransition.play();

        fadeTransition.setOnFinished(e -> gamePane.getChildren().remove(startMessage));
    }


    private void setupGameOverText() {
        gameOverText = new Text("YOU LOST");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        gameOverText.setFill(Color.RED);
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        gameOverText.setVisible(false);
        gamePane.getChildren().add(gameOverText);
    }

    public List<Enemies> getActiveEnemies() {
        return activeEnemies;
    }

    public void addTower(Towers tower) {
        activeTowers.add(tower);

        for (Enemies enemy : getActiveEnemies()) {
            enemy.addObserver(tower);
        }

        System.out.println("Tour ajoutée et enregistrée comme observateur pour les ennemis actifs.");
    }
}
