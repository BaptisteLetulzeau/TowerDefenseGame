package entities.towers;

import entities.enemies.Enemies;

import javafx.geometry.Point2D;

public interface Observer {
    void updatePosition(Point2D position);
}
