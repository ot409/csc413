package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject {
    float posX, posY; // Position of the Wall
    BufferedImage image; // Image representing the Wall
    private Rectangle collisionBox; // Rectangle representing the collision area

    // Constructor for creating a Wall object
    public Wall(float x, float y, BufferedImage img) {
        this.posX = x;
        this.posY = y;
        this.image = img;
        this.collisionBox = new Rectangle((int) x, (int) y, this.image.getWidth(), this.image.getHeight());
    }

    // Get the collision area of the Wall
    @Override
    public Rectangle getCollisionBox() {
        return this.collisionBox.getBounds();
    }

    // Handle collision with other game objects (not implemented for Wall)
    @Override
    public void handleCollision(GameObject otherObject) {
        // Collision handling not implemented for Wall
    }

    // Check if the Wall has collided with something (always returns false)
    @Override
    public boolean hasCollided() {
        return false;
    }

    // Draw the Wall on the screen
    public void draw(Graphics buffer) {
        buffer.drawImage(this.image, (int) this.posX, (int) this.posY, null);
    }

    // Get the X coordinate of the Wall
    public float getPositionX() {
        return this.posX;
    }

    // Get the Y coordinate of the Wall
    public float getPositionY() {
        return this.posY;
    }
}
