package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {
    private float positionX, positionY;
    private float velocityX, velocityY;
    private float charge = 1f;
    private float angle;
    private float radius = 5;
    private BufferedImage image;
    private Rectangle boundingBox;

    // constructor for creating a Bullet object
    public Bullet(float initialX, float initialY, BufferedImage image, float initialAngle) {
        this.positionX = initialX + 55;
        this.positionY = initialY + 20;
        this.image = image;
        this.velocityX = 0;
        this.velocityY = 0;
        this.angle = initialAngle;
        this.boundingBox = new Rectangle((int) initialX, (int) initialY, this.image.getWidth(), this.image.getHeight());
    }

    // getter methods for x and y positions
    public float getPositionX() {
        return this.positionX;
    }

    public float getPositionY() {
        return this.positionY;
    }

    // update the bullet's position
    void update() {
        // calculate the velocity components based on the angle
        velocityX = Math.round(radius * Math.cos(Math.toRadians(angle)));
        velocityY = Math.round(radius * Math.sin(Math.toRadians(angle)));

        // update the bullet's position
        positionX += velocityX;
        positionY += velocityY;

        // check and handle the bullet crossing the game screen borders
        checkBorder();

        this.boundingBox.setLocation((int) positionX, (int) positionY);
    }

    // check and handle the bullet crossing the game screen borders
    private void checkBorder() {
        if (positionX < 30) {
            positionX = 30;
        }
        if (positionX >= GameConstants.GAME_SCREEN_WIDTH - 88) {
            positionX = GameConstants.GAME_SCREEN_WIDTH - 88;
        }
        if (positionY < 40) {
            positionY = 40;
        }
        if (positionY >= GameConstants.GAME_SCREEN_HEIGHT - 80) {
            positionY = GameConstants.GAME_SCREEN_HEIGHT - 80;
        }
    }

    // increase the bullet's charge
    public void increaseCharge() {
        this.charge = this.charge + 0.05f;
    }

    // draw the bullet on the screen
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(positionX, positionY);
        rotation.rotate(Math.toRadians(angle), this.image.getWidth() / 2.0, this.image.getHeight() / 2.0);
        rotation.scale(this.charge, this.charge); // scale the bullet to make it larger

        Graphics2D buffer = (Graphics2D) g;
        buffer.drawImage(this.image, rotation, null);

        // draw hit-box representation
        buffer.setColor(Color.RED); // You can choose any color you prefer
        buffer.drawRect((int) positionX, (int) positionY, this.image.getWidth(), this.image.getHeight());
    }

    @Override
    public Rectangle getBoundingBox() {
        return this.boundingBox.getBounds();
    }

    @Override
    public void handleCollision(GameObject object) {
    }

    @Override
    public boolean hasCollided() {
        return false;
    }

    // set the bullet's heading (position and angle)
    public void setHeading(float x, float y, float angle) {
        this.positionX = x + 55;
        this.positionY = y + 20;
        this.angle = angle;
    }
}
