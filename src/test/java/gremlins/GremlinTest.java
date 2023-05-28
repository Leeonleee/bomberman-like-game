package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GremlinTest {

    // Constructing gremlin
    @Test
    public void testWizardConstruct(){
        assertNotNull(new Gremlin(100, 20, null, 0));
    }

    // Testing tick
    @Test
    public void testGremlinTick(){
        Gremlin gremlin = new Gremlin(20,20,null,0);
        int gremlinArrayX = gremlin.arrayX;
        int gremlinArrayY = gremlin.arrayY;
        // Left movement
        int i = 0;
        while (i < 20){
            gremlin.tick(0,false);
            i++;
        }
        assertEquals(gremlin.getX() / 20, gremlin.arrayX);

        // Right movement
        i = 0;
        while (i < 20){
            gremlin.tick(1,false);
            i++;
        }
        assertEquals(gremlin.getX() / 20, gremlin.arrayX);

        // Up movement
        i = 0;
        while (i<20){
            gremlin.tick(2,false);
            i++;
        }
        assertEquals(gremlin.getY() / 20, gremlin.arrayY);

        // Up movement
        i = 0;
        while (i<20){
            gremlin.tick(3,false);
            i++;
        }
        assertEquals(gremlin.getY() / 20, gremlin.arrayY);

    }

    // Testing gremlin wall collision
    @Test
    public void testGremlinWallCollision(){
        Map map = new Map();
        Gremlin gremlin = new Gremlin(20,20,null,0);

        gremlin.wallCollision(map);


    }

    // Random respawn position
    @Test
    public void testRandomRespawn(){
        Map map = new Map();
        Player wizard = new Player(300,300, null);
        Gremlin gremlin = new Gremlin(200,200,null, 0);
    }
}
