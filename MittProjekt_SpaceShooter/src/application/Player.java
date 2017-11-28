package application;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Player extends SpriteBuild {

	private float playerShipMinX;
	private float playerShipMaxX;
	private float playerShipMinY;
	private float playerShipMaxY;

	private Input input;
	private float speed;

	private float bulletChargeTime = 6; // can fire every n frame
	private float bulletChargeCounter = bulletChargeTime; // bullet is charged
															// from
															// start

	private float bulletCounterDelta = 0.905f; // counter increased by this
												// value every frame
	
	private float bullets = 7; // number of bullets ship shots at 1 time
	// right,left,center

	private float bulletSpread = 0.6f; // the spread of bullets
	private float bulletSpeed = 8; // speed of the bullets
	
	private float health = Settings.PLAYER_SHIP_HEALTH;
	private int score;

	public Player(Pane pane, Image image, float x, float y, float r, float dx, float dy, float dr, float health,
			float damage, float speed, Input input) {

		super(image, pane, x, y, r, dx, dy, dr, health, damage);

		this.speed = speed;
		this.input = input;

		init();

	}

	private void init() {

		// calculate movement bounds of the player ship
		// change values to make it appear on other side or bound to walls

		playerShipMinX = (float) (0 - image.getWidth() / 3.0f);
		playerShipMaxX = (float) (Settings.SCENE_WIDTH - image.getWidth() / 1.5f);

		playerShipMinY = (float) (0 - image.getHeight() / 3.0f);
		playerShipMaxY = (float) (Settings.SCENE_HEIGHT - image.getHeight() / 1.5f);
	}

	public void initInput() {

		// Movement

		// vertical move
		if (input.moveUp()) {
			dy = -speed;

		} else if (input.moveDown()) {
			dy = speed;
		} else {
			dy = 0;
		}

		// horizontal move
		if (input.moveLeft()) {
			dx = -speed;
		} else if (input.moveRight()) {
			dx = speed;
		} else {
			dx = 0;
		}
	}

	@Override
	public void move() {
		super.move();
		checkBounds();

	}

	private void checkBounds() {

		// vertical
		if (Float.compare(y, playerShipMinY) < 0) {
			y = playerShipMinY;
		} else if (Float.compare(y, playerShipMaxY) > 0) {
			y = playerShipMaxY;
		}
		// horizontal
		if (Float.compare(x, playerShipMinX) < 0) {
			x = playerShipMinX;
		} else if (Float.compare(x, playerShipMaxX) > 0) {
			x = playerShipMaxX;
		}

	}
	public float getCurrentHealth(){
		return getHealth() / health;
	}

	public void resetScore(){
		this.score=0;
	}
	
	public void addScore(int score){
		this.score += score;
	}
	
	public int getScore(){
		return this.score;
	}
	
	
	@Override
	public void checkIfRemovable() {
		// TODO

	}

	public boolean firePrimaryWeapon() {
		boolean isBulletCharged = bulletChargeCounter >= bulletChargeTime;

		return input.firePrimary() && isBulletCharged;

	}

	public void primaryWeaponCharge() {

		// limit weapon fire
		bulletChargeCounter += bulletCounterDelta;
		if (bulletChargeCounter > bulletChargeTime) {
			bulletChargeCounter = bulletChargeTime;
		}

	}

	public void primaryWeaponUncharge() {
		// player bullet uncharged
		bulletChargeCounter = 0;

	}

	public float getPrimaryWeaponX() {
		return (float) (x + image.getWidth() / 2.0f);
	}

	public float getPrimaryWeaponLeftX() {
		return (float) (x + image.getWidth() / 3.0f);
	}

	public float getPrimaryWeaponRightX() {
		return (float) (x + image.getWidth() / 1.5f);
	}

	public float getPrimaryWeaponY() {
		return y;
	}

	public float getPrimaryWeaponBulletSpread() {
		return bulletSpread;
	}

	public float getPrimaryWeaponBulletCount() {
		return bullets;
	}

	public float getPrimaryWeaponBulletSpeed() {
		return bulletSpeed;
	}

}
