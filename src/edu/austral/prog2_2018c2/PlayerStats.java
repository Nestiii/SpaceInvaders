package edu.austral.prog2_2018c2;

public class PlayerStats {
    private int lives = 3;
    private int shield = 0;

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getShield() {
        return shield;
    }

    public void substractLive(){
        lives--;
    }

    public void substractShield(){
        shield--;
    }
}
