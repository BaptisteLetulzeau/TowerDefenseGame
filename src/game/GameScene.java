package game;

import controllers.GameController;
import entities.towers.ArrowTower;
import entities.towers.KnightMan;
import entities.towers.Towers;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox; // Import du HBox
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameScene {

    private final Pane gamePane;
    private final ComboBox<String> towerTypeComboBox;
    private final VBox rootLayout;
    private final List<ImageView> towerSpots;
    private final GameController gameController;
    private Timeline gameLoop;
    private final Image towerSpotImage = new Image("/assets/images/towers/Tower_Purple.png");

    private Label healthLabel;
    private Label moneyLabel;
    private Label scoreLabel;
    private HBox playerInfoBox; // Déclaration du HBox pour les labels

    public GameScene() {
        gamePane = new Pane();
        towerTypeComboBox = new ComboBox<>();
        towerSpots = new ArrayList<>();

        setupComboBox();
        setupBackground();
        setupTowerSpots();

        gameController = new GameController(gamePane);

        setupPlayerInfoLabels(); // Initialiser les labels
        updatePlayerInfoLabels(); // Mettre à jour les labels avec les valeurs initiales

        // Ajouter le HBox des labels au rootLayout
        rootLayout = new VBox(towerTypeComboBox, playerInfoBox, gamePane);

        setupGameLoop();
    }

    private void setupGameLoop() {
        gameLoop = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            gameController.update();
            updatePlayerInfoLabels(); // Mettre à jour les labels pendant le jeu
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    public VBox getRootLayout() {
        return rootLayout;
    }

    private void setupComboBox() {
        towerTypeComboBox.getItems().addAll("ArrowTower", "KnightMan");
        towerTypeComboBox.setValue("ArrowTower");
    }

    private void setupBackground() {
        Image backgroundImage = new Image("/assets/images/background/background.png");

        ImageView backgroundImageView = new ImageView(backgroundImage);

        backgroundImageView.setFitWidth(1400);
        backgroundImageView.setFitHeight(1000);

        gamePane.getChildren().addFirst(backgroundImageView);
    }

    private void setupTowerSpots() {
        addTowerSpot(150, 240, "ArrowTower");
        addTowerSpot(330, 700, "ArrowTower");
        addTowerSpot(600, 220, "ArrowTower");
        addTowerSpot(950, 660, "ArrowTower");
        addTowerSpot(1200, 220, "ArrowTower");
    }

    private void addTowerSpot(double x, double y, String defaultTowerType) {
        ImageView spotImageView = new ImageView(towerSpotImage);
        spotImageView.setFitWidth(150);
        spotImageView.setFitHeight(150);
        spotImageView.setX(x - 75);
        spotImageView.setY(y - 75);

        spotImageView.setOnMouseClicked(event -> {
            String currentSelectedTowerType = towerTypeComboBox.getValue();
            addTower(x, y, currentSelectedTowerType);
        });

        gamePane.getChildren().add(spotImageView);
        towerSpots.add(spotImageView);
    }

    private void addTower(double x, double y, String towerType) {
        Towers tower;
        switch (towerType) {
            case "ArrowTower":
                tower = new ArrowTower(x - 95, y - 140);
                break;
            case "KnightMan":
                tower = new KnightMan(x - 95, y - 140);
                break;
            default:
                throw new IllegalArgumentException("Type de tour inconnu : " + towerType);
        }

        gamePane.getChildren().add(tower);
        System.out.println("Tour " + towerType + " placée à : " + x + ", " + y);
    }

    private void setupPlayerInfoLabels() {
        healthLabel = new Label("Points de vie : " + gameController.getPlayerHealth());
        moneyLabel = new Label("Argent : " + gameController.getPlayerMoney());
        scoreLabel = new Label("Score : " + gameController.getPlayerScore());

        healthLabel.setStyle("-fx-font-size: 16px;");
        moneyLabel.setStyle("-fx-font-size: 16px;");
        scoreLabel.setStyle("-fx-font-size: 16px;");

        playerInfoBox = new HBox(10, healthLabel, moneyLabel, scoreLabel);
        playerInfoBox.setStyle("-fx-padding: 10px;"); // Optionnel : ajouter du padding
    }

    private void updatePlayerInfoLabels() {
        healthLabel.setText("Points de vie : " + gameController.getPlayerHealth());
        moneyLabel.setText("Argent : " + gameController.getPlayerMoney());
        scoreLabel.setText("Score : " + gameController.getPlayerScore());
    }
}
