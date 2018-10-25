package edu.austral.prog2_2018c2;

public class Level {

    public int shields;
    public int movementSpeed;
    public int num;

    public Level(int number,int shields,int movementSpeed){
        this.shields = shields;
        this.movementSpeed = movementSpeed;
        num= number;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public int getShields() {
        return shields;
    }

    public int getNum() {
        return num;
    }
}
