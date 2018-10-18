package edu.austral.prog2_2018c2;

import java.util.List;

public class Level {

    int shields;
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
