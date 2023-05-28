package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FreezeTest {
    // Test ability
    @Test
    public void testPowerupAbility(){
        ArrayList<Gremlin> gremlins = new ArrayList<>();
        gremlins.add(new Gremlin(20,20,null,0));
        gremlins.add(new Gremlin(20,40,null,0));

        Freeze freeze = new Freeze(80,40,null,gremlins);
        freeze.powerupAbility(0);
        for (Gremlin gremlin : gremlins){
            assertEquals(gremlin.movementSpeed, 0);
        }
    }

    // Test end powerup
    @Test
    public void testEndPowerup(){
        ArrayList<Gremlin> gremlins = new ArrayList<>();
        gremlins.add(new Gremlin(20,20,null,0));
        gremlins.add(new Gremlin(20,40,null,0));

        Freeze freeze = new Freeze(80,40,null,gremlins);
        freeze.powerupAbility(0);

        freeze.endPowerup(0);
        for (Gremlin gremlin : gremlins){
            assertEquals(gremlin.movementSpeed, 1);
        }
    }

    // Getters and setters
    @Test
    public void testGettersSetters(){
        Freeze freeze = new Freeze(80,40,null,null);

        freeze.setOnCooldown(true);
        assertTrue(freeze.isOnCooldown());

        freeze.setDurationPercentage(0.5f);
        assertEquals(freeze.getDurationPercentage(), 0.5);
    }
}
