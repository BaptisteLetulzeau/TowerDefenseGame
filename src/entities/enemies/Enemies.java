package entities.enemies;

import entities.towers.Observer;
import entities.towers.Subject;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;

public abstract class Enemies extends ImageView implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    protected List<Point2D> waypoints;
    private int health;
    private double coordX;
    private double coordY;

    public Enemies(String imagePath, List<Point2D> waypoints, int health) {
        this.waypoints = waypoints;
        this.health = health;

        if (waypoints.isEmpty()){
            waypoints.add(new Point2D(-100, 500));
            waypoints.add(new Point2D(1250, 500));
        }

        setImage(new Image(imagePath));
        setFitWidth(100);
        setFitHeight(100);
    }

    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        System.out.println("Tour mise à jour !");
    }

    public void reduceHealth(int attackRate) {
        System.out.println("Ancienne vie: " + this.health);
        this.health -= attackRate;
        System.out.println("Nouvelle vie: " + this.health);
        if (this.health <= 0) {
            this.health = 0;
        }
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public int getHealth() {
        return health;
    }

    public void saveCurrentPosition() {
        this.coordX = this.getLayoutX();
        this.coordY = this.getLayoutY();
    }

    public void restoreSavedPosition(Enemies enemy) {
        enemy.setLayoutX(this.coordX);
        enemy.setLayoutY(this.coordY);
    }

    public abstract boolean hasReachedFinalWaypoint();
    public abstract void update();
}
