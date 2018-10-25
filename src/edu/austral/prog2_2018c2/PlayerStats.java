package edu.austral.prog2_2018c2;

public class PlayerStats {
    private int lives = 3;
    private int shield = 0;
    private int consecutiveShots;
    private boolean immunity = false;

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

    public void addConsecutiveShot(){
        consecutiveShots++;
    }

    public int getAcertedShots(){
        return consecutiveShots;
    }

    public void resetConsecutiveShots(){
        consecutiveShots = 0;
    }

    public void setImmunity(boolean a){
        immunity = a;
    }

    public boolean isImmune() {
        return immunity;
    }
}
