package application;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Bullet extends SpriteBuild {

    public Bullet(Pane pane, Image image, float x, float y, float dx, float dy) {
        super(image, pane, x, y, 0, dx, dy, 0, 1, 1);
    }

    Bullet(Pane pane, Image image, float x, float y, float r, float dx, float dy, float dr, float health,
           float damage) {
        super(image, pane, x, y, r, dx, dy, dr, health, damage);
    }

    @Override
    public void checkIfRemovable() {
        // upper bounds exceeded

        if (getY() + getH() < 0) {
            setRemovable(true);

        }

    }

}
