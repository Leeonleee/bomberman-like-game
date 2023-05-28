package gremlins;

import processing.core.PImage;

import java.util.ArrayList;

public class Freeze extends Powerup{

    private ArrayList<Gremlin> gremlins;

    public Freeze(int x, int y, PImage sprite, ArrayList<Gremlin> gremlins) {
        super(x, y);
        this.powerupTimer = 0;
        this.sprite = sprite;
        this.gremlins = gremlins;

    }

    @Override
    public void powerupAbility(int millis) {

        /**
         * Activates the freeze powerup
         * and sets a random respawn time between 6 and 20 seconds
         *
         * @param millis, the amount of time the program has been running for
         */


        this.setRespawnTime(random.nextInt(20-6) + 6);
        this.powerupTimer = millis; // last time cooldown was used

        // freezes all gremlins
        for (Gremlin gremlin : gremlins){
            gremlin.setMovementSpeed(0);
            gremlin.setCanFire(false);
        }
        this.onCooldown = true;
    }

    @Override
    public void endPowerup(int millis) {
        /**
         * Ends the freeze effect of the powerup
         * @param milis, the amount of time the program has been running for
         */

        for (Gremlin gremlin : this.gremlins){
            gremlin.movementSpeed = 1;
            gremlin.setCanFire(true);
        }

    }


}
