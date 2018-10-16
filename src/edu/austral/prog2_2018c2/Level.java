package edu.austral.prog2_2018c2;

public class Level {

    public int shields;
    public double movementSpeed;
    public int num;

    public Level(int number,int shields,double movementSpeed){
        this.shields = shields;
        this.movementSpeed = movementSpeed;
        num= number;
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public int getShields() {
        return shields;
    }

    public int getNum() {
        return num;
    }
}
