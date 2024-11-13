package game;

import controllers.GameController;
import entities.towers.ArrowTower;
import entities.towers.FlailMan;
import entities.towers.Towers;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameScene {

    private final Pane gamePane;
    private final ComboBox<String> towerTypeComboBox;
    private final VBox rootLayout;
    private final List<Rectangle> towerSpots;
    private final Map<Rectangle, Towers> towerMap; // Map pour suivre les tours placées par emplacement
    private final GameController gameController;

    public GameScene() {
        gamePane = new Pane();
        towerTypeComboBox = new ComboBox<>();
        towerSpots = new ArrayList<>();
        towerMap = new HashMap<>(); // Initialiser la map

        setupComboBox();
        setupBackground();
        setupTowerSpots();
        setupMouseClickListener();

        rootLayout = new VBox(towerTypeComboBox, gamePane);
        gameController = new GameController(rootLayout);
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
        towerMap.put(spot, null); // Ajouter chaque emplacement avec une valeur initiale null
    }

    private void setupMouseClickListener() {
        gamePane.setOnMouseClicked((MouseEvent event) -> {
            double clickX = event.getX();
            double clickY = event.getY();
            String selectedTowerType = towerTypeComboBox.getValue();

            for (Rectangle spot : towerSpots) {
                if (spot.contains(clickX, clickY)) {
                    // Vérifie si l'emplacement est déjà occupé
                    if (towerMap.get(spot) != null) {
                        System.out.println("Emplacement déjà occupé. Impossible d'ajouter une autre tour ici.");
                        return;
                    }
                    // Ajouter une tour si l'emplacement est libre
                    addTower(spot, selectedTowerType);
                    return;
                }
            }

            System.out.println("Clic hors d'un emplacement valide");
        });
    }

    private void addTower(Rectangle spot, String towerType) {
        Towers tower;
        double x = spot.getX() + spot.getWidth() / 2;
        double y = spot.getY() + spot.getHeight() / 2;

        switch (towerType) {
            case "ArrowTower":
                tower = new ArrowTower(x - 75, y - 75);
                break;
            case "FlailMan":
                tower = new FlailMan(x - 75, y - 75);
                break;
            default:
                throw new IllegalArgumentException("Type de tour inconnu : " + towerType);
        }

        gamePane.getChildren().add(tower);
        towerMap.put(spot, tower); // Marquer l'emplacement comme occupé avec cette tour
        System.out.println("Tour placée à : " + x + ", " + y);
    }
}
