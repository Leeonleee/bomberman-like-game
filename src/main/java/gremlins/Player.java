package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Player extends Character{

    private PImage[] sprite;
    private boolean collided;


    // Fireballs
    private ArrayList<Fireball> fireballs;
    private int fireballTime;
    private boolean fireballFired;

    private float fireballCooldownPercentage;

    // Extension
    private boolean teleported;

    public Player(int x, int y, PImage[] sprites) {
        super(x, y);
        this.sprite = sprites;
        this.movementSpeed = 2;
        this.collided = false;

        this.fireballs = new ArrayList<>();
        this.fireballTime = -1000000000;
        this.fireballFired = false;

        this.teleported = false;
    }


    public void createFireball(PImage fireballSprite, int millis){
        /**
         * Creates a new fireball when the spacebar has been pressed
         * Sets the time the fireball was created
         * Sets fireballFired to true
         * @param fireballSprite, fireball sprite
         * @param millis, the amount of time the program has been running for
         */
        this.fireballs.add(new Fireball(this.x, this.y, this.currentDirection, fireballSprite));
        this.fireballTime = millis;
        this.fireballFired = true;
    }

    public void shootFireball(PApplet app, Map map, ArrayList<Stonewall> stonewalls, ArrayList<Brickwall> brickwalls,
                              ArrayList<Gremlin> gremlins){

        /**
         * Draws the fireball and moves it.
         *
         * Detects whether the fireball has collided with a wall
         * or a gremlin and handles it
         *
         * @param map, the game map
         * @param stonewalls, ArrayList of all the stone walls
         * @param brickwalls, ArrayList of all the brick walls
         * @param gremlins, ArrayList of all the gremlins
         */
        for (Fireball fireball : this.fireballs){
            if (!fireball.isCollided()){
                fireball.draw(app);
                fireball.tick();

                for (Gremlin gremlin : gremlins){
                    if (fireball.collidingWith(gremlin)){
                        fireball.setCollided(true);
                        gremlin.randomRespawn(this.arrayX, this.arrayY, map.getTileGrid());
                    }
                }

                for (Brickwall brickwall : brickwalls){
                    if (fireball.collidingWith(brickwall) && !brickwall.isDestroyed()){
                        brickwall.setDestroyed(true);
                        fireball.setCollided(true);
                    }
                }

                for (Stonewall stonewall : stonewalls){
                    if (fireball.collidingWith(stonewall)){
                        fireball.setCollided(true);
                    }
                }

            }
        }
    }



    public void checkOffset(){
        /**
         * Ensures that the wizard always stops on a whole tile
         */
        if (this.currentDirection == 2){
            if (this.y % 20 != 0){
                this.y -= this.movementSpeed;
            }
        }
        if (this.currentDirection == 3){
            if (this.y % 20 != 0){
                this.y += this.movementSpeed;
            }
        }
        if (this.currentDirection == 0){
            if (this.x % 20 != 0){
                this.x -= this.movementSpeed;
            }
        }
        if (this.currentDirection == 1){
            if (this.x % 20 != 0){
                this.x += this.movementSpeed;
            }
        }
    }

    public int getFireballTime() {
        return fireballTime;
    }

    public boolean isFireballFired() {
        return fireballFired;
    }
    public void setFireballFired(boolean fireballFired) {
        this.fireballFired = fireballFired;
    }

    public float getFireballCooldownPercentage() {
        return fireballCooldownPercentage;
    }

    public void setFireballCooldownPercentage(float fireballCooldownPercentage) {
        this.fireballCooldownPercentage = fireballCooldownPercentage;
    }

    public boolean isCollided() {
        return collided;
    }
    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    public boolean isTeleported() {
        return teleported;
    }

    public void setTeleported(boolean teleported) {
        this.teleported = teleported;
    }

    public ArrayList<Fireball> getFireballs() {
        return fireballs;
    }

    @Override
    public void draw(PApplet app){
        app.image(this.sprite[this.currentDirection], this.x, this.y);
    }
}