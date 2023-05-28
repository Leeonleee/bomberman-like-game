package gremlins;

import processing.core.PImage;

public class Teleport extends Entity{
    private int index; // which portal it is

    public Teleport(int x, int y, PImage sprite, int index) {
        super(x, y);
        this.sprite = sprite;
        this.index = index;
    }


    public void teleportWizard(Player wizard, Map map){
        /**
         * Teleports the wizard from one portal to another
         * Prevents the wizard from teleporting again until they have left the portal they teleported to
         *
         * @param wizard, the player character
         * @param map, the game map
         */

        if (!wizard.isTeleported()){
                if (this.index == 1){

                    wizard.setX(map.getTeleports().get(1).getX());
                    wizard.setY(map.getTeleports().get(1).getY());
                    wizard.setArrayX(wizard.getX() / 20);
                    wizard.setArrayY(wizard.getY() / 20);
                    wizard.setTeleported(true);
                }
                else if (this.index == 2){
                    wizard.setX(map.getTeleports().get(0).getX());
                    wizard.setY(map.getTeleports().get(0).getY());
                    wizard.setArrayX(wizard.getX() / 20);
                    wizard.setArrayY(wizard.getY() / 20);
                    wizard.setTeleported(true);
                }
            }

    }





}