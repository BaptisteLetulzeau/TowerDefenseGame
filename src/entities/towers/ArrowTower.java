package entities.towers;

import javafx.scene.layout.Pane;

public class ArrowTower extends Towers implements Observer {

    public ArrowTower(double x, double y) {
        super(x, y, "/assets/images/towers/clement.JPG");
    }

    @Override
    public void update() {
        shoot();
    }

    private void shoot() {
        System.out.println("ArrowTower attaque !");
    }
}