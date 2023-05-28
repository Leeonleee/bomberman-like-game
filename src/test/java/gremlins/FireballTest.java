package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FireballTest {

    // Constructing fireball
    @Test
    public void testFireballConstruct(){
        assertNotNull(new Fireball(20, 20, 3, null));
    }

}
