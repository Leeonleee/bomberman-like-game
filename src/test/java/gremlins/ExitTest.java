package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExitTest {

    public static App app;

    public static void runApp(){

    }

    // Constructing exit
    @Test
    public void testExitConstruct(){
        assertNotNull(new Exit(600, 600, null));
    }

    // Testing draw
    @Test
    public void testDraw(){
        //App app = new App();
        //PApplet.runSketch(new String[] { "App" }, app);
        //app.setup();
        //app.loop();
        //app.delay(1000);
        //Exit exit = new Exit(0,0,null);
        //exit.draw(app);

    }
}
