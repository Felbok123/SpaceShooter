package application;

import java.text.NumberFormat;

import javafx.animation.FadeTransition;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TransText {

	Text text;

	FadeTransition fadeTrans;

	public TransText(Pane pane, SpriteBuild sprite, double durationMs, int score) {

		// create view
		text = new Text(NumberFormat.getInstance().format(score));
		

		text.setStroke(Color.BLACK);
		text.setStrokeWidth(0.2);
		text.setFill(Color.BISQUE);
		text.setEffect(new Glow(50));

		// relocate score text to sprite
		relocateToSprite(sprite);

		// add score text
		pane.getChildren().add(getView());

		// create fade transition
		fadeTrans = new FadeTransition(Duration.millis(durationMs));

		fadeTrans.setNode(getView());
		fadeTrans.setFromValue(2.0);
		fadeTrans.setToValue(0.0);
		fadeTrans.setCycleCount(1);
		fadeTrans.setAutoReverse(false);

		// remove when finished
		fadeTrans.setOnFinished(e -> {

			pane.getChildren().remove(getView());
		});

	}

	public void play() {
		fadeTrans.play();
	}

	private Text getView() {
		return text;
	}

	private void relocateToSprite(SpriteBuild sprite) {

		getView().relocate(sprite.getX() - getView().getBoundsInParent().getWidth() + 60,
				sprite.getY() - getView().getBoundsInParent().getHeight() / 2.0);
	}

}
