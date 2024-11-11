package entities.towers;

import entities.enemies.Enemies;

public interface Observer {
    void update(Enemies enemy);
}
