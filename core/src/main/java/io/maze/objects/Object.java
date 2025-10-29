package io.maze.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

public abstract class Object {
    private final Sprite sprite;
    private final String name;

    public Object(String name, String texturePath) {
        this.name = name;
        Texture texture = new Texture(texturePath);
        this.sprite = new Sprite(texture);
        this.sprite.setSize(1f, 1f);
    }

    public void setPosition(MapObjects objects) {
        for (RectangleMapObject object : objects.getByType(RectangleMapObject.class)) {
            if (object.getName().equals(this.name)) {
                this.sprite.setPosition(object.getRectangle().x / 16f - this.sprite.getWidth() / 2, object.getRectangle().y / 16f - this.sprite.getHeight() / 2);
            }
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public String getName() {
        return name;
    }
}
