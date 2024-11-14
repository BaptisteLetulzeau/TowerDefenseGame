package controllers;

import entities.enemies.Dwarf;
import entities.enemies.Enemies;
import entities.enemies.Gnom;
import entities.enemies.Troll;
import game.Path;
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
    private final List<Enemies> activeEnemies;
    private final List<List<Enemies>> waves;
    private final Path path;
    private int currentWaveIndex = 0;
    private Text gameOverText;
    private Timeline waveTimeline;

    private int playerHealth;
    private int playerMoney;
    private int playerScore;

    public GameController(Pane gamePane) {
        this.gamePane = gamePane;
        this.activeEnemies = new ArrayList<>();
        this.path = new Path();
        this.playerHealth = 10;
        this.playerMoney = 100;
        this.playerScore = 0;

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

        List<Enemies> currentWave = new ArrayList<>(waves.get(currentWaveIndex));
        activeEnemies.addAll(currentWave);

        currentWaveIndex++;

        waveTimeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> spawnEnemies(currentWave)));
        waveTimeline.setCycleCount(currentWave.size());
        waveTimeline.play();
    }

    private void spawnEnemies(List<Enemies> currentWave) {
        if (!currentWave.isEmpty()) {
            Enemies enemy = currentWave.remove(0);
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

        Timeline hideMessageTimeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            gamePane.getChildren().remove(startMessage);
        }));
        hideMessageTimeline.setCycleCount(1);
        hideMessageTimeline.play();
    }

    private void setupGameOverText() {
        gameOverText = new Text("YOU LOST !");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        gameOverText.setFill(Color.RED);
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        gameOverText.setVisible(false);
        gamePane.getChildren().add(gameOverText);
    }

    private void decreasePlayerHealth(int amount) {
        playerHealth -= amount;
        if (playerHealth < 0) playerHealth = 0;
    }

    private void increasePlayerMoney(int amount) {
        playerMoney += amount;
    }

    private void decreasePlayerMoney(int amount) {
        playerMoney -= amount;
    }

    private void increasePlayerScore(int amount) {
        playerScore += amount;
    }

    public int getPlayerHealth() {
        return playerHealth;
    }

    public int getPlayerMoney() {
        return playerMoney;
    }

    public int getPlayerScore() {
        return playerScore;
    }
}