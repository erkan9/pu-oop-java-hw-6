import javax.swing.*;
import java.awt.*;

public class GameFrame {

    public GameFrame() {

        JFrame frame = new JFrame("Classic Snake");
        frame.setContentPane(new GamePanel());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}