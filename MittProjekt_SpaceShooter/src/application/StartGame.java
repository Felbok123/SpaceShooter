package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StartGame extends Application {

    private Stage thisStage;
    private Scene mainScene;
    private AnimationTimer gameLoop;
    private boolean gamePause = false;
    private Random rnd;

    private Pane statsField;
    private Pane playField;
    private Pane lowerDustField;
    private Pane upperDustField;
    private Pane bulletField;
    private Pane backgroundField;

    private Image playerShip;
    private Image enemyShip;
    private Image enemyShip2;
    private Image enemyShip3;
    private Image enemyShip4;
    private Image backgroundImage;
    private Image starDust[];
    private Image playerBullet;
    private Image[] explosionSeq;

    private Player player;
    private Input input;
    private Stats stats;
    private ScoreManager manageScore;

    private Group gameGroup;

    private int score;

    private List<Stats> statList;
    private List<Background> backgroundList;
    private List<Enemy> enemyList;
    private List<StarDust> starList;
    private List<Player> playerList;
    private List<Bullet> bulletList;

    @Override
    public void start(Stage primaryStage) {

        try {
            thisStage = primaryStage;
            mainScene = createMenu();
            thisStage.setScene(mainScene);
            thisStage.initStyle(StageStyle.UNDECORATED);
            thisStage.show();
            manageScore = new ScoreManager();
            manageScore.loadFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Scene createMenu() {

        Group root = new Group();
        Scene menuScene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        ImageView view = new ImageView(getClass().getResource("/images/space650x800y.jpg").toExternalForm());

        GridPane gPane = new GridPane();
        gPane.setPadding(new Insets(100, 5, 0, 95));
        gPane.setVgap(5);

        Font font = new Font("Verdana", 40);

        Text title = new Text("Space Shooter");

        title.setFill(Color.DARKKHAKI);
        title.setStroke(Color.BLACK);
        title.setEffect(new Glow(100));
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
        Text newGame = new Text("New game");
        Text controls = new Text("Controls");
        Text highScore = new Text("High score");
        Text exit = new Text("Exit Game");
        newGame.setFont(font);
        newGame.setFill(Color.CHOCOLATE);
        controls.setFont(font);
        controls.setFill(Color.CHOCOLATE);
        highScore.setFont(font);
        highScore.setFill(Color.CHOCOLATE);
        exit.setFont(font);
        exit.setFill(Color.CHOCOLATE);
        changeColor(exit);
        changeColor(highScore);
        changeColor(controls);
        changeColor(newGame);

        VBox meny = new VBox();
        meny.setSpacing(30);
        meny.setAlignment(Pos.CENTER);
        meny.getChildren().addAll(newGame, highScore, controls, exit);
        gPane.add(title, 6, 13);
        gPane.add(meny, 6, 20);

        newGame.setOnMousePressed(e -> {

            mainScene = createGame();
            thisStage.setScene(mainScene);

        });

        highScore.setOnMousePressed(e -> {
            mainScene = createHighScore();
            thisStage.setScene(mainScene);

        });

        exit.setOnMousePressed(e -> Platform.exit());

        controls.setOnMousePressed(e -> {
            mainScene = createControls();
            thisStage.setScene(mainScene);

        });

        root.getChildren().add(view);
        root.getChildren().addAll(gPane);

        return menuScene;

    }

    private void changeColor(Text text) {
        text.setOnMouseEntered(e -> text.setFill(Color.BURLYWOOD));
        text.setOnMouseExited(e -> text.setFill(Color.CHOCOLATE));

    }

    private Scene createControls() {
        BorderPane root = new BorderPane();
        Scene controlScene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        ImageView view = new ImageView(getClass().getResource("/images/space650x800y.jpg").toExternalForm());
        Font font = new Font("Verdana", 40);

        Text back = new Text("Go Back");
        Text startGame = new Text("Start Game");
        Text cont = new Text();

        VBox vbox = new VBox(50);

        startGame.setFont(font);
        startGame.setFill(Color.CHOCOLATE);
        changeColor(startGame);
        back.setFont(font);
        back.setFill(Color.CHOCOLATE);
        changeColor(back);

        cont.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        cont.setFill(Color.DEEPSKYBLUE);

        cont.setText("  Hi and welcome to space shooter your objective " + "\n" + "  is to shoot ships and get points"
                + "\n" + "  to move the ship forward press W " + "\n" + "  to move the ship down press S" + "\n"
                + "  to move it to either side use the A for left and D for right" + "\n"
                + "  to shoot press the space bar" + "\n" + "  to pause the game press P and to exit press ESC");

        back.setOnMousePressed(e -> {
            mainScene = createMenu();
            thisStage.setScene(mainScene);

        });

        startGame.setOnMousePressed(e -> {
            mainScene = createGame();
            thisStage.setScene(mainScene);

        });

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(cont, startGame, back);

        cont.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(view);
        root.setCenter(vbox);

        return controlScene;

    }

    private Scene createGame() {

        try {

            // create lists
            bulletList = new ArrayList<>();
            playerList = new ArrayList<>();
            starList = new ArrayList<>();
            enemyList = new ArrayList<>();
            backgroundList = new ArrayList<>();
            statList = new ArrayList<>();

            // create group node
            gameGroup = new Group();

            // create pane nodes

            statsField = new Pane();
            backgroundField = new Pane();
            lowerDustField = new Pane();
            bulletField = new Pane();
            playField = new Pane();
            upperDustField = new Pane();

            rnd = new Random();

            gameGroup.getChildren().add(backgroundField);
            gameGroup.getChildren().add(bulletField);
            gameGroup.getChildren().add(playField);
            gameGroup.getChildren().add(lowerDustField);
            gameGroup.getChildren().add(upperDustField);
            gameGroup.getChildren().add(statsField);

            Scene gameScene = new Scene(gameGroup, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

            createFirst(gameScene);

            // createDebugInterface();

            createGameLoop();

            startGame();
            return gameScene;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private void createFirst(Scene scene) {

        loadResources();

        createBackground();

        createPlayer(scene);

        createStatUi();

    }

    private void createPlayer(Scene scene) {

        // player input
        input = new Input(scene);
        // add input listener
        input.addListener();

        Image image = playerShip;

        // center player on screen
        float x = (float) (Settings.SCENE_WIDTH - image.getWidth() * 3.7f);
        float y = Settings.SCENE_HEIGHT * 0.7f;

        player = new Player(playField, playerShip, x, y, 0f, 0f, 0f, 0f, Settings.PLAYER_SHIP_HEALTH, 0.5f,
                Settings.PLAYER_SHIP_SPEED, input);
        playerList.add(player);

    }

    private void createBackground() {

        Image image = backgroundImage;

        float x = 0;
        float y = (float) (-image.getHeight() + Settings.SCENE_HEIGHT);

        Background background = new Background(backgroundImage, backgroundField, x, y, Settings.BACKGROUND_SPEED);

        backgroundList.add(background);

    }

    /*
    Create Star dust is not working atm
     */
    private void createStarDust(boolean random) {

        if (random && rnd.nextInt(Settings.STAR_SPAWN_RANDOMNESS) != 0) {
            return;
        }

        Pane pane;
        Image image;
        float speed;
        float opacity;
        float x;
        float y;

        // random pane for dust particles also set different opacity depending
        // on pane
        if (rnd.nextInt(2) == 0) {
            pane = lowerDustField;
            opacity = 0.1f;
            speed = rnd.nextFloat() * 1.0f + 1.0f;
        } else {
            pane = upperDustField;
            opacity = 0.01f;
            speed = rnd.nextFloat() * 1.0f + 2.0f;
        }

        image = starDust[rnd.nextInt(starDust.length)];

        x = (float) (rnd.nextFloat() * Settings.SCENE_WIDTH - image.getWidth() / 2.0f);

        y = (float) -image.getHeight();

        StarDust starDust = new StarDust(pane, image, x, y, speed, opacity);

        starList.add(starDust);

    }

    private Scene createHighScore() {

        manageScore.updateScoreFile();
        BorderPane root = new BorderPane();
        Scene highScene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        ImageView view = new ImageView(getClass().getResource("/images/space650x800y.jpg").toExternalForm());

        Font fonth = new Font("Verdana", 20);

        Text highsc = new Text(manageScore.getHighScore());

        highsc.setFont(fonth);
        Font font = new Font("Verdana", 40);
        Text high = new Text("Top 10");
        Text back = new Text("Go Back");

        VBox vbox = new VBox(50);

        high.setFill(Color.CHOCOLATE);
        high.setFont(font);
        back.setFont(font);
        back.setFill(Color.CHOCOLATE);
        changeColor(back);

        back.setOnMousePressed(e -> {
            mainScene = createMenu();
            thisStage.setScene(mainScene);

        });

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(high, highsc, back);

        root.getChildren().add(view);
        root.setCenter(vbox);
        return highScene;

    }

    private Scene createGameOver() {
        BorderPane root = new BorderPane();
        Scene overScene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        ImageView view = new ImageView(getClass().getResource("/images/space650x800y.jpg").toExternalForm());
        Font font = new Font("Verdana", 40);

        TextField writeName = new TextField();
        Text name = new Text("Write your name");
        Text exit = new Text("Exit");
        Text startGame = new Text("Start Game");

        Button submit = new Button("Submit");

        VBox vbox = new VBox(50);

        writeName.setMaxSize(180, 40);
        writeName.setPromptText("enter name");

        writeName.setStyle("-fx-control-inner-background: chocolate; -fx-text-fill: black; -fx-font-size: 14;");

        name.setFont(font);
        name.setFill(Color.CHOCOLATE);
        startGame.setFont(font);
        startGame.setFill(Color.CHOCOLATE);
        submit.setFont(Font.font("Arial Black", 20));
        submit.setStyle("-fx-base: chocolate");
        exit.setFont(font);
        exit.setFill(Color.CHOCOLATE);
        changeColor(startGame);
        changeColor(exit);

        exit.setOnMousePressed(e -> Platform.exit());

        startGame.setOnMousePressed(e -> {
            mainScene = createGame();
            thisStage.setScene(mainScene);

        });

        submit.setOnAction(e -> {

            manageScore.addScore(writeName.getText(), score);
            mainScene = createHighScore();
            thisStage.setScene(mainScene);

        });

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(name, writeName, submit, startGame, exit);

        root.getChildren().add(view);
        root.setCenter(vbox);

        return overScene;
    }

    private void spawnEnemies(boolean random) {

        Image image = enemyShip;
        Image image2 = enemyShip2;
        Image image3 = enemyShip3;
        Image image4 = enemyShip4;
        int randomShip = rnd.nextInt(100);
        // spawn rate
        if (random && rnd.nextInt(Settings.ENEMY_SPAWN_RATE) != 0) {
            return;
        }

        // random speed
        float speed = rnd.nextFloat() * 1.0f + 3.0f;
        float speed2 = rnd.nextFloat() + 1.0f;

        // x make sure enemies always are inside the game and no parts outside
        // y spawn right on top of the screen

        float x = (float) (rnd.nextFloat() * (Settings.SCENE_WIDTH - image.getWidth() / 1.2f));
        float x2 = (float) (rnd.nextFloat() * (Settings.SCENE_WIDTH - image2.getWidth()));
        float x3 = (float) (rnd.nextFloat() * (Settings.SCENE_WIDTH - image3.getWidth()));
        float x4 = (float) (rnd.nextFloat() * (Settings.SCENE_WIDTH - image4.getWidth()));

        float y = (float) -image.getHeight();
        float y2 = (float) -image2.getHeight();
        float y3 = (float) -image3.getHeight();
        float y4 = (float) -image4.getHeight();

        Enemy enemy1 = new Enemy(playField, image, x, y, 0, 0, speed, 0, 5, 20, 100);
        Enemy enemy2 = new Enemy(playField, image2, x2, y2, 0, 0, speed, 0, 40, 4, 200);
        Enemy enemy3 = new Enemy(playField, image3, x3, y3, 0, 0, speed2, 0, 90, 5, 300);
        Enemy enemy4 = new Enemy(playField, image4, x4, y4, 0, 0, speed2, 0, 150, 10, 600);

        // random sprite and add sprite
        if (randomShip >= 70) {
            enemyList.add(enemy1);
        } else if (randomShip < 69 && randomShip >= 29) {
            enemyList.add(enemy2);
        } else if (randomShip < 28 && randomShip >= 10) {
            enemyList.add(enemy3);
        } else {
            enemyList.add(enemy4);
        }
    }

    private void spawnPrimaryWeapon(Player player) {

        player.primaryWeaponCharge();

        if (player.firePrimaryWeapon()) {
            Bullet bullet;

            Image image = playerBullet;

            // weapons rightX bullets from the right side of ship center leftX
            // left center side
            // and x middle bullet

            float rightX = (float) (player.getPrimaryWeaponRightX() - image.getWidth() / 2.0f);
            float x = (float) (player.getPrimaryWeaponX() - image.getWidth() / 2.0f);

            float leftX = (float) (player.getPrimaryWeaponLeftX() - image.getWidth() / 2.0f);

            float y = player.getPrimaryWeaponY() - 30;

            float spread = player.getPrimaryWeaponBulletSpread();
            float count = player.getPrimaryWeaponBulletCount();
            float speed = player.getPrimaryWeaponBulletSpeed();

            // create sprite middle shot

            bullet = new Bullet(bulletField, image, x, y, 0, 0, -speed, 0, 1, 1);
            bulletList.add(bullet);

            // left/right: vary x-axis position
            for (int i = 0; i < count / 2.0; i++) {

                // left shot
                bullet = new Bullet(bulletField, image, leftX, y, 0, -spread * i, -speed, 0, 1, 1);

                bulletList.add(bullet);

                // right shot
                bullet = new Bullet(bulletField, image, rightX, y, 0, spread * i, -speed, 0, 1, 1);

                bulletList.add(bullet);

            }

            player.primaryWeaponUncharge();

        }

    }

    private void spawnExplosion(SpriteBuild sprite) {
        Explosion exp = new Explosion(playField, sprite, explosionSeq, 400);
        exp.play();
    }

    private void createStatUi() {
        stats = new Stats(player);

        stats.prefWidthProperty().bind(thisStage.getScene().widthProperty());

        statsField.getChildren().add(stats);

        statList.add(stats);

    }

    private void updateScoreUi() {

        stats.updateUI();

    }

    private void spawnScore(SpriteBuild sprite, int sc) {
        TransText score = new TransText(playField, sprite, 5000, sc);
        score.play();

    }

    private void loadResources() {

        // explosion image
        explosionSeq = new Image[6];
        explosionSeq[0] = new Image(getClass().getResource("/eximage/Ex_A10.png").toExternalForm());
        explosionSeq[1] = new Image(getClass().getResource("/eximage/Ex_A11.png").toExternalForm());
        explosionSeq[2] = new Image(getClass().getResource("/eximage/Ex_A12.png").toExternalForm());
        explosionSeq[3] = new Image(getClass().getResource("/eximage/Ex_A13.png").toExternalForm());
        explosionSeq[4] = new Image(getClass().getResource("/eximage/Ex_A14.png").toExternalForm());
        explosionSeq[5] = new Image(getClass().getResource("/eximage/Ex_A15.png").toExternalForm());

        // enemy image
        enemyShip = new Image(getClass().getResource("/images/ship2fd.png").toExternalForm());
        enemyShip2 = new Image(getClass().getResource("/images/ship3fd.png").toExternalForm());
        enemyShip3 = new Image(getClass().getResource("/images/ship4fd.png").toExternalForm());
        enemyShip4 = new Image(getClass().getResource("/images/ship5fd.png").toExternalForm());

        // bullet image
        playerBullet = new Image(getClass().getResource("/images/bullet3.png").toExternalForm());

        // dust particles
        starDust = new Image[4];
        starDust[0] = new Image(getClass().getResource("/images/tests3.png").toExternalForm());
        starDust[1] = new Image(getClass().getResource("/images/tests3.png").toExternalForm());
        starDust[2] = new Image(getClass().getResource("/images/tests3.png").toExternalForm());
        starDust[3] = new Image(getClass().getResource("/images/tests3.png").toExternalForm());

        // get background
        backgroundImage = new Image(getClass().getResource("/images/space650x800y.jpg").toExternalForm());

        // ship image
        playerShip = new Image(getClass().getResource("/images/shipfu.png").toExternalForm());

    }

    private void moveSprites(List<? extends SpriteBuild> spriteList) {
        for (SpriteBuild sprite : spriteList) {
            sprite.move();
        }
    }

    private void updateSprites(List<? extends SpriteBuild> spriteList) {
        for (SpriteBuild sprite : spriteList) {
            sprite.update();
        }
    }

    private void checkIfRemovable(List<? extends SpriteBuild> spriteList) {
        for (SpriteBuild sprite : spriteList) {
            sprite.checkIfRemovable();
        }
    }

    private void removeSprites(List<? extends SpriteBuild> spriteList) {
        Iterator<? extends SpriteBuild> iterator = spriteList.iterator();
        while (iterator.hasNext()) {
            SpriteBuild sprite = iterator.next();
            if (sprite.isRemovable()) {

                sprite.removeFromPane();

                iterator.remove();

            }
        }

    }

    private void bulletCollisionCheck(List<? extends SpriteBuild> bulletCollisionList) {

        for (SpriteBuild bullet : bulletCollisionList) {
            for (Enemy enemy : enemyList) {

                if (!enemy.isAlive())
                    continue;
                if (bullet.collideDetect(enemy)) {

                    // inflict damage
                    enemy.getDamagedBy(bullet);

                    if (!enemy.isAlive()) {

                        enemy.stopMovement();
                        spawnScore(enemy, enemy.getPoints());

                        spawnExplosion(enemy);

                        player.addScore(enemy.getPoints());

                    }

                    bullet.kill();

                    // remove bullet
                    bullet.remove();
                }
            }
        }

    }

    private void shipCollisionCheck() {
        for (Player player : playerList) {
            for (Enemy enemy : enemyList) {
                if (player.collideDetect(enemy)) {

                    // player takes damage by enemy collision
                    player.getDamagedBy(enemy);

                    // enemy takes damage by collision
                    enemy.getDamagedBy(player);

                    if (!enemy.isAlive()) {
                        enemy.stopMovement();
                        spawnExplosion(enemy);
                        spawnScore(enemy, enemy.getPoints());
                        player.addScore(enemy.getPoints());

                        enemy.remove();
                    }
                    if (!player.isAlive()) {
                        spawnExplosion(player);
                        gameStop();
                    }

                }
            }
        }
    }

    private void startGame() {
        gameLoop.start();

    }

    private void gamePause() {
        gamePause = true;
        gameLoop.stop();
    }

    private void gameResume() {
        gamePause = false;
        gameLoop.start();

    }

    private void gameStop() {
        score = player.getScore();
        gameLoop.stop();
        input.removeListener();

        gameGroup.getChildren().removeAll(gameGroup.getChildren());
        mainScene = createGameOver();
        thisStage.setScene(mainScene);

    }

    private EventHandler<KeyEvent> globalKeyEvent = new EventHandler<>() {

        @Override
        public void handle(KeyEvent event) {

            // toggle pause
            if (event.getCode() == KeyCode.P) {

                if (gamePause) {
                    gameResume();
                } else {
                    gamePause();
                }

            } else if (event.getCode() == KeyCode.ESCAPE) {
                gameStop();
                mainScene = createMenu();
                thisStage.setScene(mainScene);
            }
        }
    };

    private void createGameLoop() {
        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // add input for player
                for (Player player : playerList) {
                    player.initInput();
                }

                // add random enemies and stars
                spawnEnemies(true);
                //	createStarDust(true);

                // spawn bullets of player
                for (Player player : playerList) {
                    spawnPrimaryWeapon(player);

                }

                thisStage.addEventFilter(KeyEvent.KEY_RELEASED, globalKeyEvent);
                // move sprite
                moveSprites(playerList);
                moveSprites(backgroundList);
                moveSprites(bulletList);
                moveSprites(enemyList);
                moveSprites(starList);

                // update sprite
                updateSprites(backgroundList);
                updateSprites(playerList);
                updateSprites(bulletList);
                updateSprites(starList);
                updateSprites(enemyList);

                // check collision
                bulletCollisionCheck(bulletList);
                shipCollisionCheck();

                // check if sprite can be removed
                checkIfRemovable(bulletList);
                checkIfRemovable(starList);
                checkIfRemovable(enemyList);

                // remove sprite
                removeSprites(bulletList);
                removeSprites(starList);
                removeSprites(enemyList);

                // update score and health
                updateScoreUi();

            }
        };

    }

    public static void main(String[] args) {
        launch(args);
    }
}
