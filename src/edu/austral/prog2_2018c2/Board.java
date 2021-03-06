package edu.austral.prog2_2018c2;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.*;

public class Board extends JPanel implements Runnable, Commons {


    private Dimension d;
    private ArrayList<Alien> aliens;
    private ArrayList<Shield> shields;
    private Ufo ufo;
    private Player player;
    private List<Shot> shots;
    private final int SHIELD_INIT_Y = 230;
    private final int ALIEN_INIT_X = 40;
    private final int ALIEN_INIT_Y = 5;
    private final int MALIEN_INIT_Y = 23;
    private final int BALIEN_INIT_Y = 59;
    private int ufodirection = 1;
    private int direction = -1;
    private int deaths = 0;
    private int score;
    private long initialTime;
    private int ufoTime;
    private boolean inPower;
    private long powerTime;
    private int powerLength;
    private int lastDirection;
    private int chance;

    private boolean ingame = true;
    private final String explImg = "src/images/explosion.png";
    private String message = "Game Over";
    private Level actualLevel;
    private List<Level> levelList = new ArrayList<>();

    private Thread animator;

    Ranking ranking;


    public Board() {
        levelList.add(LEVEL1);
        levelList.add(LEVEL2);
        levelList.add(LEVEL3);
        levelList.add(LEVEL4);
        levelList.add(LEVEL5);
        actualLevel = LEVEL1;

       initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);
        initialTime = System.currentTimeMillis();
        double random = Math.random() * 15 + 46;
        ufoTime = (int) random;
        gameInit();
        setDoubleBuffered(true);
    }

    @Override
    public void addNotify() {

        super.addNotify();
        gameInit();
    }

    public void gameInit() {

        aliens = new ArrayList<>();
        shields = new ArrayList<>();
        shots = new ArrayList<>();
        chance = 5;
        initShields();

        for (int j = 0; j < 6; j++) {
            Alien alien = new Alien(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y, "src/images/SmallAlien.png", "SmallAlien");
            aliens.add(alien);
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                Alien alien = new Alien(ALIEN_INIT_X + 18 * j, MALIEN_INIT_Y + 18 * i, "src/images/MedAlien.png", "MedAlien");
                aliens.add(alien);
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                Alien alien = new Alien(ALIEN_INIT_X + 18 * j, BALIEN_INIT_Y + 18 * i, "src/images/BigAlien.png", "BigAlien");
                aliens.add(alien);
            }
        }

        player = new Player();
        player.getStats().setShield(actualLevel.getShields());
        Shot shot = new Shot();
        shots.add(shot);


        if (animator == null || !ingame) {

            animator = new Thread(this);
            animator.start();
        }
    }

    private void initShields() {
        int shieldsAmount = actualLevel.getShields();
        int spacing = BOARD_WIDTH / (shieldsAmount + 1);


        for (int i = 0; i < shieldsAmount; i++) {
            Shield shield = new Shield(spacing - 20 + spacing * i, SHIELD_INIT_Y);
            shields.add(shield);

        }

    }

    public void drawUfo(Graphics g) {
        if (ufo != null) {
            if (ufo.isVisible()) {

                g.drawImage(ufo.getImage(), ufo.getX(), ufo.getY(), this);
            }
            if (ufo.isDying()) {
                ufo.die();
            }
        }
    }


    public void drawAliens(Graphics g) {

        for (Alien alien : aliens) {

            if (alien.isVisible()) {

                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {

                alien.die();
            }
        }
    }

    public void drawPlayer(Graphics g) {

        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {

            player.die();
            message = "Game over  Score: " + score;
            ingame = false;
        }
    }

    public void drawShot(Graphics g) {
        for (int i = 0; i < shots.size(); i++) {
            if (shots.get(i).isVisible()) {

                g.drawImage(shots.get(i).getImage(), shots.get(i).getX(), shots.get(i).getY(), this);
            }
        }

    }

    public void drawBombing(Graphics g) {

        for (Alien a : aliens) {

            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    private void drawShields(Graphics g) {

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
            drawUfo(g);
            g.setColor(Color.white);
            g.setFont(new Font("text", Font.PLAIN, 13));
            g.drawString("Lives: " + player.getStats().getLives(), 10, BOARD_HEIGHT - 44);
            g.drawString("Level: " + actualLevel.getNum(), BOARD_WIDTH / 4, BOARD_HEIGHT - 44);
            g.drawString("Score: " + score, 4 * BOARD_WIDTH / 9 - 5, BOARD_HEIGHT - 44);
            g.drawString("Aliens killed: " + deaths + "/" + NUMBER_OF_ALIENS_TO_DESTROY, 2 * BOARD_WIDTH / 3 - 10, BOARD_HEIGHT - 44);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }


    public void gameOver() {

        Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);
        drawHighscores(g);

    }

    public void print(String message) {

        Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(35, 93, 15));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

        g.setColor(Color.white);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2, BOARD_WIDTH / 2);

    }

    public void drawHighscores(Graphics g){
        Ranking ranking = new Ranking();
        List<Ranking.Score> highscores = ranking.getRanking();
        g.setColor(Color.green);
        g.setFont(new Font("asd",Font.PLAIN,20));
        g.drawLine(20,20,118,20);
        g.drawString("Highscores:",20,18);
        g.setColor(Color.white);
        g.setFont(new Font("a",Font.ITALIC,14));
        for (int i = 0; i <highscores.size() ; i++) {
            g.drawString(highscores.get(i).getName()+ "   " + highscores.get(i).getPoints(),20,15*i+35);
        }

    }


    public void animationCycle() {

        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY && levelList.indexOf(actualLevel) == levelList.size() - 1) {

            ingame = false;
            message = "Game won!   Final Score: " + score;
            Graphics g = this.getGraphics();
            drawHighscores(g);
        }
        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY && levelList.indexOf(actualLevel) < levelList.size() - 1) {
            deaths = 0;
            actualLevel = levelList.get(levelList.indexOf(actualLevel) + 1);
            try {
                Thread.sleep(50);
            } catch (InterruptedException exc) {

            }
            print("Level " + (levelList.indexOf(actualLevel) + 1) + "   Score: " + score);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException exc) {

            }
            gameInit();
        }
        // player
        player.act();

        // shot
        for (int i = 0; i < shots.size(); i++) {

            if (shots.get(i).isVisible()) {

                int shotX = shots.get(i).getX();
                int shotY = shots.get(i).getY();

                for (int j = 0; j < shields.size(); j++) {
                    int shieldx = shields.get(j).getX();
                    int shieldy = shields.get(j).getY();

                    if (shields.get(j).isVisible() && shots.get(i).isVisible()) {
                        if (shotX >= (shieldx)
                                && shotX <= (shieldx + SHIELD_WIDTH)
                                && shotY >= (shieldy)
                                && shotY <= (shieldy + SHIELD_HEIGHT)) {
                            shots.get(i).die();
                            player.getStats().resetConsecutiveShots();
                        }
                    }

                }


                for (Alien alien : aliens) {

                    int alienX = alien.getX();
                    int alienY = alien.getY();

                    if (alien.isVisible() && shots.get(i).isVisible()) {
                        if (shotX >= (alienX)
                                && shotX <= (alienX + ALIEN_WIDTH)
                                && shotY >= (alienY)
                                && shotY <= (alienY + ALIEN_HEIGHT)) {
                            ImageIcon ii
                                    = new ImageIcon(explImg);
                            alien.setImage(ii.getImage());
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            alien.setDying(true);
                            deaths++;
                            shots.get(i).die();
                            if (!inPower) {
                                player.getStats().addConsecutiveShot();
                                System.out.println(player.getStats().getConsecutiveShots());
                            }
                            switch (alien.getType()) {
                                case "MedAlien":

                                    score += MEDALIEN_POINTS;
                                    break;
                                case "BigAlien":

                                    score += BIGALIEN_POINTS;
                                    break;
                                case "SmallAlien":

                                    score += SMALLALIEN_POINTS;
                                    break;
                            }
                        }
                    }
                }


                int y = shots.get(i).getY();
                y -= 4;

                if (y < 0) {
                    player.getStats().resetConsecutiveShots();
                    shots.get(i).die();
                } else {
                    shots.get(i).setY(y);
                }

            }

            // aliens

            for (Alien alien : aliens) {

                int x = alien.getX();

                if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {

                    direction = -1;
                    Iterator i1 = aliens.iterator();

                    while (i1.hasNext()) {

                        Alien a2 = (Alien) i1.next();
                        a2.setY(a2.getY() + GO_DOWN);
                    }
                }

                if (x <= BORDER_LEFT && direction != 1) {

                    direction = 1;

                    Iterator i2 = aliens.iterator();

                    while (i2.hasNext()) {

                        Alien a = (Alien) i2.next();
                        a.setY(a.getY() + GO_DOWN);
                    }
                }
            }

            Iterator it = aliens.iterator();

            while (it.hasNext()) {

                Alien alien = (Alien) it.next();

                if (alien.isVisible()) {

                    int y = alien.getY();

                    if (y > GROUND - ALIEN_HEIGHT) {
                        ingame = false;
                        message = "Invasion! Score " + score;
                    }
                    if (direction != 0) {
                        if (direction == -1) {

                            alien.act(direction - actualLevel.getMovementSpeed());

                        } else {

                            alien.act(direction + actualLevel.getMovementSpeed());

                        }
                    }
                }
            }

            // bombs
            Random generator = new Random();

            for (Alien alien : aliens) {

                int shot = generator.nextInt(15);
                Alien.Bomb b = alien.getBomb();

                if (shot == chance && alien.isVisible() && b.isDestroyed()) {

                    b.setDestroyed(false);
                    b.setX(alien.getX());
                    b.setY(alien.getY());
                }

                int bombX = b.getX();
                int bombY = b.getY();
                int playerX = player.getX();
                int playerY = player.getY();

                if (player.isVisible() && !b.isDestroyed()) {

                    if (bombX >= (playerX)
                            && bombX <= (playerX + PLAYER_WIDTH)
                            && bombY >= (playerY)
                            && bombY <= (playerY + PLAYER_HEIGHT)) {

                        ImageIcon ii
                                = new ImageIcon(explImg);
                        ImageIcon i2 = new ImageIcon("src/images/player.png");

                        player.setImage(ii.getImage());
                        b.setDestroyed(true);
                        player.getHit();
                        player.setImage(i2.getImage());

                    }
                }

                for (int h = 0; h < shields.size(); h++) {

                    if (shields.get(h).isVisible() && !b.isDestroyed()) {

                        if (bombX >= (shields.get(h).getX())
                                && bombX <= (shields.get(h).getX() + SHIELD_WIDTH)
                                && bombY >= (shields.get(h).getY())
                                && bombY <= (shields.get(h).getY() + SHIELD_HEIGHT)) {


                            shields.get(h).receiveShot();
                            b.setDestroyed(true);


                        }
                    }
                }

                for (int g = 0; g < shields.size(); g++) {

                    if (shields.get(g).isVisible() && !b.isDestroyed()) {

                        if (alien.getX() >= (shields.get(g).getX())
                                && alien.getX() <= (shields.get(g).getX() + SHIELD_WIDTH)
                                && alien.getY() >= (shields.get(g).getY())
                                && alien.getY() <= (shields.get(g).getY() + SHIELD_HEIGHT)) {

                            shields.get(g).setDying(true);

                        }
                    }
                }

                if (!b.isDestroyed()) {

                    b.setY(b.getY() + 1);

                    if (b.getY() >= GROUND - BOMB_HEIGHT) {
                        b.setDestroyed(true);
                    }
                }
            }
            if ((System.currentTimeMillis() - initialTime) / 1000 == ufoTime) {

                initialTime = System.currentTimeMillis();
                ufo = new Ufo(ALIEN_INIT_X, ALIEN_INIT_Y);


            }
            if (ufo != null) {

                int x = ufo.getX();

                if (x >= BOARD_WIDTH - BORDER_RIGHT) {

                    ufo.die();

                }

                ufo.act(ufodirection);

                if (shots.get(i).isVisible()) {

                    int shotX = shots.get(i).getX();
                    int shotY = shots.get(i).getY();
                    if (ufo.isVisible() && shots.get(i).isVisible()) {
                        if (shotX >= (ufo.getX()) && shotX <= (ufo.getX() + UFO_WIDTH) && shotY >= (ufo.getY()) && shotY <= (ufo.getY() + UFO_HEIGHT)) {
                            ufo.setDying(true);
                            shots.get(i).die();
                            double random = Math.random() * 250 + 50;
                            score += (int) random;
                            player.getStats().addConsecutiveShot();
                        }
                    }
                }
            }
        }

        if (player.getStats().getConsecutiveShots() == 4 && !inPower) {
            double random = Math.random() * 100;

            if ((int) random < 10) {
                freezePower();

            } else if ((int) random < 30) {
                immunityPower();

            } else {
                freezePower();

            }
            player.getStats().resetConsecutiveShots();
            inPower = true;
            powerTime = System.currentTimeMillis();
            double random2 = Math.random() * 3 + 3;
            powerLength = (int) random2;

        }
        if (player.getStats().isImmune()) {
            System.out.println("inmune");

        }
        if (inPower && System.currentTimeMillis() - (powerTime) >= powerLength * 1000) {
            inPower = false;
            powersOff();
        }
    }

    public void powersOff() {
        player.getStats().setImmunity(false);
        if (direction == 0) {
            direction = lastDirection;
            chance = 5;
        }
    }

    public void immunityPower() {
        player.getStats().setImmunity(true);

    }

    public void freezePower() {
        lastDirection = direction;
        direction = 0;
        chance = 16;

    }

    public void doubleShotPower() {//hacer

    }

    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        requestFocus();
        while (ingame) {

            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }
        gameOver();

    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {

                if (ingame) {
                    for (int i = 0; i < shots.size(); i++) {
                        if (!shots.get(i).isVisible()) {
                            shots.remove(i);
                            shots.add(new Shot(x, y));
                        }
                    }
                }
            }
        }
    }
}