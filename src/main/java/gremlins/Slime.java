package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

public class Slime extends Projectile{
    public Slime(int x, int y, int direction, PImage sprite) {
        super(x, y, direction);
        this.sprite = sprite;
    }
}
