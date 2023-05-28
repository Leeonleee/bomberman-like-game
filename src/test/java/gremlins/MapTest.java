package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    // Constructing map
    @Test
    public void testMapConstruct(){
        assertNotNull(new Map());
    }

    // Reading config
    @Test
    public void testReadConfig(){
        Map map = new Map();
        map.readConfig();

        assertNotNull(map.getCurrentLives());
        assertNotNull(map.getLevels());
        assertNotNull(map.getCurrentLevel());
        assertNotNull(map.getPlayerCooldown());
        assertNotNull(map.getEnemyCooldown());

        assertNotNull(map.getNumberOfTeleportDoors());


        // No more maps
        map.setCurrentLevelIndex(3);
        map.readConfig();
    }

    // Creating map
    @Test
    public void testCreateMap() throws InvalidStonewallBorderException, InvalidNumberOfTeleportingDoorsException, InvalidNumberOfWizardSpawnException, InvalidNumberOfExitsException, InvalidSymbolException {
        Map map = new Map();
        map.readConfig();

        map.createMap(0, null,null,null,null,null, null);
        assertNotNull(map.getStonewalls());
    }

    // Grid collision
    @Test
    public void testGridCollision(){
        Map map = new Map();
        Player wizard = new Player(20, 20, null);
        wizard.setArrayX(1);
        wizard.setArrayY(1);

        wizard.setCurrentDirection(2);
        //assertTrue(map.gridCollision(wizard, 2));
    }

    // Invalid Border

    // Testing getters and setters
    @Test
    public void testGetterSetter() throws InvalidStonewallBorderException, InvalidNumberOfTeleportingDoorsException, InvalidNumberOfWizardSpawnException, InvalidNumberOfExitsException, InvalidSymbolException {
        Map map = new Map();
        map.readConfig();
        map.createMap(0, null,null,null,null,null, null);

        map.setConfigRead(true);
        assertTrue(map.isConfigRead());

        map.setNoMoreMaps(true);
        assertTrue(map.isNoMoreMaps());

        map.setCurrentLives(2);
        assertEquals(map.getCurrentLives(), 2);

        map.setLivesLoaded(true);
        assertNotNull(map.getCurrentLevelIndex());

        assertNotNull(map.getWizardSpawnX());
        assertNotNull(map.getWizardSpawnY());

        map.setPowerupActive(true);
        assertTrue(map.isPowerupActive());

        assertNotNull(map.getExit());
        assertNotNull(map.getTileGrid());
        assertNotNull(map.getBrickwalls());
        assertNotNull(map.getGremlins());
        assertNotNull(map.getGremlins());
        assertNotNull(map.getPowerups());


    }

}


