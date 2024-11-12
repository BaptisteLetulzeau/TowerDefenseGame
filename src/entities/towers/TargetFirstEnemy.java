package entities.towers;

import entities.enemies.Enemies;
import java.util.List;

public class TargetFirstEnemy implements AttackStrategy {
    @Override
    public void attack(Towers tower, List<Enemies> enemies) {
        if (!enemies.isEmpty()) {
            System.out.println("Attaque du premier ennemi.");
        }
    }
}