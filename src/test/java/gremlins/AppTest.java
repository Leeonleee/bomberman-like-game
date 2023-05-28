package gremlins;

import org.junit.jupiter.api.BeforeAll;
import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import processing.core.PImage;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    // Test app
    @Test
    public void testApp(){
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
    }

    // Cooldown bar
    @Test
    public void testCooldownbar(){
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        //Player wizard = new Player(20,20,null);
        //wizard.setFireballFired(true);
        //app.cooldownBar("fireball");
    }

    // Bottom bar text
    @Test
    public void testBottomBarText() throws InvalidStonewallBorderException, InvalidNumberOfTeleportingDoorsException, InvalidNumberOfWizardSpawnException, InvalidNumberOfExitsException, InvalidSymbolException {


    }

    // Keypressed
    @Test
    public void testKeyPressed(){
        App app = new App();

        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        Player wizard = new Player(20,20,null);

        app.keyCode = 37;
        assertEquals(wizard.getX(), 20);
        app.keyPressed();
        assertEquals(wizard.getX() - 2, 18);
        app.keyReleased();

        app.loop();




    }
}
