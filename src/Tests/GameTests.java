package Tests;

import entities.enemies.Dwarf;
import entities.towers.ArrowTower;
import controllers.GameController;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    private Pane gamePane;
    private List<Point2D> waypoints;
    private Dwarf enemy;
    private ArrowTower tower;
    private GameController controller;

    @BeforeEach
    public void setUp() {
        gamePane = new Pane();
        waypoints = new ArrayList<>();
        waypoints.add(new Point2D(0, 0.45));
    }

    @Test
    public void testTakeDamage() {
        enemy = new Dwarf(waypoints, -0.1, 0.45, gamePane);

        enemy.reduceHealth(20);

        assertEquals(60, enemy.getHealth(), "La santé de l'ennemi après avoir subi des dégâts est incorrecte.");
        assertFalse(enemy.isDead(), "L'ennemi ne devrait pas être mort.");
    }

    @Test
    public void testEnemyMovement() {
        enemy = new Dwarf(waypoints, -0.1, 0.45, gamePane);

        enemy.move();
        Point2D position = enemy.getPosition();

        assertNotNull(position, "La position de l'ennemi ne devrait pas être null.");
        assertNotEquals(new Point2D(-0.1, 0.45), position, "L'ennemi devrait changer de position après un mouvement.");
    }

    @Test
    public void attackEnemy() {
        tower = new ArrowTower(0.08 - 95, 0.35 - 140);
        enemy = new Dwarf(waypoints, -0.1, 0.45, gamePane);

        boolean isInRange = tower.isEnemyInRange(enemy);
        enemy.reduceHealth(30);

        assertTrue(isInRange, "L'ennemi devrait être dans la portée de la tour.");
        assertEquals(50, enemy.getHealth(), "La santé de l'ennemi après une attaque est incorrecte.");
    }

    @Test
    public void createGameController() {
        controller = new GameController(gamePane);
        tower = new ArrowTower(0.08 - 95, 0.35 - 140);
        String towerType = "ArrowTower";

        controller.createWaves();
        int cost = controller.getTowerCost(towerType);
        controller.addTower(tower);

        assertNotNull(controller.getActiveEnemies(), "Les ennemis actifs ne devraient pas être null.");
        assertEquals(50, cost, "Le coût de la tour 'ArrowTower' est incorrect.");
    }

    @Test
    public void testGameOver() {
        controller = new GameController(gamePane);

        controller.createWaves();
        controller.getActiveEnemies().forEach(enemy -> enemy.reduceHealth(100)); // Tuez tous les ennemis.

        assertTrue(controller.isGameOver(), "Le jeu devrait être terminé après la mort de tous les ennemis.");
    }

    @Test
    public void testCannotAffordTower() {
        controller = new GameController(gamePane);
        controller.deductMoney(controller.getMoney());

        boolean canPurchase = controller.canPurchase("ArrowTower");
        assertFalse(canPurchase, "Le joueur ne devrait pas pouvoir acheter une tour sans argent.");
    }
}