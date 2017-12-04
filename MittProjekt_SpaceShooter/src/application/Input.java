package application;

import java.util.BitSet;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Input {

    private BitSet keyboardBit = new BitSet();

    private KeyCode goUp = KeyCode.W;
    private KeyCode goDown = KeyCode.S;
    private KeyCode goLeft = KeyCode.A;
    private KeyCode goRight = KeyCode.D;
    private KeyCode primaryWeaponKey = KeyCode.SPACE;
    private KeyCode secondaryWeaponKey = KeyCode.CONTROL;

    Scene scene;

    Input(Scene scene) {
        this.scene = scene;
    }

    public void addListener() {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEvent);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEvent);

    }

    public void removeListener() {
        scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedEvent);
        scene.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEvent);
    }

    // register pressed key in the bitset

    // register key down
    private EventHandler<KeyEvent> keyPressedEvent = event -> keyboardBit.set(event.getCode().ordinal(), true);

    // unregister released key in the bitset

    // register key up
    private EventHandler<KeyEvent> keyReleasedEvent = event -> keyboardBit.set(event.getCode().ordinal(), false);

    // get pressed direction of bitset if both ways are pressed it wont move
    // either way

    public boolean moveUp() {
        return keyboardBit.get(goUp.ordinal()) && !keyboardBit.get(goDown.ordinal());

    }

    public boolean moveDown() {
        return keyboardBit.get(goDown.ordinal()) && !keyboardBit.get(goUp.ordinal());
    }

    public boolean moveLeft() {
        return keyboardBit.get(goLeft.ordinal()) && !keyboardBit.get(goRight.ordinal());
    }

    public boolean moveRight() {
        return keyboardBit.get(goRight.ordinal()) && !keyboardBit.get(goLeft.ordinal());
    }

    public boolean firePrimary() {
        return keyboardBit.get(primaryWeaponKey.ordinal());
    }

    public boolean fireSecondary() {
        return keyboardBit.get(secondaryWeaponKey.ordinal());
    }

    // Getters and Setters

    public BitSet getKeyboardBit() {
        return keyboardBit;
    }

    public void setKeyboardBit(BitSet keyboardBit) {
        this.keyboardBit = keyboardBit;
    }

    public KeyCode getGoUp() {
        return goUp;
    }

    public void setGoUp(KeyCode goUp) {
        this.goUp = goUp;
    }

    public KeyCode getGoDown() {
        return goDown;
    }

    public void setGoDown(KeyCode goDown) {
        this.goDown = goDown;
    }

    public KeyCode getGoLeft() {
        return goLeft;
    }

    public void setGoLeft(KeyCode goLeft) {
        this.goLeft = goLeft;
    }

    public KeyCode getGoRight() {
        return goRight;
    }

    public void setGoRight(KeyCode goRight) {
        this.goRight = goRight;
    }

    public KeyCode getPrimaryWeaponKey() {
        return primaryWeaponKey;
    }

    public void setPrimaryWeaponKey(KeyCode primaryWeaponKey) {
        this.primaryWeaponKey = primaryWeaponKey;
    }

    public KeyCode getSecondaryWeaponKey() {
        return secondaryWeaponKey;
    }

    public void setSecondaryWeaponKey(KeyCode secondaryWeaponKey) {
        this.secondaryWeaponKey = secondaryWeaponKey;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public EventHandler<KeyEvent> getKeyPressedEvent() {
        return keyPressedEvent;
    }

    public void setKeyPressedEvent(EventHandler<KeyEvent> keyPressedEvent) {
        this.keyPressedEvent = keyPressedEvent;
    }

    public EventHandler<KeyEvent> getKeyReleasedEvent() {
        return keyReleasedEvent;
    }

    public void setKeyReleasedEvent(EventHandler<KeyEvent> keyReleasedEvent) {
        this.keyReleasedEvent = keyReleasedEvent;
    }

}
