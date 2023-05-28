package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TeleportTest {

    // Constructing teleport
    @Test
    public void testTeleportConstruct(){
        assertNotNull(new Teleport(100,80,null,0));
    }

    // Testing teleport
    @Test
    public void testWizardTeleport(){
        Map map = new Map();
        Player wizard = new Player(100,80,null);
        map.getTeleports().add(new Teleport(100,80,null,1));
        map.getTeleports().add(new Teleport(200,220,null,2));


        // Enters first portal
        map.getTeleports().get(0).teleportWizard(wizard, map);
        assertEquals(wizard.getX(), map.getTeleports().get(1).getX());
        assertEquals(wizard.getY(), map.getTeleports().get(1).getY());
        assertEquals(wizard.arrayX, wizard.getX() / 20);
        assertEquals(wizard.arrayY, wizard.getY() / 20);
        assertTrue(wizard.isTeleported());


        wizard.setTeleported(false);

        // Enters second portal
        map.getTeleports().get(1).teleportWizard(wizard, map);
        assertEquals(wizard.getX(), map.getTeleports().get(0).getX());
        assertEquals(wizard.getY(), map.getTeleports().get(0).getY());
        assertEquals(wizard.arrayX, wizard.getX() / 20);
        assertEquals(wizard.arrayY, wizard.getY() / 20);



        wizard.setTeleported(true);
        map.getTeleports().get(0).teleportWizard(wizard,map);
        assertEquals(wizard.getX(), map.getTeleports().get(0).getX());
        assertEquals(wizard.getY(), map.getTeleports().get(0).getY());
    }
}
