package edu.austral.prog2_2018c2;

import java.awt.*;
import javax.swing.JFrame;

public class SpaceInvaders extends JFrame implements Commons {
    private Controller controller;

    public SpaceInvaders() {
        controller = new Controller(new Model(),new IngameWindow());

        initUI();
    }

    private void initUI() {
        add(new Board());
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            SpaceInvaders ex = new SpaceInvaders();
            ex.setVisible(true);
        });
    }
}