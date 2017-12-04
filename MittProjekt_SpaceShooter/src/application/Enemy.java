package application;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Enemy extends SpriteBuild {

    private int points;

    Enemy(Pane pane, Image image, float x, float y, float r, float dx, float dy, float dr, float health,
          float damage, int score) {
        super(image, pane, x, y, r, dx, dy, dr, health, damage);
        this.points = score;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public void checkIfRemovable() {
        if (Double.compare(getY(), Settings.SCENE_HEIGHT) > 0) {
            setRemovable(true);
        }

    }

}
