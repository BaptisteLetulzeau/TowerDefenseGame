package entities.towers;

import entities.Entity;

public class ArrowTour extends Entity {
    /// Allow to an entity to shoot
    @Override
    public boolean Shoot() {
        return true;
    }
}