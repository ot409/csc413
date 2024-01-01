package tankrotationexample.game;

import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthPowerUp extends GameObject implements PowerUp {
    private float positionX, positionY;
    private BufferedImage image;
    private Rectangle boundingBox;
    private boolean hasBeenCollected = false; // flag to track if the power-up has been collected
    private int healthBonus = 100;
    private GameWorld gameWorld;

    // constructor for creating a Health power-up
    public HealthPowerUp(float x, float y, BufferedImage img) {
        this.positionX = x;
        this.positionY = y;
        this.image = img;
        this.boundingBox = new Rectangle((int) x, (int) y, this.image.getWidth(), this.image.getHeight());
    }

    // get the hitbox of the Health power-up
    @Override
    public Rectangle getBoundingBox() {
        return this.boundingBox.getBounds();
    }

    // handle collision with other game objects
    @Override
    public void handleCollision(GameObject obj) {
        if (!hasBeenCollected && obj instanceof Tank) {
            applyPowerUp((Tank) obj); // apply power-up effect to the Tank
            // gameWorld.anims.add(new Animation(positionX, positionY, ResourceManager.getAnimation("bulletshoot"))); // ! don't work
        }
    }

    // check if the Health power-up has been collected
    @Override
    public boolean hasBeenCollected() {
        return hasBeenCollected;
    }

    // draw the Health power-up on the screen if it hasn't been collected
    public void drawImage(Graphics buffer) {
        if (!this.hasBeenCollected) {
            buffer.drawImage(this.image, (int) positionX, (int) positionY, null);
        }
    }

    // apply the power-up effect to the Tank
    @Override
    public void applyPowerUp(Tank tank) {
        tank.increaseLife(healthBonus); // increase tank's life by the specified bonus amount
        hasBeenCollected = true; // mark the power-up as collected
    }
}
