package game;

import controllers.GameController;
import entities.towers.ArrowTower;
import entities.towers.KnightMan;
import entities.towers.Towers;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
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
    private MediaPlayer mediaPlayer;
    private final Image towerSpotImage = new Image("/assets/images/towers/Tower_Purple.png");
    private ImageView backgroundImageView;
    private Stage stage;

    public GameScene(Stage stage) {
        this.stage = stage;
        gamePane = new Pane();
        towerTypeComboBox = new ComboBox<>();
        towerSpots = new ArrayList<>();
        rootLayout = new VBox(10, towerTypeComboBox, gamePane);

        setupBackgroundMusic();
        setupComboBox();
        setupBackground();
        setupTowerSpots();
        setupGameLoop();

        gameController = new GameController(gamePane);

        makeSceneResponsive();
    }

    private void setupGameLoop() {
        gameLoop = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            gameController.update();
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private void setupBackgroundMusic() {
        String musicFilePath = getClass().getResource("/assets/sounds/music.mp3").toExternalForm();
        Media backgroundMusic = new Media(musicFilePath);
        mediaPlayer = new MediaPlayer(backgroundMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();
    }

    public void showSettingsMenu() {
        gameController.saveEnemyPositions();

        Scene mainScene = rootLayout.getScene();
        SettingsMenu settingsMenu = new SettingsMenu(mainScene, mediaPlayer, gameController);
        settingsMenu.display();
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

        backgroundImageView = new ImageView(backgroundImage);

        backgroundImageView.setFitWidth(1300);
        backgroundImageView.setFitHeight(1000);

        gamePane.getChildren().addFirst(backgroundImageView);
    }

    private void setupTowerSpots() {
        addTowerSpot(0.08, 0.35, "ArrowTower");
        addTowerSpot(0.27, 0.7, "ArrowTower");
        addTowerSpot(0.5, 0.52, "ArrowTower");
        addTowerSpot(0.85, 0.35, "ArrowTower");
    }

    private void addTowerSpot(double x, double y, String defaultTowerType) {
        ImageView spotImageView = new ImageView(towerSpotImage);
        spotImageView.setFitWidth(150);
        spotImageView.setFitHeight(150);

        spotImageView.layoutXProperty().bind(gamePane.widthProperty().multiply(x).subtract(75));
        spotImageView.layoutYProperty().bind(gamePane.heightProperty().multiply(y).subtract(75));

        spotImageView.setOnMouseClicked(event -> {
            if (gameController.isGameOver()) {
                return;
            }

            String currentSelectedTowerType = towerTypeComboBox.getValue();
            addTower(spotImageView.getLayoutX() + 75, spotImageView.getLayoutY() + 75, currentSelectedTowerType);
        });

        gamePane.getChildren().add(spotImageView);
        towerSpots.add(spotImageView);
    }

    private void addTower(double x, double y, String towerType) {
        if (!gameController.canPurchase(towerType)) {
            System.out.println("Pas assez d'argent pour acheter " + towerType + " !");
            return;
        }

        Towers tower;
        switch (towerType) {
            case "ArrowTower":
                tower = new ArrowTower(x - 95, y - 140);
                gameController.addTower(tower);
                break;
            case "KnightMan":
                tower = new KnightMan(x - 95, y - 50);
                gameController.addTower(tower);
                break;
            default:
                throw new IllegalArgumentException("Type de tour inconnu : " + towerType);
        }

        gamePane.getChildren().add(tower);

        int towerCost = gameController.getTowerCost(towerType);
        gameController.deductMoney(towerCost);

        if (towerType.equals("ArrowTower")) {
            tower.startShootingArrow(gameController);
        }
        else {
            tower.startShootingKnight(gameController);
        }

        System.out.println(towerType + " placé à : " + x + ", " + y);
    }

    private void makeSceneResponsive() {
        // Ajuster la taille de l'arrière-plan selon la taille de la fenêtre.
        backgroundImageView.fitWidthProperty().bind(stage.widthProperty());
        backgroundImageView.fitHeightProperty().bind(stage.heightProperty());
    }
}
