package game;

import javax.swing.*;
import java.awt.*;

public class GameFrame {

    /**
     * Constructor of the Game Frame
     */
    public GameFrame() {

        JFrame frame = new JFrame("Classic Snake");
        frame.setContentPane(new GamePanel());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(GamePanel.WIDTH_AND_HEIGHT, GamePanel.WIDTH_AND_HEIGHT));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}