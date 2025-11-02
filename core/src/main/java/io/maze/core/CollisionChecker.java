package io.maze.core;

import io.maze.entities.Entity;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import io.maze.entities.Player;
import java.util.List;

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

    public static boolean isColliding(Entity entity, MapObjects objects, List<String> exceptions) {

        Rectangle futureHitbox = new Rectangle(
            entity.getActiveSprite().getX() + entity.getDeltaX(),
            entity.getActiveSprite().getY() + entity.getDeltaY(),
            1f,
            1f
        );

        for (RectangleMapObject collisionObject : objects.getByType(RectangleMapObject.class)) {

            if (exceptions != null && exceptions.contains(collisionObject.getName())) {
                continue;
            }

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

    public static boolean isColliding(Player player, Entity entity) {
        Rectangle playerHitbox = new Rectangle(
            player.getActiveSprite().getX(),
            player.getActiveSprite().getY(),
            1f,
            1f
        );

        Rectangle entityHitbox = new Rectangle(
            entity.getActiveSprite().getX(),
            entity.getActiveSprite().getY(),
            1f,
            1f
        );

        return Intersector.overlaps(playerHitbox, entityHitbox);
    }

    public static String CurrentObject(Player player, MapObjects mapObjects) {
        Rectangle playerHitbox = new Rectangle(
            player.getActiveSprite().getX(),
            player.getActiveSprite().getY(),
            1f,
            1f
        );

        for (RectangleMapObject mapObject : mapObjects.getByType(RectangleMapObject.class)) {

            Rectangle object = mapObject.getRectangle();

            Rectangle scaledObject = new Rectangle(
                object.x / 16f,
                object.y / 16f,
                object.width / 16f,
                object.height / 16f
            );

            if (Intersector.overlaps(scaledObject, playerHitbox)) {
                return mapObject.getName();
            }
        }
        return null;
    }

}
