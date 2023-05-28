package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Random;

public class Gremlin extends Character{

    private Random random;

    // Slimes
    private ArrayList<Slime> slimes;
    private int slimeTimer; // tracks last time slime was fired
    private boolean canFire; // if true, the slime can fire a slime

    // Respawn
    private int xRespawnPosition; // x position of respawn
    private int yRespawnPosition; // y position of respawn
    private int randomDirection; // direction the gremlin will face


    public Gremlin(int x, int y, PImage sprite, int millis) {
        super(x, y);
        //this.arrayX = x / 20;
        //this.arrayY = y / 20;

        this.random = new Random();
        this.currentDirection = random.nextInt(4);
        this.sprite = sprite;
        this.movementSpeed = 1;

        this.slimes = new ArrayList<>();
        this.slimeTimer = millis;
        this.canFire = true;
    }

    public void wallCollision(Map map){
        /**
         * Detects if the gremlin has collided with a wall
         * and determines what direction it should turn
         * @param map, the game map
         */
        if (currentDirection == 0){
            if (map.gridCollision(this, 0)){
                // Case 1: Both sides free
                if (!map.gridCollision(this, 2) && !map.gridCollision(this, 3)){
                    this.currentDirection = random.nextInt(2) + 2;
                }
                // Case 2: Left side wall
                if (!map.gridCollision(this, 2) && map.gridCollision(this, 3)){
                    this.currentDirection = 2;
                }
                // Case 3: Right side wall
                if (map.gridCollision(this, 2) && !map.gridCollision(this, 3)){
                    this.currentDirection = 3;
                }
                // Case 4: All sides walls
                if (map.gridCollision(this, 2) && map.gridCollision(this, 3)){
                    this.currentDirection = 1;
                }
            }
        }
        if (currentDirection == 1){
            if (map.gridCollision(this, 1)){
                // Case 1: Both sides free
                if (!map.gridCollision(this, 2) && !map.gridCollision(this, 3)){
                    this.currentDirection = random.nextInt(2) + 2;
                }
                // Case 2: Left side wall
                if (map.gridCollision(this, 2) && !map.gridCollision(this, 3)){
                    this.currentDirection = 3;
                }
                // Case 3: Right side wall
                if (!map.gridCollision(this, 2) && map.gridCollision(this, 3)){
                    this.currentDirection = 2;
                }
                // Case 4: All sides walls
                if (map.gridCollision(this, 2) && map.gridCollision(this, 3)){
                    this.currentDirection = 0;
                }
            }
        }
        if (currentDirection == 2){
            if (map.gridCollision(this, 2)){
                if (map.gridCollision(this, 2)){
                    // Case 1: Both sides free
                    if (!map.gridCollision(this, 0) && !map.gridCollision(this, 1)){
                        this.currentDirection = random.nextInt(2);
                    }
                    // Case 2: Left side wall
                    if (map.gridCollision(this, 0) && !map.gridCollision(this, 1)){
                        this.currentDirection = 1;
                    }
                    // Case 3: Right side wall
                    if (!map.gridCollision(this, 0) && map.gridCollision(this, 1)){
                        this.currentDirection = 0;
                    }
                    // Case 4: All sides walls
                    if (map.gridCollision(this, 0) && map.gridCollision(this, 1)){
                        this.currentDirection = 3;
                    }
                }
            }
        }
        if (currentDirection == 3){
            if (map.gridCollision(this, 3)){
                if (map.gridCollision(this, 3)){
                    // Case 1: Both sides free
                    if (!map.gridCollision(this, 0) && !map.gridCollision(this, 1)){
                        this.currentDirection = random.nextInt(2);
                    }
                    // Case 2: Left side wall
                    if (!map.gridCollision(this, 0) && map.gridCollision(this, 1)){
                        this.currentDirection = 0;
                    }
                    // Case 3: Right side wall
                    if (map.gridCollision(this, 0) && !map.gridCollision(this, 1)){
                        this.currentDirection = 1;
                    }
                    // Case 4: All sides walls
                    if (map.gridCollision(this, 0) && map.gridCollision(this, 1)){
                        this.currentDirection = 2;
                    }
                }
            }
        }

    }


    public void randomRespawn(int wizardArrayX, int wizardArrayY, String[][] tileGrid){
        /**
         * Respawns the gremlin at least 10 tiles away from the wizard
         * after being hit by a fireball.
         *
         * First randomly chooses a tile 10 tiles away from the wizard
         * then checks to see if its valid, else randomly choose again
         * until a valid tile is chosen.
         *
         * @param wizardArrayX, X position of the wizard in the grid
         * @param wizardArrayY, Y position of the wizard in the grid
         * @param tileGrid, array containing the contents of the map from the .txt file
         */
        while (true){
            randomDirection = random.nextInt(4);
            // Left
            if (randomDirection == 0){
                if (wizardArrayX - 10 > 2){
                    this.xRespawnPosition = random.nextInt(wizardArrayX - 10);
                    this.yRespawnPosition = random.nextInt(32);
                }
                else{
                    randomDirection = random.nextInt(4);
                }

            }
            // Right
            else if (randomDirection == 1){
                if (wizardArrayX + 10 < 33){
                    this.xRespawnPosition = random.nextInt(36 - (wizardArrayX + 10)) + (wizardArrayX + 10);
                    this.yRespawnPosition = random.nextInt(32);
                }
                else{
                    randomDirection = random.nextInt(4);
                }
            }
            // Above
            else if (randomDirection == 2){
                if (wizardArrayY - 10 > 2){
                    this.yRespawnPosition = random.nextInt(wizardArrayY - 10);
                    this.xRespawnPosition = random.nextInt(35);
                }
                else{
                    randomDirection = random.nextInt(4);
                }
            }
            // Below
            else if (randomDirection == 3){
                if (wizardArrayY + 10 < 30){
                    this.yRespawnPosition = random.nextInt(33 - (wizardArrayY + 10)) + (wizardArrayY + 10);
                    this.xRespawnPosition = random.nextInt(35);
                }
                else{
                    randomDirection = random.nextInt(4);
                }
            }

            if (tileGrid[this.xRespawnPosition][this.yRespawnPosition] == " "){
                this.x = this.xRespawnPosition * 20;
                this.y = this.yRespawnPosition * 20;
                this.arrayX = this.xRespawnPosition;
                this.arrayY = this.yRespawnPosition;
                break;
            }
        }
    }

    public void createSlime(int millis, PImage slimeSprite, Map map){

        /**
         * Creates a new slime once the cooldown is over
         *
         * @param millis, the amount of time the program has been running for
         * @param slimeSprite, the sprite of the slime
         * @param map, the game map
         */
        if (millis - this.slimeTimer > map.getEnemyCooldown() * 1000){
            this.slimeTimer = millis;
            this.slimes.add(new Slime(this.x, this.y, this.currentDirection, slimeSprite));
        }
    }
    public void shootSlime(PApplet app, Map map, Player wizard){
        /**
         * Allows the gremlin to shoot the slime
         * also handles what happens when the slime collides
         * with different types of objects
         *
         * @param map, the game map
         * @param wizard, the wizard character
         */
        for (Slime slime : this.slimes){
            if (!slime.isCollided() && this.canFire){
                slime.draw(app);
                slime.tick();
            }
            for (Stonewall stonewall : map.getStonewalls()){
                if (slime.collidingWith(stonewall)){
                    slime.setCollided(true);
                }
            }
            for (Brickwall brickwall : map.getBrickwalls()){
                if (slime.collidingWith(brickwall) && !brickwall.isDestroyed()){
                    slime.setCollided(true);
                }
            }
            for (Fireball fireball : wizard.getFireballs()){
                if (slime.collidingWith(fireball) && !fireball.isCollided() && !slime.isCollided()){
                    slime.setCollided(true);
                    fireball.setCollided(true);
                }
            }
            if (slime.collidingWith(wizard) && !slime.isCollided() && !wizard.isCollided()){
                wizard.setCollided(true);
                map.setCurrentLives(map.getCurrentLives() - 1);
                map.setConfigRead(false);
            }
        }
    }

    public void setCanFire(boolean canFire) {
        this.canFire = canFire;
    }

}