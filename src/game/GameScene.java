package game;

import entities.towers.ArrowTower;
import entities.towers.FlailMan;
import entities.towers.Towers;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GameScene {

    private final Pane gamePane;
    private final ComboBox<String> towerTypeComboBox;
    private final VBox rootLayout;
    private final List<Rectangle> towerSpots;
    private final Label healthLabel;
    private final Label moneyLabel;
    private final Player player;

    public GameScene() {

        player = new Player(50, 100);

        healthLabel = new Label();
        moneyLabel = new Label();

        gamePane = new Pane();
        towerTypeComboBox = new ComboBox<>();
        towerSpots = new ArrayList<>();

        setupComboBox();
        setupBackground();
        setupTowerSpots();
        setupMouseClickListener();

        rootLayout = new VBox(towerTypeComboBox, gamePane);
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
        addTowerSpot(150, 240, 150, 150);
        addTowerSpot(330, 700, 150, 150);
        addTowerSpot(600, 220, 150, 150);
        addTowerSpot(950, 660, 150, 150);
        addTowerSpot(1200, 220, 150, 150);
    }

    private void addTowerSpot(double x, double y, double width, double height) {
        Rectangle spot = new Rectangle(x - width / 2, y - height / 2, width, height);
        spot.setFill(Color.TRANSPARENT);
        spot.setStroke(Color.BLACK);
        gamePane.getChildren().add(spot);
        towerSpots.add(spot);
    }

    private void setupMouseClickListener() {
        gamePane.setOnMouseClicked((MouseEvent event) -> {
            double clickX = event.getX();
            double clickY = event.getY();
            String selectedTowerType = towerTypeComboBox.getValue();

            for (Rectangle spot : towerSpots) {
                if (spot.contains(clickX, clickY)) {
                    addTower(spot.getX() + spot.getWidth() / 2, spot.getY() + spot.getHeight() / 2, selectedTowerType);
                    return;
                }
            }

            System.out.println("Clic hors d'un emplacement valide");
        });
    }

    public VBox getRootLayout() {
        return rootLayout;
    }

    private void addTower(double x, double y, String towerType) {
        int towerCost = 0;
        Towers tower = switch (towerType) {
            case "ArrowTower" -> new ArrowTower(x - 75, y - 75);
            case "FlailMan" -> new FlailMan(x - 75, y - 75);
            default -> throw new IllegalArgumentException("Type de tour inconnu : " + towerType);
        };

        gamePane.getChildren().add(tower);
        System.out.println("Tour placée à : " + x + ", " + y);
    }

    private void updatePlayerInfoLabels() {
        healthLabel.setText("Points de vie : " + player.getHealthPoints());
        moneyLabel.setText("Argent : " + player.getMoney());
    }

    public void playerTakeDamage(int damage) {
        player.decreaseHealth(damage);
        updatePlayerInfoLabels();

        if (player.getHealthPoints() <= 0) {
            System.out.println("Game Over!");
        }
    }
}