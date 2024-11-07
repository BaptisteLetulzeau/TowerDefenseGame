package entities.towers;

import entities.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Towers extends ImageView  {
    protected double x;
    protected double y;

    public Towers(double x, double y, String imagePath) {
        this.x = x;
        this.y = y;
        setImage(new Image(imagePath));
        setX(x);
        setY(y);
    }

    public abstract void shoot(Pane gamePane);
}

//public abstract class Towers extends Rectangle {
//    public Towers(double x, double y) {
//        super(40, 40);
//        this.setFill(Color.BLUE);
//        this.setX(x);
//        this.setY(y);
//    }
//}