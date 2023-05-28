package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SlimeTest {

    // Constructing slime
    @Test
    public void testSlimeConstruct(){
        assertNotNull(new Slime(200, 200, 3, null));
    }
}
