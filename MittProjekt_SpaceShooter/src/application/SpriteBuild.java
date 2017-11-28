package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class SpriteBuild {

	Image image;
	ImageView imageView;

	Pane pane;

	float x;
	float y;
	float r;

	float dx;
	float dy;
	float dr;

	float health;
	float damage;

	boolean removable = false;
	boolean canMove = true;

	float w;
	float h;

	public SpriteBuild(Image image, Pane pane, float x, float y, float r, float dx, float dy, float dr,
			float health, float damage) {
		this.image = image;
		this.pane = pane;
		this.x = x;
		this.y = y;
		this.r = r;
		this.dx = dx;
		this.dy = dy;
		this.dr = dr;

		this.health = health;
		this.damage = damage;

		this.w = (float) image.getWidth();
		this.h = (float) image.getHeight();

		this.imageView = new ImageView(image);
		this.imageView.relocate(x, y);
		this.imageView.setRotate(r);

		addToPane();

	}

	public void addToPane() {
		this.pane.getChildren().add(this.imageView);

	}

	public void removeFromPane() {
		this.pane.getChildren().remove(this.imageView);
	}

	public void move() {
		if (!canMove)
			return;

		x += dx;
		y += dy;
		r += dr;

	}

	// flag sprite to not move
	public void stopMovement() {
		this.canMove = false;

	}

	public boolean isAlive() {
		return Float.compare(health, 0) > 0;
	}

	public void update() {

		imageView.relocate(x, y);
		imageView.setRotate(r);
	}

	public boolean collideDetect(SpriteBuild otherSprite) {

		return (otherSprite.x + otherSprite.w >= x && otherSprite.y + otherSprite.h >= y && otherSprite.x <= x + w
				&& otherSprite.y <= y + h);

	}

	// reduce health by damage

	public void getDamagedBy(SpriteBuild sprite) {
		health -= sprite.getDamage();

	}

	// set ship or enemy to killed

	public void kill() {
		setHealth(0);
	}

	// if sprite is dead remove it
	public void remove() {
		setRemovable(true);
	}

	public abstract void checkIfRemovable();

	// Getters and Setters

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public float getDr() {
		return dr;
	}

	public void setDr(float dr) {
		this.dr = dr;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public boolean isRemovable() {
		return removable;
	}

	public void setRemovable(boolean removable) {
		this.removable = removable;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

}
