package entities.towers;

import javafx.scene.layout.Pane;

public class FlailMan extends Towers {

    public FlailMan(double x, double y) {
        super(x, y, "/assets/towers/flailMan.png");
    }

    @Override
    public void shoot(Pane gamePane) {
        System.out.println("FlailMan attaque !");
    }
}