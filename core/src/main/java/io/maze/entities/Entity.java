package io.maze.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
public abstract class Entity {
    private final Texture frontTexture, backTexture, leftTexture, rightTexture;

    private final Sprite frontSprite, backSprite, leftSprite, rightSprite;

    private Sprite activeSprite;

    float x, y;

    private final float speed;

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

    public void updateSpritePositions(){
        frontSprite.setPosition(x, y);
        backSprite.setPosition(x, y);
        leftSprite.setPosition(x, y);
        rightSprite.setPosition(x, y);
    }

    public float getCenterX(){
        return activeSprite.getX() + activeSprite.getWidth() / 2f;
    }

    public float getCenterY(){
        return activeSprite.getY() + activeSprite.getHeight() / 2f;
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

    public float getSpeed() {
        return speed;
    }

    public Sprite getActiveSprite() {
        return activeSprite;
    }

    public void setActiveSprite(Sprite activeSprite) {
        this.activeSprite = activeSprite;
    }

    public Sprite getFrontSprite() {
        return frontSprite;
    }

    public Sprite getBackSprite() {
        return backSprite;
    }

    public Sprite getLeftSprite() {
        return leftSprite;
    }

    public Sprite getRightSprite() {
        return rightSprite;
    }

    public float getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(float deltaX) {
        this.deltaX = deltaX;
    }

    public float getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(float deltaY) {
        this.deltaY = deltaY;
    }


    public void dispose() {
        frontTexture.dispose();
        backTexture.dispose();
        leftTexture.dispose();
        rightTexture.dispose();
    }

}
