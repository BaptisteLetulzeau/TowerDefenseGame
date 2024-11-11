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
    protected int currentWaypointIndex = 0;
    protected double speed = 2.0;
    private List<Observer> observers = new ArrayList<>();

    public Enemies(String imagePath, List<Point2D> waypoints) {
        this.waypoints = waypoints;
        setImage(new Image(imagePath));
        setFitWidth(100);
        setFitHeight(100);

        // Position initiale : premier waypoint
        if (!waypoints.isEmpty()) {
            setX(waypoints.getFirst().getX());
            setY(waypoints.getFirst().getY());
        }
    }

    public void move() {
        if (currentWaypointIndex >= waypoints.size()) {
            return; // L'ennemi a atteint la fin du chemin
        }

        Point2D target = waypoints.get(currentWaypointIndex);
        double deltaX = target.getX() - getX();
        double deltaY = target.getY() - getY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Déplacement vers le prochain waypoint
        if (distance > speed) {
            double moveX = (deltaX / distance) * speed;
            double moveY = (deltaY / distance) * speed;
            setX(getX() + moveX);
            setY(getY() + moveY);
        }
        else {
            // Atteindre le waypoint
            setX(target.getX());
            setY(target.getY());
            currentWaypointIndex++;
        }

        notifyObservers();
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
}
