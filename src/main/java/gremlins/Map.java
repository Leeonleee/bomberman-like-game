package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import static processing.core.PApplet.loadJSONObject;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Map{

    // Config
    private boolean configRead;
    private String configPath; // config.json file path
    private int lives; // total number of lives in config
    private JSONArray levels; // All the levels in config

    // Level details
    private int currentLevelIndex;
    private int currentLives;
    private boolean livesLoaded;
    private JSONObject currentLevel; // Info for current level
    private String layoutFile; // level.txt file
    private float playerCooldown; // player cooldown for current level
    private float enemyCooldown; // enemy cooldown for current level
    private String[][] tileGrid; // array containing the .txt file contents


    // Wizard
    private int wizardSpawnX;
    private int wizardSpawnY;

    // ArrayLists
    private ArrayList<Stonewall> stonewalls;
    private ArrayList<Brickwall> brickwalls;
    private ArrayList<Gremlin> gremlins;
    private ArrayList<Powerup> powerups;
    private ArrayList<Teleport> teleports;

    // Objects
    private Exit exit;

    // Powerups
    private boolean powerupActive;

    // Game over
    private boolean noMoreMaps; // true if there are no more maps

    // Error checking
    private int numberOfExits;
    private int numberOfSpawns;
    private int numberOfTeleportDoors;
    private boolean invalidStonewallBorder;

    public Map(){
        this.configPath = "config.json";
        this.tileGrid = new String[36][33];
        this.livesLoaded = false;
        this.configRead = false;

        this.invalidStonewallBorder = false;
        this.noMoreMaps = false;
        this.numberOfSpawns = 0;
        this.numberOfExits = 0;
        this.numberOfTeleportDoors = 0;

        this.stonewalls = new ArrayList<>();
        this.brickwalls = new ArrayList<>();
        this.gremlins = new ArrayList<>();
        this.powerups = new ArrayList<>();
        this.teleports = new ArrayList<>(2);
    }

    public void readConfig(){
        /**
         * Reads the config.json file
         * and saves the contents to variables.
         * Once the next level is reached, currentLevelIndex increases
         * which will allow this method to read the next map
         *
         * Adds the content of the .txt file into an array
         */


        // Reading the config file
        JSONObject conf = loadJSONObject(new File(this.configPath));

        // Non level specific
        this.lives = conf.getInt("lives");
        if (!this.livesLoaded){
            this.currentLives = this.lives;
            this.livesLoaded = true;
        }
        this.levels = conf.getJSONArray("levels");

        // Level specific
        try{
            this.currentLevel = this.levels.getJSONObject(this.currentLevelIndex);
        }
        catch (Exception e){
            this.noMoreMaps = true; // if there are no more maps
        }

        this.layoutFile = this.currentLevel.getString("layout");
        this.playerCooldown = this.currentLevel.getFloat("wizard_cooldown");
        this.enemyCooldown = this.currentLevel.getFloat("enemy_cooldown");

        // Resetting counters
        this.numberOfExits = 0;
        this.numberOfSpawns = 0;
        this.numberOfTeleportDoors = 0;
        this.invalidStonewallBorder = false;

        // Reading the tile grid
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.layoutFile));
            String line; // line of text

            for (int row = 0; row < 33; row++){
                line = br.readLine();
                for (int col = 0; col < 36; col++){

                    String tile = String.valueOf(line.charAt(col));
                    if (tile.equals("W")){ // Wizard spawn
                        this.tileGrid[col][row] = "W";

                        invalidBorder(row, col);
                    }
                    else if (tile.equals("X")){ // Stone walls
                        this.tileGrid[col][row] = "X";
                    }
                    else if (tile.equals("B")){ // Brick walls
                        this.tileGrid[col][row] = "B";
                        invalidBorder(row, col);
                    }
                    else if (tile.equals(" ")){ // Blank space
                        this.tileGrid[col][row] = " ";
                        invalidBorder(row, col);
                    }
                    else if (tile.equals("G")){ // Gremlin
                        this.tileGrid[col][row] = "G";
                        invalidBorder(row, col);
                    }
                    else if (tile.equals("E")){ // Exit
                        this.tileGrid[col][row] = "E";
                        invalidBorder(row, col);
                    }
                    else if (tile.equals("F")){ // Freeze power-up
                        this.tileGrid[col][row] = "F";
                        invalidBorder(row, col);
                    }
                    else if (tile.equals("D")){ // Teleporting doors
                        this.tileGrid[col][row] = "D";
                        invalidBorder(row, col);
                    }
                    else{
                        this.tileGrid[col][row] = tile;
                    }
                }
            }
        }
        catch (Exception e){

        }
    }

    public void createMap(int millis, PImage stonewallSprite, PImage[] brickwallSprite,
                          PImage exitSprite, PImage gremlinSprite, PImage freezePowerupSprite,
                          PImage teleportDoorSprite) throws InvalidNumberOfTeleportingDoorsException, InvalidNumberOfWizardSpawnException, InvalidStonewallBorderException, InvalidNumberOfExitsException, InvalidSymbolException {

        /**
         * Creates all the map objects except for wizard and gremlins
         *
         *
         * @param millis, the amount of time the program has been running for
         * @param stonewallSprite, stone wall sprites
         * @param brickwallSprite, brick wall sprites
         * @param exitSprite, exit door sprite
         * @param gremlinSprite, gremlin sprite
         * @param freezePowerupSprite, freeze powerup sprite
         * @param teleportDoorSprite, portal sprite
         *
         * @throws InvalidSymbolException, if an invalid character is detected in the .txt file
         * @throws InvalidNumberOfExitsException, if the number of exits is not equal to 1
         * @throws InvalidNumberOfTeleportingDoorsException, if the number of portals is not 0 or 2
         * @throws InvalidNumberOfWizardSpawnException, if the number of wizard spawns is not 1
         * @throws InvalidStonewallBorderException, if the map is not surrounded by stone walls
         */


        // Clearing map
        this.stonewalls.clear();
        this.brickwalls.clear();
        this.gremlins.clear();
        this.powerups.clear();
        this.teleports.clear();


        int tileX = 0; // x position
        int tileY = 0; // y position

        for (int row = 0; row < 33; row++){
            for (int col = 0; col < 36; col++){

                if (this.tileGrid[col][row] == "X"){
                    this.stonewalls.add(new Stonewall(tileX, tileY, stonewallSprite));
                }
                else if (this.tileGrid[col][row] == "B"){
                    this.brickwalls.add(new Brickwall(tileX, tileY, brickwallSprite));
                }
                else if (this.tileGrid[col][row] == "E"){
                    this.exit = new Exit(tileX, tileY, exitSprite);
                    this.numberOfExits += 1;
                }
                else if (this.tileGrid[col][row] == "G"){
                    this.gremlins.add(new Gremlin(tileX, tileY, gremlinSprite, millis));
                }

                else if (this.tileGrid[col][row] == "W"){
                    this.wizardSpawnX = tileX;
                    this.wizardSpawnY = tileY;
                    this.numberOfSpawns += 1;
                }

                else if (this.tileGrid[col][row] == "F"){
                    this.powerups.add(new Freeze(tileX, tileY, freezePowerupSprite, this.gremlins));
                }
                else if (this.tileGrid[col][row] == "D"){
                    this.numberOfTeleportDoors += 1;
                    this.teleports.add(new Teleport(tileX, tileY, teleportDoorSprite, this.numberOfTeleportDoors));
                }
                else if (this.tileGrid[col][row] == " "){
                }
                else{
                    throw new InvalidSymbolException(this.layoutFile, this.tileGrid[col][row], col, row);
                }

                tileX += 20;
            }
            tileX = 0;
            tileY += 20;
        }


        if (this.numberOfExits != 1){
            throw new InvalidNumberOfExitsException(this.layoutFile);
        }

        if (this.invalidStonewallBorder){
            throw new InvalidStonewallBorderException(this.layoutFile);
        }

        if (this.numberOfSpawns != 1){
            throw new InvalidNumberOfWizardSpawnException(this.layoutFile);
        }

        if (this.numberOfTeleportDoors != 2){
            if (this.numberOfTeleportDoors == 0){
                ;
            }
            else{
                throw new InvalidNumberOfTeleportingDoorsException(this.layoutFile, this);
            }
        }
    }

    public void drawMap(PApplet app){ // Draws stationary objects
        /**
         * Draws the stationary objects onto the map
         */
        for (Stonewall stonewall : this.stonewalls){
            stonewall.draw(app);
        }

        for (Brickwall brickwall : this.brickwalls){
            if (brickwall.isDestroyed()){
                brickwall.destruction(app);
                this.tileGrid[brickwall.getX() / 20][brickwall.getY() / 20] = " ";
            }
            else{
                brickwall.draw(app);
            }
        }

        for (Teleport door : this.teleports){
            door.draw(app);
        }


        this.exit.draw(app);
    }

    public boolean gridCollision(Entity object, int direction){
        /**
         * Collision detection for the wizard and gremlin's movement
         * Checks if the tile in front of the object is a wall
         *
         * @param object, the object you want to check collision for
         * @param direction, the direction the object is facing
         */
        int increment = 1;

        if (direction == 0){
            if (this.tileGrid[object.arrayX - increment][object.arrayY] == "X" ||
                    tileGrid[object.arrayX - 1][object.arrayY] == "B"){
                return true;
            }
        }
        else if (direction == 1){
            if (this.tileGrid[object.arrayX + increment][object.arrayY] == "X" ||
                    tileGrid[object.arrayX + 1][object.arrayY] == "B"){
                return true;
            }
        }
        else if (direction == 2){
            if (this.tileGrid[object.arrayX][object.arrayY - increment] == "X" ||
                    this.tileGrid[object.arrayX][object.arrayY - increment] == "B"){
                return true;
            }
        }
        else if (direction == 3){
            if (this.tileGrid[object.arrayX][object.arrayY + increment] == "X" ||
                    this.tileGrid[object.arrayX][object.arrayY + increment] == "B"){
                return true;
            }
        }

        return false;
    }


    public void invalidBorder(int row, int col){
        if (row == 0 || row == 32 || col == 0 || col == 35){
            this.invalidStonewallBorder = true;
        }
    }


    public JSONArray getLevels() {
        return levels;
    }

    public boolean isConfigRead() {
        return configRead;
    }
    public void setConfigRead(boolean configRead) {
        this.configRead = configRead;
    }

    public float getPlayerCooldown() {
        return playerCooldown;
    }
    public float getEnemyCooldown() {
        return enemyCooldown;
    }

    public void setCurrentLevelIndex(int currentLevelIndex) {
        this.currentLevelIndex = currentLevelIndex;
    }

    public boolean isNoMoreMaps() {
        return noMoreMaps;
    }
    public void setNoMoreMaps(boolean noMoreMaps) {
        this.noMoreMaps = noMoreMaps;
    }

    public int getCurrentLives() {
        return currentLives;
    }
    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }

    public void setLivesLoaded(boolean livesLoaded) {
        this.livesLoaded = livesLoaded;
    }

    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }

    public int getWizardSpawnX() {
        return wizardSpawnX;
    }
    public int getWizardSpawnY() {
        return wizardSpawnY;
    }

    public boolean isPowerupActive() {
        return powerupActive;
    }
    public void setPowerupActive(boolean powerupActive) {
        this.powerupActive = powerupActive;
    }

    public int getNumberOfTeleportDoors() {
        return numberOfTeleportDoors;
    }


    public Exit getExit() {
        return exit;
    }

    public String[][] getTileGrid() {
        return tileGrid;
    }

    public ArrayList<Stonewall> getStonewalls() {
        return stonewalls;
    }
    public ArrayList<Brickwall> getBrickwalls() {
        return brickwalls;
    }
    public ArrayList<Gremlin> getGremlins() {
        return gremlins;
    }
    public ArrayList<Powerup> getPowerups() {
        return powerups;
    }

    public ArrayList<Teleport> getTeleports() {
        return teleports;
    }

    public Object getCurrentLevel() {
        return this.currentLevelIndex + 1;
    }
}


class InvalidSymbolException extends Exception{
    public InvalidSymbolException(String layoutFile, String symbol, int col, int row){
        super("Invalid symbol "+ symbol + " in " + layoutFile + " at "+ col +", " + row +". Please check " + layoutFile + " and restart the application");
    }
}
class InvalidNumberOfExitsException extends Exception{
    public InvalidNumberOfExitsException(String layoutFile){
        super("Invalid number of exits in " + layoutFile + ". Please check " + layoutFile + " and restart the application");
    }
}
class InvalidNumberOfWizardSpawnException extends Exception{
    public InvalidNumberOfWizardSpawnException(String layoutFile){
        super("Invalid number of wizard spawns in " + layoutFile + ". Please check " + layoutFile + " and restart the application");
    }
}
class InvalidStonewallBorderException extends Exception{
    public InvalidStonewallBorderException(String layoutFile){
        super("Map not surrounded by stone walls. Please check " + layoutFile + " and restart the application");
    }
}
class InvalidNumberOfTeleportingDoorsException extends Exception{
    public InvalidNumberOfTeleportingDoorsException(String layoutFile, Map map){
        super(map.getNumberOfTeleportDoors()+" teleporting doors in " + layoutFile + ". Please check " + layoutFile + " to ensure there are either 0 or 2 doors and restart the application");
    }
}

