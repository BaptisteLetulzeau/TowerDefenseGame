package entities.towers;

import javafx.scene.layout.Pane;
import entities.enemies.Enemies;

public class ArrowTower extends Towers implements Observer {

    private Enemies target;

    public ArrowTower(double x, double y) {
        super(x, y, "/assets/images/towers/clement.JPG");
    }

    @Override
    public void update(Enemies enemy) {
        if (isInRange(enemy)) {
            shoot(enemy);
        }
    }

    private boolean isInRange(Enemies enemy) {
        double distance = Math.sqrt(Math.pow(enemy.getX() - getX(), 2) + Math.pow(enemy.getY() - getY(), 2));
        return distance < 200;
    }

    private void shoot(Enemies enemy) {
        System.out.println("ArrowTower attaque l'ennemi !");
    }
}