package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

public class App extends PApplet{

    // Default settings
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int FPS = 60;

    // Wizard
    private Player wizard;

    // Sprites
    private PImage[] wizardSprites;
    private PImage gremlinSprite;

    private PImage fireballSprite;
    private PImage slimeSprite;

    private PImage stonewallSprite;
    private PImage[] brickwallSprites;

    private PImage exitSprite;

    private PImage freezePowerupSprite;
    private PImage teleportDoorSprite;


    // Config
    private Map map;

    // Game
    private boolean gameOver;


    public App(){
        // Sprites
        this.wizardSprites = new PImage[4];
        this.stonewallSprite = new PImage();
        this.brickwallSprites = new PImage[5];
        this.gremlinSprite = new PImage();
        this.slimeSprite = new PImage();
        this.freezePowerupSprite = new PImage();
        this.teleportDoorSprite = new PImage();
        this.fireballSprite = new PImage();
        this.exitSprite = new PImage();
    }

    public void settings(){
        size(WIDTH, HEIGHT);
    }

    public void setup(){
        frameRate(FPS);

        // Sprites;
        for (int i = 0; i < 4; i++){
            this.wizardSprites[i] = loadImage(this.getClass().getResource("wizard" + i + ".png").getPath().replace("%20", " "));
        }
        this.gremlinSprite = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
        this.fireballSprite = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));
        this.slimeSprite = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));

        this.exitSprite = loadImage(this.getClass().getResource("exit.png").getPath().replace("%20", " "));

        this.stonewallSprite = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
        this.brickwallSprites[0] = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
        for (int i = 0; i < 4; i++){
            this.brickwallSprites[i+1] = loadImage(this.getClass().getResource("brickwall_destroyed" + i + ".png").getPath().replace("%20", " "));
        }

        this.freezePowerupSprite = loadImage(this.getClass().getResource("freeze_powerup.png").getPath().replace("%20", " "));
        this.teleportDoorSprite = loadImage(this.getClass().getResource("blue_portal.png").getPath().replace("%20", " "));

        // Config
        this.map = new Map();

        // Game
        this.gameOver = false;
    }

    public void draw(){
        background(191, 152, 118);

        if (!this.map.isConfigRead()){
            // Reading map
            try{
                this.map.readConfig();
                this.map.createMap(millis(), this.stonewallSprite, this.brickwallSprites,
                                    this.exitSprite, this.gremlinSprite, this.freezePowerupSprite,
                                    this.teleportDoorSprite);
                this.wizard = new Player(this.map.getWizardSpawnX(), this.map.getWizardSpawnY(), this.wizardSprites);

                this.map.setConfigRead(true);
            }
            catch (NullPointerException e){
                this.map.setNoMoreMaps(true);
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }

        }

        // If game over / win
        if (this.map.isNoMoreMaps() || this.map.getCurrentLives() <= 0){
            this.gameOver = true;
            background(255,255,255);
            fill(0,0,0);
            textSize(40);
            // If the player reaches the end
            if (this.map.isNoMoreMaps()){
                this.map.setCurrentLevelIndex(0);
                text("You Win!", 250, 360);
                textSize(25);
                text("Press any key to restart", 220, 400);
            }
            // If the player loses all lives
            if (this.map.getCurrentLives() <= 0){
                text("GAME OVER", 240, 360);
                textSize(25);
                text("Press any key to restart", 220, 400);
            }
        }
        else{
            this.gameOver = false;
            this.map.drawMap(this);

            this.wizard.draw(this);
            this.wizard.checkOffset();

            this.wizard.shootFireball(this, this.map, this.map.getStonewalls(), this.map.getBrickwalls(), this.map.getGremlins());

            for (Gremlin gremlin : this.map.getGremlins()){
                gremlin.wallCollision(this.map);
                gremlin.draw(this);
                gremlin.tick(gremlin.getCurrentDirection(), false);

                gremlin.createSlime(millis(), this.slimeSprite, this.map);
                gremlin.shootSlime(this, this.map, this.wizard);

                // If wizard touches gremlin
                if (gremlin.collidingWith(this.wizard) && !this.wizard.isCollided()){
                    this.wizard.setCollided(true);
                    this.map.setCurrentLives(map.getCurrentLives() - 1);
                    this.map.setConfigRead(false);
                }
            }

            for (Powerup powerup : this.map.getPowerups()){
                // Timer to see if powerup can respawn
                if (millis() - powerup.getPowerupTimer() > powerup.getRespawnTime() * 1000){
                    powerup.draw(this);
                    powerup.setOnCooldown(false);
                    // If powerup is not on cooldown and wizard collides, activate powerup
                    if (!powerup.isOnCooldown() && powerup.collidingWith(this.wizard)){
                        powerup.powerupAbility(millis());
                        this.map.setPowerupActive(true);
                    }
                }
                // If powerup duration is over, end effects
                if (millis() - powerup.getPowerupTimer() > powerup.getDuration() * 1000){
                    powerup.endPowerup(millis());
                    this.map.setPowerupActive(false);
                }
            }

            // Checks if there are portals on the map
            if (!this.map.getTeleports().isEmpty()){
                for (Teleport door : this.map.getTeleports()){
                    if (wizard.collidingWith(door)){
                        door.teleportWizard(this.wizard, this.map);
                    }
                }
                if (!wizard.collidingWith(this.map.getTeleports().get(0)) && !wizard.collidingWith(this.map.getTeleports().get(1))){
                    this.wizard.setTeleported(false);
                }
            }

            // If the wizard reaches the exit
            if (this.wizard.collidingWith(this.map.getExit())){
                this.map.setConfigRead(false);
                this.map.setCurrentLevelIndex(this.map.getCurrentLevelIndex() + 1);
            }



            cooldownBar("fireball");
            cooldownBar("powerup");
            bottomBarText();

        }
    }

    public void cooldownBar(String type){
        /**
         * Draws the cooldown bar on the bottom bar.
         * Used for the fireball and powerup duration
         * @param type, Type of cooldown bar
         */

        if (type.equals("fireball")){
            if (this.wizard.isFireballFired()){
                fill(255,255,255);
                // Cooldown bar background
                rect(600, 683, 100 ,7);
                // Percentage of the cooldown that has passed
                this.wizard.setFireballCooldownPercentage((millis() - this.wizard.getFireballTime())
                        / (this.map.getPlayerCooldown() * 1000));
                if (this.wizard.getFireballCooldownPercentage() >= 1){
                    this.wizard.setFireballCooldownPercentage(1);
                }
                fill(0,0,0);
                // Cooldown bar
                rect(600,683,100 * this.wizard.getFireballCooldownPercentage(), 7);
                // Cooldown over
                if (millis() - this.wizard.getFireballTime() > this.map.getPlayerCooldown() * 1000){
                    this.wizard.setFireballFired(false);
                }
            }
        }
        if (type.equals("powerup")){
            if (this.map.isPowerupActive()){
                fill(255,255,255);
                // Cooldown bar background
                rect(600,700,100,7);
                Powerup powerup = this.map.getPowerups().get(0);
                // Percentage of the duration left
                float timeRemaining = 1 - ((millis() - powerup.getPowerupTimer()) / (powerup.getDuration() * 1000));
                powerup.setDurationPercentage(timeRemaining);

                fill(135, 236, 255);
                // Cooldown bar
                rect(600,700,100 * powerup.getDurationPercentage(), 7);
                //}
            }
        }
    }

    public void bottomBarText(){
        /**
         * Draws the lives remaining icons and current level
         */
        fill(255,255,255);
        textSize(20);
        // Lives
        text("Lives: ", 8, 697);
        // Displays the amount of lives left as wizard sprites
        for (int lives = 0; lives < this.map.getCurrentLives(); lives++){
            image(this.wizardSprites[1], 70 + (20 * lives), 680);
        }
        // Levels
        text("Level", 200, 697);
        text((this.map.getCurrentLevel()) + "/" + this.map.getLevels().size(), 260, 697);
    }

    public void keyPressed(){
        /**
         * Detects which keys are pressed
         * If arrow keys are pressed, tick wizard
         * If spacebar is pressed and fireball is not on cooldown, shoot fireball
         * If game is over, any key press will restart the game
         */
        // If game over, any key press will restart
        if (this.gameOver){
            if (keyPressed == true){
                this.map.setConfigRead(false);
                this.map.setLivesLoaded(false);
                this.map.setNoMoreMaps(false);
            }
        }
        else{
            if (this.wizard.getX() % 20 == 0 && this.wizard.getY() % 20 == 0){
                if (key == CODED){
                    if (keyCode == LEFT){
                        if (!this.map.gridCollision(this.wizard, 0)){
                            this.wizard.tick(0, true);
                        }
                        else{
                            this.wizard.setCurrentDirection(0);
                        }
                    }
                    if (keyCode == RIGHT){
                        if (!this.map.gridCollision(this.wizard, 1)){
                            this.wizard.tick(1, true);
                        }
                        else{
                            this.wizard.setCurrentDirection(1);
                        }
                    }
                    if (keyCode == UP){
                        if (!this.map.gridCollision(this.wizard, 2)){
                            this.wizard.tick(2, true);
                        }
                        else{
                            this.wizard.setCurrentDirection(2);
                        }
                    }
                    if (keyCode == DOWN){
                        if (!this.map.gridCollision(this.wizard, 3)){
                            this.wizard.tick(3, true);
                        }
                        else{
                            this.wizard.setCurrentDirection(3);
                        }
                    }
                }
            }

            if (key == ' '){
                // If not on cooldown
                if (millis() - this.wizard.getFireballTime() > this.map.getPlayerCooldown() * 1000){
                    this.wizard.createFireball(this.fireballSprite, millis());
                }
            }
        }
    }

    public static void main(String[] args){
        PApplet.main("gremlins.App");
    }
}