package edu.austral.prog2_2018c2;

public class MedAlien extends Alien{
    private static String image = "src/images/Medalien.png";
    private String type = "MedAlien";

    public MedAlien(int x, int y){
        super(x,y,image);
    }

    public String getType() {
        return type;
    }
}
