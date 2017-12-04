package application;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

class Explosion extends AnimationBuild {

    Explosion(Pane pane, SpriteBuild sprite, Image[] sequence, double durationMs) {

        super(sequence, durationMs);

        relocateAnimationTo(sprite);

        pane.getChildren().add(getView());

        setOnFinished(event -> {

            if (!sprite.isAlive())

                sprite.setRemovable(true);

            stop();

            pane.getChildren().remove(getView());

        });

    }

    // create explosion at sprite location
    private void relocateAnimationTo(SpriteBuild sprite) {

        getView().relocate(sprite.getX() - getView().getImage().getWidth() / 3.6,
                sprite.getY() - getView().getImage().getHeight() / 2.5);

    }

}
