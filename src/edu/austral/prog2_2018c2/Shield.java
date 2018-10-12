package edu.austral.prog2_2018c2;

import javax.swing.*;

public class Shield extends Sprite{
    String image = "src/images/shield.png";
    private int shotsReceived= 0;

    public Shield(int x, int y){
        this.x = x;
        this.y = y;
        ImageIcon ii = new ImageIcon(image);
        setImage(ii.getImage());

    }

    public int getShotsReceived() {
        return shotsReceived;
    }

    public void receiveShot(){
        if (shotsReceived<16){
            shotsReceived++;

        }else if (shotsReceived<33){
            ImageIcon im = new ImageIcon("src/images/shieldBroken1.png");
            setImage(im.getImage());
            shotsReceived++;

        }else if(shotsReceived<50){
            ImageIcon im = new ImageIcon("src/images/shieldBroken2.png");
            setImage(im.getImage());
            shotsReceived++;

        }else {
            setDying(true);
        }
    }
}
