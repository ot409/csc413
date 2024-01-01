package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Animation {
    float positionX, positionY; // position of the animation
    private List<BufferedImage> animationFrames; // list of frames to be displayed in the animation
    private long lastUpdateTime = 0;   // time tracking variables for animation update
    private long frameDelay = 50; // delay between frame updates
    private int currentFrameIndex = 0; // current frame being displayed

    private boolean isRunning = false; // flag to indicate if the animation is currently running

    // constructor for the animation class
    public Animation(float initialX, float initialY, List<BufferedImage> frames) {
        this.positionX = initialX;
        this.positionY = initialY;
        this.animationFrames = frames;
        isRunning = true; // animation starts running upon creation
    }

    // update the animation's state
    public void update(){
        // check if it's time to update the frame
        if (lastUpdateTime + frameDelay < System.currentTimeMillis()) {
            this.lastUpdateTime = System.currentTimeMillis();
            this.currentFrameIndex++;

            // check if the animation has reached its end
            if(this.currentFrameIndex == this.animationFrames.size()){
                isRunning = false; // stop the animation
            }
        }
    }

    // draw the current frame of the animation
    public void drawImage(Graphics2D g2d){
        if(isRunning){
            // draw the current frame at the specified position
            g2d.drawImage(this.animationFrames.get(currentFrameIndex), (int)positionX, (int)positionY, null);
        };
    }
}
