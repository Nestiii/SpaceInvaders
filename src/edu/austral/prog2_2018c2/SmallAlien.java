package edu.austral.prog2_2018c2;

public class SmallAlien extends Alien {
    private static String image = "src/images/Smallalien.jpg";
    private String type = "SmallAlien";

    public SmallAlien(int x, int y){
        super(x,y,image);
    }

    public String getType() {
        return type;
    }
}
