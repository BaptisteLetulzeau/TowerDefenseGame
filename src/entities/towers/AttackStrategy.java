package entities.towers;

import entities.enemies.Enemies;
import java.util.List;

public interface AttackStrategy {
    void attack(Towers tower, List<Enemies> enemies);
}