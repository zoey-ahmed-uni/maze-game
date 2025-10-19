package io.github.eng1team3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private final Texture frontTexture;
    private final Texture backTexture;
    private final Texture leftTexture;
    private final Texture rightTexture;

    Sprite frontSprite;
    Sprite backSprite;
    Sprite leftSprite;
    Sprite rightSprite;

    Sprite activeSprite;

    float x;
    float y;

    private final float speed;

    public Player() {
        frontTexture = new Texture("FrontView.png");
        backTexture = new Texture("BackView.png");
        rightTexture = new Texture("RightView.png");
        leftTexture = new Texture("LeftView.png");

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
    }

    public void moveRight(float delta, MapObjects objects) {
        activeSprite = rightSprite;
        float deltaX = speed * delta;
        if (!checkCollision(deltaX, 0, objects)) {
            activeSprite.translateX(deltaX);
        }
    }

    public void moveLeft(float delta, MapObjects objects) {
        activeSprite = leftSprite;
        float deltaX = -speed * delta;
        if (!checkCollision(deltaX, 0, objects)) {
            activeSprite.translateX(deltaX);
        }
    }

    public void moveUp(float delta, MapObjects objects) {
        activeSprite = backSprite;
        float deltaY = speed * delta;
        if (!checkCollision(0, deltaY, objects)) {
            activeSprite.translateY(deltaY);
        }
    }

    public void moveDown(float delta, MapObjects objects) {
        activeSprite = frontSprite;
        float deltaY = -speed * delta;
        if (!checkCollision(0, deltaY, objects)) {
            activeSprite.translateY(deltaY);
        }
    }

    private boolean checkCollision(float deltaX, float deltaY, MapObjects objects) {
        Rectangle futureHitBox = new Rectangle(
            activeSprite.getX() + deltaX,
            activeSprite.getY() + deltaY,
            1f,
            1f
        );

        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();

            Rectangle scaledRectangle = new Rectangle(
                rectangle.x / 16f,
                rectangle.y / 16f,
                rectangle.width / 16f,
                rectangle.height / 16f
            );

            if (Intersector.overlaps(scaledRectangle, futureHitBox)) {
                return true;
            }
        }
        return false;
    }

    public void dispose() {
        frontTexture.dispose();
        backTexture.dispose();
        leftTexture.dispose();
        rightTexture.dispose();
    }
}
