package entities.towers;

import controllers.GameController;
import entities.enemies.Enemies;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class Towers extends ImageView implements Observer  {
    protected double x;
    protected double y;
    private final double ATTACK_RANGE;
    private final int POWER;
    private Timeline shootingTimeline;

    public Towers(double x, double y, String imagePath, double range, int power) {
        this.x = x;
        this.y = y;
        this.ATTACK_RANGE = range;
        this.POWER = power;

        setImage(new Image(imagePath));

        setFitWidth(150);
        setFitHeight(150);

        setX(x);
        setY(y);
    }
    
    public void startShootingArrow(GameController gameController) {
        shootingTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (!gameController.hasWon) {
                Enemies weakestEnemy = findWeakestEnemyInRange(gameController);
                System.out.println(weakestEnemy);
                if (weakestEnemy != null) {
                    attackEnemyInRange(weakestEnemy);
                }
            }
            else {
                for (Enemies enemy : gameController.getActiveEnemies()) {
                   if (isEnemyInRange(enemy)) {
                        attackEnemyInRange(enemy);
                        break;
                   }
                }
            }
        }));
        shootingTimeline.setCycleCount(Timeline.INDEFINITE);
        shootingTimeline.play();
    }

    public void startShootingKnight(GameController gameController) {
        shootingTimeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            if (!gameController.hasWon) {
                Enemies strongestEnemy = findStrongestEnemyInRange(gameController);
                System.out.println(strongestEnemy);
                if (strongestEnemy != null) {
                    attackEnemyInRange(strongestEnemy);
                }
            }
            else {
                for (Enemies enemy : gameController.getActiveEnemies()) {
                    if (isEnemyInRange(enemy)) {
                        attackEnemyInRange(enemy);
                        break;
                    }
                }
            }
        }));
        shootingTimeline.setCycleCount(Timeline.INDEFINITE);
        shootingTimeline.play();
    }

    public Enemies findWeakestEnemyInRange(GameController gameController) {
        Enemies weakestEnemy = null;
        double lowestHealth = Double.MAX_VALUE;

        for (Enemies enemy : gameController.getActiveEnemies()) {
            if (isEnemyInRange(enemy) && enemy.getHealth() < lowestHealth) {
                lowestHealth = enemy.getHealth();
                weakestEnemy = enemy;
            }
        }

        return weakestEnemy;
    }

    public Enemies findStrongestEnemyInRange(GameController gameController) {
        Enemies strongestEnemy = null;
        double highestHealth = Double.MIN_VALUE;

        for (Enemies enemy : gameController.getActiveEnemies()) {
            if (isEnemyInRange(enemy) && enemy.getHealth() > highestHealth) {
                highestHealth = enemy.getHealth();
                strongestEnemy = enemy;
            }
        }

        return strongestEnemy;
    }

    public boolean isEnemyInRange(Enemies enemy) {
        double dx = enemy.getLayoutX() - this.getX();
        double dy = enemy.getLayoutY() - this.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance <= ATTACK_RANGE;
    }

    public void attackEnemyInRange(Enemies enemy) {
        System.out.println("L'Ennemi est dans la portée !");
        enemy.reduceHealth(POWER);

        if (enemy.isDead()) {
            System.out.println("L'ennemi " + enemy + " est mort par une tour.");
        }
    }
}
