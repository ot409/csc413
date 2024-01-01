package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpeedPowerUp extends GameObject implements PowerUp {
    float posX, posY; // Position of the Speed power-up
    BufferedImage image; // Image representing the Speed power-up
    private Rectangle collisionBox; // Rectangle representing the collision area
    boolean collected = false; // Flag to track if the power-up has been collected

    // Constructor for creating a Speed power-up
    public SpeedPowerUp(float x, float y, BufferedImage img) {
        this.posX = x;
        this.posY = y;
        this.image = img;
        this.collisionBox = new Rectangle((int) x, (int) y, this.image.getWidth(), this.image.getHeight());
    }

    // Get the collision area of the Speed power-up
    @Override
    public Rectangle getCollisionBox() {
        return this.collisionBox.getBounds();
    }

    // Handle collision with other game objects (not implemented for Speed power-up)
    @Override
    public void handleCollision(GameObject otherObject) {
        // Collision handling not implemented for Speed power-up
    }

    // Check if the Speed power-up has been collected (always returns false)
    @Override
    public boolean isCollected() {
        return false;
    }

    // Draw the Speed power-up on the screen if it hasn't been collected
    public void draw(Graphics buffer) {
        if (!this.collected) {
            buffer.drawImage(this.image, (int) this.posX, (int) this.posY, null);
        }
    }

    // Apply the power-up effect to the Tank
    @Override
    public void applyPowerUp(Tank tank) {
        tank.increaseSpeed(0.25f); // Adjust the speed increase amount as needed
        collected = true; // Mark the power-up as collected
    }
}
