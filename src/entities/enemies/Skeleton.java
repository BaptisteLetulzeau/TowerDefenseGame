package entities.enemies;

import entities.Entity;

public class Skeleton extends Entity {
    /// Allow to an entity to shoot
    @Override
    public boolean Shoot() {
        return true;
    }
}