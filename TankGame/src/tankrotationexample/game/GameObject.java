package tankrotationexample.game;

import jdk.jfr.consumer.RecordedStackTrace;
import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    // factory method to create a new instance of a GameObject based on its type
    public static GameObject newInstance(String type, float initialX, float initialY) throws UnsupportedOperationException {
        return switch (type) {
            case "9", "3" -> new Wall(initialX, initialY, ResourceManager.getSprite("unbreak")); // create Wall object
            case "2" ->  new BreakableWall(initialX, initialY, ResourceManager.getSprite("break2")); // create BreakableWall object
            case "4" -> new Health(initialX, initialY, ResourceManager.getSprite("health")); // create Health object
            case "5" -> new Speed(initialX, initialY, ResourceManager.getSprite("speed")); // create Speed object
            case "6"->  new Shield(initialX, initialY, ResourceManager.getSprite("shield")); // create Shield object
            default -> throw new UnsupportedOperationException(); // unsupported type
        };
    }

    // draw the GameObject
    public void drawImage(Graphics graphics) {};

    // get the hitbox of the GameObject
    public abstract Rectangle getHitBox();

    // handle collision with another GameObject
    public abstract void handleCollision(GameObject otherObject);

    // check if the GameObject has collided with something
    public abstract boolean hasCollided();
}
