package gremlins;

abstract class Character extends Entity{

    protected int currentDirection; // direction the character is facing/moving

    public Character(int x, int y){
        super(x, y);
    }

    public void tick(int currentDirection, boolean player){

        /**
         * Moves the character by movementSpeed per frame
         * and changes its array position
         *
         * @param currentDirection, the direction that the character is facing
         * @param player, true if the character is the player, else false
         */

        this.currentDirection = currentDirection;

        if (currentDirection == 0){
            this.x -= this.movementSpeed;
            if (player){
                this.arrayX -= 1;
            }
            else{
                if(this.x % 20 == 0){
                    this.arrayX = this.x / 20;
                }
            }
        }
        if (currentDirection == 1){
            this.x += this.movementSpeed;
            if (player){
                this.arrayX += 1;
            }
            else{
                if(this.x % 20 == 0){
                    this.arrayX = this.x / 20;
                }
            }
        }
        if (currentDirection == 2){
            this.y -= this.movementSpeed;
            if (player){
                this.arrayY -= 1;
            }
            else{
                if (this.y % 20 == 0){
                    this.arrayY = this.y / 20;
                }
            }
        }
        if (currentDirection == 3){
            this.y += this.movementSpeed;
            if (player){
                this.arrayY += 1;
            }
            else{
                if (this.y % 20 == 0){
                    this.arrayY = this.y / 20;
                }
            }
        }
    }



    public int getCurrentDirection() {
        return currentDirection;
    }
    public void setCurrentDirection(int currentDirection) {
        this.currentDirection = currentDirection;
    }


}