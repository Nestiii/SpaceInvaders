package edu.austral.prog2_2018c2;

public class Level {

    public int shields;
    public int movementSpeed;

    public Level(int shields,int movementSpeed){
        this.shields = shields;
        this.movementSpeed = movementSpeed;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public int getShields() {
        return shields;
    }

}
