package game;

import entities.towers.ArrowTower;
import entities.towers.FlailMan;
import entities.towers.Towers;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameScene {

    private final Pane gamePane;
    private final ComboBox<String> towerTypeComboBox;
    private final VBox rootLayout;

    public GameScene() {
        gamePane = new Pane();
        towerTypeComboBox = new ComboBox<>();
        setupComboBox();
        setupBackground();
        setupMouseClickListener();

        // Combiner le ComboBox et le gamePane dans un VBox
        rootLayout = new VBox(towerTypeComboBox, gamePane);
    }

    private void setupComboBox() {
        // Ajouter les options de types de tours au ComboBox
        towerTypeComboBox.getItems().addAll("ArrowTower", "FlailMan");
        towerTypeComboBox.setValue("ArrowTower"); // Option par défaut
    }

    private void setupBackground() {
        // Charger et définir l'image de fond ici
    }

    private void setupMouseClickListener() {
        gamePane.setOnMouseClicked((MouseEvent event) -> {
            String selectedTowerType = towerTypeComboBox.getValue();
            addTower(gamePane, event.getX(), event.getY(), selectedTowerType);
        });
    }

    public Pane getGamePane() {
        return gamePane;
    }

    public VBox getRootLayout() {
        return rootLayout;
    }

    private void addTower(Pane gamePane, double x, double y, String towerType) {
        Towers tower;
        switch (towerType) {
            case "ArrowTower":
                tower = new ArrowTower(x, y);
                break;
            case "FlailMan":
                tower = new FlailMan(x, y);
                break;
            default:
                throw new IllegalArgumentException("Type de tour inconnu : " + towerType);
        }

        gamePane.getChildren().add(tower);
    }
}
