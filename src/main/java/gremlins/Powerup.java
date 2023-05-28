package gremlins;

import java.util.Random;

abstract class Powerup extends Entity{

    protected Random random;
    protected float duration;
    protected boolean onCooldown;
    protected int powerupTimer; // Timer for when powerup was used

    protected int respawnTime;

    protected float durationPercentage;

    public Powerup(int x, int y) {
        super(x, y);
        this.duration = 5;
        this.random = new Random();
        this.respawnTime = random.nextInt(10 - 4) + 4; // Spawns in between 4-10 seconds
    }


    /**
     * {@inheritDoc}
     * Starts the effects of the powerup
     * @param millis, the amount of time the program has been running for
     */
    abstract void powerupAbility(int millis);


    /**
     * {@inheritDoc}
     * Ends the effects of the powerup
     * @param millis, the amount of time the program has been running for
     */
    abstract void endPowerup(int millis);


    public void setRespawnTime(int respawnTime){
        this.respawnTime = respawnTime;
    }
    public int getRespawnTime() {
        return respawnTime;
    }


    public int getPowerupTimer() {
        return powerupTimer;
    }

    public float getDuration() {
        return duration;
    }


    public boolean isOnCooldown() {
        return onCooldown;
    }
    public void setOnCooldown(boolean onCooldown) {
        this.onCooldown = onCooldown;
    }

    public float getDurationPercentage() {
        return durationPercentage;
    }
    public void setDurationPercentage(float durationPercentage) {
        this.durationPercentage = durationPercentage;
    }
}