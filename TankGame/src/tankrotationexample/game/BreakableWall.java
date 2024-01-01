package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends GameObject {
    float positionX, positionY;
    BufferedImage image;
    private Rectangle boundingBox;
    private int health;
    private GameWorld gameWorld;

    // constructor for creating a BreakableWall object
    public BreakableWall(float initialX, float initialY, BufferedImage image) {
        this.positionX = initialX;
        this.positionY = initialY;
        this.image = image;
        this.boundingBox = new Rectangle((int)positionX, (int)positionY, this.image.getWidth(), this.image.getHeight());
        this.health = 50;
    }

    // get the bounding box of the BreakableWall
    @Override
    public Rectangle getBoundingBox() {
        return this.boundingBox.getBounds();
    }

    // handle collision with other game objects
    @Override
    public void handleCollision(GameObject object) {
        if (object instanceof Bullet) {
            // decrease the health of the wall upon collision with a bullet
            this.health--;
        }
    }

    // check if the wall has been destroyed
    @Override
    public boolean hasCollided() {
        return this.health <= 0;
    }

    // draw the BreakableWall on the screen
    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.image, (int)positionX, (int)positionY, null);
    }
}
