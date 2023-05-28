package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StonewallTest {
    // Constructing stone wall
    @Test
    public void testStonewallConstruct(){
        assertNotNull(new Stonewall(0,0,null));
    }
}
