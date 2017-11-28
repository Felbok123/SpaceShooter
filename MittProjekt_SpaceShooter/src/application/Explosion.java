package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Explosion extends AnimationBuild {

	public Explosion(Pane pane, SpriteBuild sprite, Image[] sequence, double durationMs) {

		super(sequence, durationMs);

		relocateAnimationTo(sprite);

		pane.getChildren().add(getView());

		setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (!sprite.isAlive())
					
					sprite.setRemovable(true);

				stop();

				pane.getChildren().remove(getView());

			}
		});

	}

	// create explosion at sprite location
	public void relocateAnimationTo(SpriteBuild sprite) {

		getView().relocate(sprite.getX() - getView().getImage().getWidth() / 3.6,
				sprite.getY() - getView().getImage().getHeight() / 2.5);

	}

}
