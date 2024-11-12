package entities.towers;

import entities.Entity;
import entities.enemies.Enemies;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public abstract class Towers extends ImageView  {
    protected double x;
    protected double y;
    private AttackStrategy attackStrategy;

    public Towers(double x, double y, String imagePath) {
        this.x = x;
        this.y = y;

        setImage(new Image(imagePath));

        setFitWidth(150);
        setFitHeight(150);

        setX(x);
        setY(y);
    }

    public void setAttackStrategy(AttackStrategy strategy) {
        this.attackStrategy = strategy;
    }

    public void performAttack(List<Enemies> enemies) {
        if (attackStrategy != null) {
            attackStrategy.attack(this, enemies);
        }
    }
}