package edu.austral.prog2_2018c2;

import javax.swing.*;

public class Shield extends Sprite{
    String image = "src/images/shield.png";
    public Shield(int x, int y){
        this.x = x;
        this.y = y;
        ImageIcon ii = new ImageIcon(image);
        setImage(ii.getImage());

    }
}
