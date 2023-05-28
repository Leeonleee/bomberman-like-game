package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    // Constructing wizard
    @Test
    public void testPlayerConstruct(){
        assertNotNull(new Player(20, 20, null));
    }

    // Tick wizard
    @Test
    public void testPlayerTick(){
        Player wizard = new Player(20,20, null);
        int wizardX = wizard.getX();
        int wizardY = wizard.getY();

        // Left movement
        wizard.tick(0, true);
        assertEquals(wizardX - 2, wizard.getX());
        // Right movement
        wizard.tick(1, true);
        assertEquals(wizardX, wizard.getX());
        // Up movement
        wizard.tick(2, true);
        assertEquals(wizardY - 2, wizard.getY());
        // Down movement
        wizard.tick(3,true);
        assertEquals(wizardY, wizard.getY());
    }

    // Test getters in Player
    @Test
    public void testGetters(){
        Player wizard = new Player(20,20,null);
        // Getters
        assertEquals(wizard.getX(), 20);
        assertEquals(wizard.getY(),20);
        assertNotNull(wizard.getCurrentDirection());
        assertNotNull(wizard.getFireballCooldownPercentage());
        assertNotNull(wizard.getFireballs());
        assertNotNull(wizard.getFireballTime());
        assertNotNull(wizard.isCollided());
        assertNotNull(wizard.isFireballFired());
        assertNotNull(wizard.isTeleported());
    }

    // Testing setters
    @Test
    public void testSetters(){
        Player wizard = new Player(20,20,null);
        wizard.setCurrentDirection(3);
        assertEquals(wizard.getCurrentDirection(), 3);

        wizard.setX(100);
        assertEquals(wizard.getX(), 100);

        wizard.setY(100);
        assertEquals(wizard.getY(), 100);

        wizard.setArrayX(10);
        assertEquals(wizard.arrayX, 10);

        wizard.setArrayY(10);
        assertEquals(wizard.arrayY, 10);

        wizard.setMovementSpeed(2);
        assertEquals(wizard.movementSpeed, 2);

        wizard.setCollided(true);
        assertTrue(wizard.isCollided());

        wizard.setFireballCooldownPercentage(0.5F);
        assertEquals(wizard.getFireballCooldownPercentage(), 0.5);

        wizard.setFireballFired(true);
        assertTrue(wizard.isFireballFired());
    }

    // Test collision
    @Test
    public void testCollidingWith(){
        Player wizard = new Player(20,20,null);
        Gremlin gremlin = new Gremlin(20,40,null, 2);
        assertFalse(wizard.collidingWith(gremlin));
        assertFalse(gremlin.collidingWith(wizard));

        // Test wizard bottom collision
        wizard.tick(3, true);
        gremlin.tick(2,false);
        assertTrue(wizard.collidingWith(gremlin));
        assertTrue(gremlin.collidingWith(wizard));

        // Test wizard top collision
        wizard.setX(20);
        wizard.setY(38);
        gremlin.setX(20);
        gremlin.setY(21);
        assertTrue(wizard.collidingWith(gremlin));

        // Test wizard right side collision
        wizard.setX(20);
        wizard.setY(20);
        gremlin.setX(38);
        gremlin.setY(20);
        assertTrue(wizard.collidingWith(gremlin));

        // Test wizard left side collision
        gremlin.setX(20);
        gremlin.setY(20);
        wizard.setX(38);
        wizard.setY(20);
        assertTrue(wizard.collidingWith(gremlin));

    }

    // Test creating fireball
    @Test
    public void testCreateFireball(){
        Player wizard = new Player(20,20,null);
        wizard.createFireball(null, 0);
        assertNotNull(wizard.getFireballs());
    }
}
