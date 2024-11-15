package entities.enemies;

import entities.towers.Observer;
import entities.towers.Subject;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

public abstract class Enemies extends ImageView implements Subject {
    protected List<Point2D> waypoints;
    private List<Observer> observers = new ArrayList<>();
    private double health;

    public Enemies(String imagePath, List<Point2D> waypoints) {
        this.waypoints = waypoints;
        this.health = 100;
        setImage(new Image(imagePath));
        setFitWidth(100);
        setFitHeight(100);

        if (!waypoints.isEmpty()) {
            setX(waypoints.getFirst().getX());
            setY(waypoints.getFirst().getY());
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public void takeDamage(double damage) {
        this.health -= damage;
    }

    public boolean isDefeated() {
        return this.health <= 0;
    }

    public abstract void update();
    public abstract boolean hasReachedFinalWaypoint();
}
