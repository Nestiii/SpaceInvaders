package edu.austral.prog2_2018c2;

public class BigAlien extends Alien{
    private static String image = "src/images/Bigalien.png";
    private String type = "BigAlien";

    public BigAlien(int x, int y){

        super(x,y,image);

    }

    public String getType() {
        return type;
    }
}
