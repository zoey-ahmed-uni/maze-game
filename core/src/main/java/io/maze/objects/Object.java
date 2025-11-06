package io.maze.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

/** 
 * The object class represents a generic object. It handles the texture, sprite and position.
 * <p>
 * Different to an {@link io.maze.entities.Entity Entity} as such that it does not require
 * multiple direction textures nor movement logic.
 */
public abstract class Object {
    private final Texture texture;
    private final Sprite sprite;
    private final String name;

    public Object(String name, String texturePath) {
        this.name = name;
        this.texture = new Texture(texturePath);
        this.sprite = new Sprite(texture);
        this.sprite.setSize(1f, 1f);
    }

    /** {@param objects the map objects} */
    public void setPosition(MapObjects objects) {
        for (RectangleMapObject object : objects.getByType(RectangleMapObject.class)) {
            if (object.getName().equals(this.name)) {
                this.sprite.setPosition(object.getRectangle().x / 16f, object.getRectangle().y / 16f);
            }
        }
    }

    /** {@return the sprite} */
    public Sprite getSprite() {
        return sprite;
    }

    /** {@return the name}*/
    public String getName() {
        return name;
    }
    
    /** Disposes all resources associated with the texture of the object. */
    public void dispose() {
       texture.dispose();
    }
}
