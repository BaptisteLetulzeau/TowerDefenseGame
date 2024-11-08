package entities.enemies;

import javafx.geometry.Point2D;
import java.util.List;

public class MiniBear extends Enemies {

    public MiniBear(List<Point2D> waypoints) {
        super("assets/images/enemies/minibear.png", waypoints);

        setFitWidth(100);
        setFitHeight(100);
    }

    @Override
    public void move() {
        super.move();
    }

    public void attack() {
        System.out.println("MiniBear attaque !");
    }
}
