package gremlins;

import processing.core.PImage;

abstract class Projectile extends Entity{
    protected int direction;
    protected boolean collided;


    public Projectile(int x, int y, int direction) {
        super(x, y);
        this.direction = direction;

        this.collided = false;
        this.movementSpeed = 4;
    }

    public void tick(){
        /**
         * Moves the projectile in its direction by movementSpeed per tick
         */
        if (this.direction == 0){
            this.x -= movementSpeed;
            if (this.x % 20 == 0){
                this.arrayX -= 1;
            }
        }
        if (this.direction == 1){
            this.x += movementSpeed;
            if (this.x % 20 == 0){
                this.arrayX += 1;
            }
        }
        if (this.direction == 2){
            this.y -= movementSpeed;
            if (this.y % 20 == 0){
                this.arrayY -= 1;
            }
        }
        if (this.direction == 3){
            this.y += movementSpeed;
            if (this.y % 20 == 0){
                this.arrayY += 1;
            }
        }
    }

    public boolean isCollided(){
        return this.collided;
    }
    public void setCollided(boolean collided){
        this.collided = collided;
    }
}