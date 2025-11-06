package io.maze.core;

import io.maze.entities.Entity;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import io.maze.entities.Player;
import io.maze.objects.Object;
import java.util.List;

/**
 * The CollisionChecker class contains static methods which determine if a collision is occurring.
 */

public class CollisionChecker {

    /**
     * Checks for collisions between an entity and collidable objects on the map.
     *
     * @param entity {@link io.maze.entities.Player Player} or {@link io.maze.entities.Guard Guard}
     * @param objects collidable objects on the map e.g. Map Walls
     * @return true if collision is occurring
     */
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

    /**
     * Checks for collisions between an entity and collidable objects on the map with the ability
     * to specify exceptions.
     *
     * @param entity {@link io.maze.entities.Player Player} or {@link io.maze.entities.Guard Guard}
     * @param objects collidable objects on the map
     * @param exceptions objects which are not meant to be collidable
     * e.g. completed {@link io.maze.objects.Exam Exams}
     * @return true if collision is occurring
     */
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

    /**
     * Checks for collisions between the player and another entity.
     *
     * @param player the character the player is controlling
     * @param entity a non-playable character such as a {@link io.maze.entities.Guard Guard}
     * @return true if a collision is occurring
     */
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

    /**
     * Checks for collisions between the player and an object.
     *
     * @param player the character the player is controlling
     * @param object item on the map, e.g. uncompleted {@link io.maze.objects.Exam Exams}
     * @return true if a collision is occurring
     */
    public static boolean isColliding(Player player, Object object) {
        Rectangle playerHitbox = new Rectangle(
            player.getActiveSprite().getX(),
            player.getActiveSprite().getY(),
            1f,
            1f
        );

        Rectangle objectHitbox = new Rectangle(
            object.getSprite().getX(),
            object.getSprite().getY(),
            1f,
            1f
        );

        return Intersector.overlaps(playerHitbox, objectHitbox);
    }
}
