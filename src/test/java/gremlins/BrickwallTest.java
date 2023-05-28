package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BrickwallTest {
    // Constructing brick wall
    @Test
    public void testBrickwallConstruct(){
        assertNotNull(new Brickwall(20,0, null));
    }

    // Destruction animation
    @Test
    public void testBrickwallDestruction(){

    }

    // Test getters and setters
    @Test
    public void testGetterSetter(){
        Brickwall brickwall = new Brickwall(0,0,null);
        brickwall.setDestroyed(true);
        assertTrue(brickwall.isDestroyed());
        brickwall.setDestroyed(false);
        assertFalse(brickwall.isDestroyed());
    }
}
