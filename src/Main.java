import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Snake");
        frame.setContentPane(new GamePannel());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(GamePannel.WIDTH, GamePannel.HEIGHT));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
