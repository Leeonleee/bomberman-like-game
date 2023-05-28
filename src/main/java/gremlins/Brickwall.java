package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

public class Brickwall extends Wall{

    // Display
    private PImage[] sprite;
    private int frameCounter; // counter for destruction animation
    private int currentFrame; // which sprite is being displayed

    // Destruction
    private boolean destroyed; // true if its been hit by a fireball


    public Brickwall(int x, int y, PImage[] sprites) {
        super(x, y);
        this.sprite = sprites;
        this.currentFrame = 0;
        this.frameCounter = 0;
    }

    public void destruction(PApplet app){
        /**
         * Plays the destruction animation
         * Displays each frame for 4 sprites
         * @param app
         */
        if (this.currentFrame < 4){
            if (this.frameCounter < 3){
                draw(app);
                frameCounter += 1;
            }
            else{
                this.currentFrame += 1;
                draw(app);
                this.frameCounter = 0;
            }
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    @Override
    public void draw(PApplet app){
        /**
         * Draws the current frame specified by currentFrame
         */
        app.image(this.sprite[currentFrame], this.x, this.y);
    }
}