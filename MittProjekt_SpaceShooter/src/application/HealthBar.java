package application;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class HealthBar extends Pane {

	Rectangle outerHealth;
	Rectangle innerHealth;

	public HealthBar() {

		float height = 15;

		float outerWidth = 120;
		float innerWidth = 80;

		float x = 0;
		float y = 0;

		outerHealth = new Rectangle(x, y, outerWidth, height);
		outerHealth.setStroke(Color.BLACK);
		outerHealth.setStrokeWidth(4);
		outerHealth.setStrokeType(StrokeType.OUTSIDE);
		outerHealth.setFill(Color.RED);

		innerHealth = new Rectangle(x, y, innerWidth, height);
		innerHealth.setStrokeType(StrokeType.OUTSIDE);
		innerHealth.setFill(Color.LIME);

		getChildren().addAll(outerHealth, innerHealth);

	}

	public void setValue(double value) {
		innerHealth.setWidth(outerHealth.getWidth() * value);
	}

}
