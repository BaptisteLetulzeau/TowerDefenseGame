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
import javafx.scene.shape.Rectangle;
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
import java.util.Random;

public class GameController {
    private final Pane gamePane;
    private List<Enemies> activeEnemies;
    private final List<List<Enemies>> waves;
    private final Path path;
    private int currentWaveIndex = 0;
    private Text gameOverText;
    private Text gameWonText;
    private Timeline waveTimeline;
    private final List<Towers> activeTowers = new ArrayList<>();
    private int playerLives = 3;
    private Rectangle healthBarBackground;
    private Rectangle healthBar;
    private Text healthText;
    private Text moneyText;
    private int money = 50;

    public GameController(Pane gamePane) {
        this.gamePane = gamePane;
        this.activeEnemies = new ArrayList<>();
        this.path = new Path();

        this.waves = createWaves();

        setupHealthBar();
        setupGameOverText();
        setupGameWonText();
        setupMoney();

        startWave();
    }

    public boolean canPurchase(String towerType) {
        int towerCost = getTowerCost(towerType);
        return money >= towerCost;
    }

    public int getTowerCost(String towerType) {
        switch (towerType) {
            case "ArrowTower":
                return 50;
            case "KnightMan":
                return 100;
            default:
                return Integer.MAX_VALUE;
        }
    }

    public void deductMoney(int money) {
        this.money -= money;
        updateMoney();
    }

    private void setupMoney() {
        moneyText = new Text("Money: $" + money);
        moneyText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        moneyText.setFill(Color.WHITE);
        moneyText.setX(20);
        moneyText.setY(55);

        gamePane.getChildren().add(moneyText);
    }

    private void setupHealthBar() {
        healthText = new Text("Health Life:");
        healthText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        healthText.setFill(Color.WHITE);
        healthText.setX(20);
        healthText.setY(15);

        healthBarBackground = new Rectangle(200, 20, Color.DARKGRAY);
        healthBarBackground.setX(20);
        healthBarBackground.setY(20);

        healthBar = new Rectangle(200, 20, Color.LIMEGREEN);
        healthBar.setX(20);
        healthBar.setY(20);

        gamePane.getChildren().addAll(healthBarBackground, healthBar, healthText);
    }

    private void updateHealthBar() {
        double maxLives = 3;
        double barWidth = healthBarBackground.getWidth();
        double newWidth = (playerLives / maxLives) * barWidth;

        healthBar.setWidth(newWidth);

        if (playerLives == 2) {
            healthBar.setFill(Color.ORANGE);
        }
        else if (playerLives == 1) {
            healthBar.setFill(Color.RED);
        }

        animateHealthBar();
        animateScreenShake();
    }

    private void animateHealthBar() {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), healthBar);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setAutoReverse(true);

        scaleTransition.setOnFinished(event -> {
            healthBar.setScaleX(1.0);
            healthBar.setScaleY(1.0);
        });

        scaleTransition.play();
    }

    private void animateScreenShake() {
        double amplitude = 10;
        Duration duration = Duration.millis(50);

        Timeline timeline = new Timeline(
                new KeyFrame(duration, e -> gamePane.setTranslateX(amplitude)),
                new KeyFrame(duration.multiply(2), e -> gamePane.setTranslateX(-amplitude)),
                new KeyFrame(duration.multiply(3), e -> gamePane.setTranslateX(amplitude / 2)),
                new KeyFrame(duration.multiply(4), e -> gamePane.setTranslateX(0))
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    private List<List<Enemies>> createWaves() {
        List<List<Enemies>> waves = new ArrayList<>();

        List<Enemies> wave1 = new ArrayList<>(List.of(new Gnom(path.getWaypoints(), -149, 420), new Troll(path.getWaypoints(), -149, 420)));
        List<Enemies> wave2 = new ArrayList<>(List.of(new Dwarf(path.getWaypoints(), -149, 420), new Gnom(path.getWaypoints(), -149, 420), new Troll(path.getWaypoints(), -149, 420)));
        List<Enemies> wave3 = new ArrayList<>(List.of(new Dwarf(path.getWaypoints(), -149, 420), new Gnom(path.getWaypoints(), -149, 420), new Troll(path.getWaypoints(), -149, 420), new Dwarf(path.getWaypoints(), -149, 420)));
        List<Enemies> wave4 = new ArrayList<>(List.of(new Dwarf(path.getWaypoints(), -149, 420), new Gnom(path.getWaypoints(), -149, 420), new Dwarf(path.getWaypoints(), -149, 420), new Troll(path.getWaypoints(), -149, 420), new Dwarf(path.getWaypoints(), -149, 420), new Gnom(path.getWaypoints(), -149, 420), new Troll(path.getWaypoints(), -149, 420)));

        waves.add(wave1);
        waves.add(wave2);
        waves.add(wave3);
        waves.add(wave4);

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
        Random random = new Random();
        double spawnRadius = 50;

        if (!currentWave.isEmpty()) {
            Enemies enemy = currentWave.removeFirst();

            double randomOffsetX = (random.nextDouble() - 0.5) * 2 * spawnRadius;
            double randomOffsetY = (random.nextDouble() - 0.5) * 2 * spawnRadius;

            double startX = enemy.getLayoutX() + randomOffsetX;
            double startY = enemy.getLayoutY() + randomOffsetY;

            enemy.setLayoutX(startX);
            enemy.setLayoutY(startY);

            activeEnemies.add(enemy);
            gamePane.getChildren().add(enemy);
        }
    }

    public void update() {
        Iterator<Enemies> iterator = activeEnemies.iterator();

        while (iterator.hasNext()) {
            Enemies enemy = iterator.next();
            enemy.update();

            if (enemy.hasReachedFinalWaypoint() && playerLives >= 0) {
                playerLives--;
                updateHealthBar();
                System.out.println("Un ennemi a atteint la fin ! Points de vie restants : " + playerLives);

                iterator.remove();
                gamePane.getChildren().remove(enemy);

                if (playerLives == 1) {
                    showLastOneMessage();
                    return;
                }

                if (playerLives <= 0) {
                    showGameOver();
                    return;
                }
            }

            if (enemy.isDefeated()) {
                iterator.remove();
                gamePane.getChildren().remove(enemy);

                money += 25;
                updateMoney();
            }
        }

        // Si tous les ennemis de la vague sont éliminés, lancer la prochaine vague
        if (activeEnemies.isEmpty() && currentWaveIndex < waves.size() && playerLives > 0) {
            startWave();
        }
        else if (currentWaveIndex == waves.size() && activeEnemies.isEmpty()) {
            showGameWon();
            waveTimeline.stop();
        }
    }

    private void updateMoney() {
        moneyText.setText("Money: $" + money);
    }

    private void showGameOver() {
        gameOverText.setVisible(true);
        gameOverText.setX((gamePane.getWidth() - gameOverText.getBoundsInLocal().getWidth()) / 2);
        gameOverText.setY((gamePane.getHeight() - gameOverText.getBoundsInLocal().getHeight()) / 2);
    }

    private void showGameWon() {
        gameWonText.setVisible(true);
        gameWonText.setX((gamePane.getWidth() - gameWonText.getBoundsInLocal().getWidth()) / 2);
        gameWonText.setY((gamePane.getHeight() - gameWonText.getBoundsInLocal().getHeight()) / 2);
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

    private void showLastOneMessage() {
        Text oneLast = new Text("One life remaining !");
        oneLast.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        oneLast.setFill(Color.RED);
        oneLast.setTextAlignment(TextAlignment.CENTER);

        gamePane.getChildren().add(oneLast);

        // Centrer le texte initialement
        oneLast.setX((gamePane.getWidth() - oneLast.getBoundsInLocal().getWidth()) / 2);
        oneLast.setY((gamePane.getHeight() - oneLast.getBoundsInLocal().getHeight()) / 2);

        // Mettre à jour la position en cas de redimensionnement
        gamePane.widthProperty().addListener((observable, oldValue, newValue) -> {
            oneLast.setX((newValue.doubleValue() - oneLast.getBoundsInLocal().getWidth()) / 2);
        });

        gamePane.heightProperty().addListener((observable, oldValue, newValue) -> {
            oneLast.setY((newValue.doubleValue() - oneLast.getBoundsInLocal().getHeight()) / 2);
        });

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), oneLast);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(2.5);
        scaleTransition.setToY(2.5);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), oneLast);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        scaleTransition.play();
        fadeTransition.play();

        fadeTransition.setOnFinished(e -> gamePane.getChildren().remove(oneLast));
    }

    private void setupGameOverText() {
        gameOverText = new Text("YOU LOST");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        gameOverText.setFill(Color.RED);
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        gameOverText.setVisible(false);
        gamePane.getChildren().add(gameOverText);
    }

    private void setupGameWonText() {
        gameWonText = new Text("YOU WON");
        gameWonText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        gameWonText.setFill(Color.RED);
        gameWonText.setTextAlignment(TextAlignment.CENTER);
        gameWonText.setVisible(false);
        gamePane.getChildren().add(gameWonText);
    }

    public List<Enemies> getActiveEnemies() {
        return activeEnemies;
    }

    public void addTower(Towers tower) {
        activeTowers.add(tower);

        for (Enemies enemy : getActiveEnemies()) {
            enemy.addObserver(tower);
        }
    }
}
