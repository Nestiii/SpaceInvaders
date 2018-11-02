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
        button.setIcon(new ImageIcon("src/images/PlayButton.png"));
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
        button.setOpaque(false);
        button.setFocusPainted(false);
        JLabel title = new JLabel(new ImageIcon("src/images/Title.png"));
        title.setBackground(Color.BLACK);
        panel.add(title);
        panel.add(button);
        add(panel);



    }
//java -jar FileName.jar server/client
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            SpaceInvaders ex = new SpaceInvaders();
            ex.setVisible(true);
        });
    }
}