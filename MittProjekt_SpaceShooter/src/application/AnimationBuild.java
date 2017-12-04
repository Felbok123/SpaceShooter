package application;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimationBuild extends Transition {

    private final ImageView imageView;
    private final int count;

    private int lastIndex;

    private Image[] sequence;

    AnimationBuild(Image[] sequence, double durationMs) {

        this.imageView = new ImageView(sequence[0]);
        this.sequence = sequence;
        this.count = sequence.length;

        setCycleCount(1);
        setCycleDuration(Duration.millis(durationMs));
        setInterpolator(Interpolator.LINEAR);

    }

    @Override
    protected void interpolate(double x) {

        final int index = (int) Math.min(Math.floor(x * count), count - 1);

        if (index != lastIndex) {
            imageView.setImage(sequence[index]);
            lastIndex = index;
        }

    }

    ImageView getView() {
        return imageView;
    }

}
