package edu.austral.prog2_2018c2;

public class Level {

    private int shields;
    private int movementSpeed;
    private int num;

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
