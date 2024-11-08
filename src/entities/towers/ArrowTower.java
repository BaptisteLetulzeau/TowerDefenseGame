package entities.towers;

import javafx.scene.layout.Pane;

public class ArrowTower extends Towers {

    public ArrowTower(double x, double y) {
        super(x, y, "/assets/images/towers/clement.JPG");
    }

    @Override
    public void shoot(Pane gamePane) {
        System.out.println("ArrowTower tire !");
    }
}