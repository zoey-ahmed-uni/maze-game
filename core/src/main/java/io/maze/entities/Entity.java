package io.maze.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * An abstract class which represents a generic Entity.
 * <p>
 * The class handles an Entity's texture and sprite representation and also position and movement.
 * <p>
 * Methods can be overwritten by extended classes, however that should not be necessary.
 * Optionally, additional methods can be implemented.
 */

public abstract class Entity {
    private final Texture frontTexture, backTexture, leftTexture, rightTexture;

    private final Sprite frontSprite, backSprite, leftSprite, rightSprite;

    private Sprite activeSprite;

    float x, y;

    protected float speed;

    private float deltaX;
    private float deltaY;

    public Entity(String frontPath, String backPath, String rightPath, String leftPath) {
        frontTexture = new Texture(frontPath);
        backTexture = new Texture(backPath);
        rightTexture = new Texture(rightPath);
        leftTexture = new Texture(leftPath);

        frontSprite = new Sprite(frontTexture);
        backSprite = new Sprite(backTexture);
        rightSprite = new Sprite(rightTexture);
        leftSprite = new Sprite(leftTexture);

        frontSprite.setSize(1f, 1f);
        backSprite.setSize(1f, 1f);
        rightSprite.setSize(1f, 1f);
        leftSprite.setSize(1f, 1f);

        activeSprite = frontSprite;

        x = 0f;
        y = 0f;

        speed = 4f;

        deltaX = 0f;
        deltaY = 0f;
    }

    /**
     * Updates the x and y coordinates of each sprite which represents the entity (left, right etc.).
     */
    public void updateSpritePositions(){
        frontSprite.setPosition(x, y);
        backSprite.setPosition(x, y);
        leftSprite.setPosition(x, y);
        rightSprite.setPosition(x, y);
    }

    /**
     * Gets x coordinate of the Entity's center.
     *
     * @return the x coordinate of the center
     */
    public float getCenterX(){
        return activeSprite.getX() + activeSprite.getWidth() / 2f;
    }

    /**
     * Gets y coordinate of the Entity's center.
     *
     * @return the y coordinate of the center
     */
    public float getCenterY(){
        return activeSprite.getY() + activeSprite.getHeight() / 2f;
    }

    /**
     * Gets x coordinate of the Entity.
     *
     * @return the x coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the Entity's x coordinate.
     *
     * @param x the new desired x coordinate
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets the Entity's y coordinate.
     *
     * @return the y coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the Entity's y coordinate.
     *
     * @param y the new desired y coordinate
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Sets the Entity's speed.
     *
     * @param speed the new speed
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Gets the speed of the Entity.
     *
     * @return the speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Gets the Sprite of the entity.
     *
     * @return the sprite.
     */
    public Sprite getActiveSprite() {
        return activeSprite;
    }

    /**
     * Sets the current sprite representation of the entity.
     *
     * @param activeSprite e.g. (right, left, front back).
     */
    public void setActiveSprite(Sprite activeSprite) {
        this.activeSprite = activeSprite;
    }

    /**
     * Gets front sprite of the Entity.
     *
     * @return the front sprite
     */
    public Sprite getFrontSprite() {
        return frontSprite;
    }

    /**
     * Gets the back sprite of the Entity.
     *
     * @return the back sprite
     */
    public Sprite getBackSprite() {
        return backSprite;
    }

    /**
     * Gets the left sprite of the entity.
     *
     * @return the left sprite
     */
    public Sprite getLeftSprite() {
        return leftSprite;
    }

    /**
     * Gets the right sprite of the Entity.
     *
     * @return the right sprite
     */
    public Sprite getRightSprite() {
        return rightSprite;
    }

    /**
     * Gets the Entity's change in x.
     *
     * @return the change in x
     */
    public float getDeltaX() {
        return deltaX;
    }

    /**
     * Sets the Entity's change in x.
     *
     * @param deltaX the new change in x
     */
    public void setDeltaX(float deltaX) {
        this.deltaX = deltaX;
    }

    /**
     * Gets the Entity's change in y.
     *
     * @return the change in y
     */
    public float getDeltaY() {
        return deltaY;
    }

    /**
     * Sets the Entity's change in y.
     *
     * @param deltaY the new change in y
     */
    public void setDeltaY(float deltaY) {
        this.deltaY = deltaY;
    }

    /**
     * Disposes all resources associated with each of the individual Entity textures.
     */
    public void dispose() {
        frontTexture.dispose();
        backTexture.dispose();
        leftTexture.dispose();
        rightTexture.dispose();
    }

}
