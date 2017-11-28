package application;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class StarDust extends SpriteBuild {

	// TODO Find img to actully make it appear as stars are flying..

	public StarDust(Pane pane, Image image, float x, float y, float speed, float opacity) {

		super(image, pane, x, y, 0.0f, 0.0f, speed, 0.0f, Float.MAX_VALUE, 0.0f);

		getImageView().setOpacity(opacity);
	}

	@Override
	public void checkIfRemovable() {
		if (Double.compare(getY(), Settings.SCENE_HEIGHT) > 0) {
			setRemovable(true);

		}

	}
}
