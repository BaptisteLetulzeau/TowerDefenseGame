
package entities.towers;
import entities.enemies.Enemies;
import entities.towers.Towers;
import java.util.List;

public class TargetStrongestEnemy implements AttackStrategy {
    @Override
    public void attack(Towers tower, List<Enemies> enemies) {
        System.out.println("Attaque de l'ennemi le plus faible.");
    }
}