package edu.austral.prog2_2018c2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SpaceInvaders extends JFrame implements Commons {
   private  JPanel panel;
   private JButton button;

    public SpaceInvaders() {

        initUI();
    }

    private void initUI() {

        panel = new JPanel();
        panel.setBackground(Color.white);

        setVisible(true);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button = new JButton();
        button.setFocusable(false);
        button.setIcon(new ImageIcon("src/images/StartButton.jpg"));
        button.setBorderPainted(false);
        button.setBackground(Color.white);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(panel);
                add(new Board());
                revalidate();

            }
        });

        setTitle("Space Invaders");
        setLocationRelativeTo(null);
        setResizable(false);
        button.setLayout(null);
        panel.add(button);
        add(panel);



    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            SpaceInvaders ex = new SpaceInvaders();
            ex.setVisible(true);
        });
    }
}