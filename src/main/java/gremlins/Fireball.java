package gremlins;

import processing.core.PImage;

public class Fireball extends Projectile{
    public Fireball(int x, int y, int direction, PImage sprite) {
        super(x, y, direction);
        this.sprite = sprite;
    }
}
