package application;

import java.text.NumberFormat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Stats extends StackPane {

    private Player player;

    private HealthBar healthBar;
    private Text scoreText;

    Stats(Player player) {

        this.player = player;

        setPadding(new Insets(2));

        // health bar
        healthBar = new HealthBar();
        StackPane.setAlignment(healthBar, Pos.TOP_LEFT);
        StackPane.setMargin(healthBar, new Insets(6, 0, 0, 6));

        getChildren().add(healthBar);

        // score
        scoreText = new Text();
        scoreText.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        scoreText.setStroke(Color.BLACK);
        scoreText.setFill(Color.WHITE);

        StackPane.setAlignment(scoreText, Pos.TOP_CENTER);

        getChildren().add(scoreText);

    }

    public void updateUI() {

        // update health bar
        healthBar.setValue(player.getCurrentHealth());

        // update score
        scoreText.setText(NumberFormat.getInstance().format(player.getScore()));

    }

    public Text getScoreText() {
        return scoreText;
    }

    public void setScoreText(Text scoreText) {
        this.scoreText = scoreText;
    }

}
