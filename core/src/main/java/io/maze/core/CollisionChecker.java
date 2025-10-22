package io.maze.core;

import io.maze.entities.Entity;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class CollisionChecker {

    public static boolean isColliding(Entity entity, MapObjects objects) {

        Rectangle futureHitbox = new Rectangle(
            entity.getActiveSprite().getX() + entity.getDeltaX(),
            entity.getActiveSprite().getY() + entity.getDeltaY(),
            1f,
            1f
        );

        for (RectangleMapObject collisionObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle object = collisionObject.getRectangle();

            Rectangle scaledObject = new Rectangle(
                object.x / 16f,
                object.y / 16f,
                object.width / 16f,
                object.height / 16f
            );

            if (Intersector.overlaps(scaledObject, futureHitbox)) {
                return true;
            }
        }
        return false;
    }

}
