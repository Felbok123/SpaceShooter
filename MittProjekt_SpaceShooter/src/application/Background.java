package application;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Background extends SpriteBuild {

	public Background(Image image, Pane pane, float x, float y, float speed) {
		super(image, pane, x, y, 0, 0, speed, 0, Float.MAX_VALUE, 0);
	}

	public void move() {
		super.move();

		checkBounds();

	}

	private void checkBounds() {
		// check bounds 
		if (Float.compare(y, 0) > 0) {
			y = 0;
		}
	}

	@Override
	public void checkIfRemovable() {
		// not removing background

	}

}
