package entities.enemies;

import entities.towers.Observer;
import entities.towers.Subject;
import entities.towers.Towers;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public abstract class Enemies extends ImageView implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    protected List<Point2D> waypoints;
    private double health;

    public Enemies(String imagePath, List<Point2D> waypoints) {
        this.waypoints = waypoints;
        this.health = 100;

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
            System.out.println("Observer ajouté : " + observer + " pour l'ennemi : " + this);
        }
        else {
            System.out.println("Observer déjà enregistré : " + observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            if (observer instanceof Towers) {
                Towers tower = (Towers) observer;
                if (tower.isEnemyInRange(this)) {
                    tower.onEnemyInRange(this);
                }
            }
        }
    }

    public Point2D getPosition() {
        return new Point2D(getLayoutX(), getLayoutY());
    }

    public boolean isDefeated() {
        return this.health <= 0;
    }

    public abstract boolean hasReachedFinalWaypoint();
    public abstract void update();
}
