package edu.austral.prog2_2018c2;

import java.util.List;

public class Model implements Commons{
    Player player;
    List<Shot> shots;
    List<Alien> aliens;
    List<Level> levels;

    public Player getPlayer() {
        return player;
    }

    public List<Shot> getShots() {
        return shots;
    }

    public List<Alien> getAliens() {
        return aliens;
    }

    public List<Level> getLevels() {
        return levels;
    }
}
