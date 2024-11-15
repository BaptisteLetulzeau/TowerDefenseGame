package entities.towers;

import entities.enemies.Enemies;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Point2D;

public abstract class Towers extends ImageView implements Observer  {
    protected double x;
    protected double y;
    private double ATTACK_RANGE = 150;
    private static final double ATTACK_DELAY = 1.0;
    private long lastAttackTime = System.nanoTime();

    public Towers(double x, double y, String imagePath, double range) {
        this.x = x;
        this.y = y;
        this.ATTACK_RANGE = range;

        setImage(new Image(imagePath));

        setFitWidth(150);
        setFitHeight(150);

        setX(x);
        setY(y);
    }

    public Point2D getPosition() {
        return new Point2D(x, y);
    }

    public void updatePosition(Point2D enemyPosition) {
        double distance = this.getPosition().distance(enemyPosition);

        if (distance <= ATTACK_RANGE) {
            long currentTime = System.nanoTime();
            System.out.println("Enemy in range! Position: " + enemyPosition);

            if ((currentTime - lastAttackTime) >= ATTACK_DELAY * 1_000_000_000) {
                attack(enemyPosition);
                lastAttackTime = currentTime;
            }
        }
    }

    private void attack(Point2D enemyPosition) {
        System.out.println("Tour attaque un ennemi à la position : " + enemyPosition);
    }

    public boolean isEnemyInRange(Enemies enemy) {
        double dx = enemy.getLayoutX() - this.getX();
        double dy = enemy.getLayoutY() - this.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance <= ATTACK_RANGE;
    }

    public void onEnemyInRange(Enemies enemy) {
        // Code pour attaquer ou cibler l'ennemi
        System.out.println("Ennemi dans la portée de la tour : " + enemy);
    }
}