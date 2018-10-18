package edu.austral.prog2_2018c2;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.*;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class IngameWindow extends JPanel implements Commons {
    private Dimension d;
    private Graphics g;


    public void drawAliens(List<Alien> aliens) {

        for (Alien alien: aliens) {

            if (alien.isVisible()) {

                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {

                alien.die();
            }
        }
    }

    public void drawPlayer(Player player) {

        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {

            player.die();
            message = "Game over  Score: "+score;
            ingame = false;
        }
    }

    public void drawShot(Shot shot) {

        if (shot.isVisible()) {

            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }

    }

    public void drawBombing(List<Alien> aliens) {

        for (Alien a : aliens) {

            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }
    private void drawShields(List<Shield> shields) {

        for (int i = 0; i < shields.size(); i++) {

            if (shields.get(i).isVisible()) {

                Shield shield = shields.get(i);

                g.drawImage(shield.getImage(), shield.getX(), shield.getY(), this);
            }

            if (shields.get(i).isDying()) {

                shields.get(i).die();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (ingame) {

            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
            drawShields(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }



}
