package game;

import controllers.GameController;
import entities.towers.ArrowTower;
import entities.towers.FlailMan;
import entities.towers.Towers;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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

    public GameScene() {
        gamePane = new Pane();
        towerTypeComboBox = new ComboBox<>();
        towerSpots = new ArrayList<>();

        setupComboBox();
        setupBackground();
        setupTowerSpots();
        setupMouseClickListener();

        rootLayout = new VBox(towerTypeComboBox, gamePane);
        gameController = new GameController(gamePane);

        setupGameLoop();
    }

    private void setupGameLoop() {
        gameLoop = new Timeline(new KeyFrame(Duration.millis(70), e -> {
            gameController.update();
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    public VBox getRootLayout() {
        return rootLayout;
    }

    private void setupComboBox() {
        towerTypeComboBox.getItems().addAll("ArrowTower", "FlailMan");
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
            addTower(x, y, defaultTowerType);
        });

        gamePane.getChildren().add(spotImageView);
        towerSpots.add(spotImageView);
    }

    private void setupMouseClickListener() {
        gamePane.setOnMouseClicked((MouseEvent event) -> {
            double clickX = event.getX();
            double clickY = event.getY();
            String selectedTowerType = towerTypeComboBox.getValue();

//            for (Rectangle spot : towerSpots) {
//                if (spot.contains(clickX, clickY)) {
//                    addTower(spot.getX() + spot.getWidth() / 2, spot.getY() + spot.getHeight() / 2, selectedTowerType);
//                    return;
//                }
//            }

            System.out.println("Clic hors d'un emplacement valide");
        });
    }

    private void addTower(double x, double y, String towerType) {
        Towers tower;
        switch (towerType) {
            case "ArrowTower":
                tower = new ArrowTower(x - 95, y - 140);
                break;
            case "FlailMan":
                tower = new FlailMan(x - 95, y - 140);
                break;
            default:
                throw new IllegalArgumentException("Type de tour inconnu : " + towerType);
        }

        gamePane.getChildren().add(tower);
        System.out.println("Tour placée à : " + x + ", " + y);
    }
}