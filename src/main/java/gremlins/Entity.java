package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

abstract class Entity{
    protected int x; // x position of entity
    protected int y; // y position of entity
    private int w; // width of entity
    private int h; // height of entity

    protected int arrayX; // x position in grid
    protected int arrayY; // y position in grid

    protected int movementSpeed;

    protected PImage sprite;

    public Entity(int x, int y){
        this.x = x;
        this.y = y;
        this.w = 20;
        this.h = 20;
        this.arrayX = this.x / 20;
        this.arrayY = this.y / 20;
        this.sprite = new PImage();
    }

    // Sprite collision
    public boolean collidingWith(Entity other){
        /**
         * Checks if the current object has collided with the specified object
         *
         * @param other, the object you want to check collision for
         * @return true if there is a collision, else false
         */
        return this.x + this.w > other.x &&
                this.x < other.x + other.w &&
                this.y + this.h > other.y &&
                this.y < other.y + other.h;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }


    public void setArrayX(int arrayX) {
        this.arrayX = arrayX;
    }
    public void setArrayY(int arrayY) {
        this.arrayY = arrayY;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }
    public void draw(PApplet app){
        /**
         * Draws the sprite onto the game screen
         */
        app.image(this.sprite, this.x, this.y);
    }
}