package edu.austral.prog2_2018c2;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    Model model;
    IngameWindow ingameWindow;
    private boolean ingame = true;
    private Level actualLevel;
    Player player;

    public Controller(Model model, IngameWindow ingameWindow) {
        this.model = model;
        this.ingameWindow = ingameWindow;
        player = model.getPlayer();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int x = player.getX();
            int y = player.getY();
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {

                player.setDx(-2);
            }else if (key == KeyEvent.VK_RIGHT) {

                player.setDx(2);
            }else if (key == KeyEvent.VK_SPACE) {

                if (ingame) {
                    ingameWindow.drawShot(new Shot(x, y));
                    }
                }
            }
        }
    }
}
}

