package gamefigures;

import game.GamePanel;

import java.awt.*;

public class Apple extends Entity {

    /**
     * Constructor
     *
     * @param size Size of the entities.Entity
     */
    public Apple(int size) {
        super(size);
    }

    /**
     * Method that sets an Apple on the Board
     */
    public void setApple() {

        int appleXCoordinate = (int) (Math.random() * (GamePanel.WIDTH_AND_HEIGHT - GamePanel.BOX_SIZE));
        int appleYCoordinate = (int) (Math.random() * (GamePanel.WIDTH_AND_HEIGHT - GamePanel.BOX_SIZE));

        appleXCoordinate = appleXCoordinate - (appleXCoordinate % GamePanel.BOX_SIZE);
        appleYCoordinate = appleYCoordinate - (appleYCoordinate % GamePanel.BOX_SIZE);

        setPosition(appleXCoordinate, appleYCoordinate);
    }

    /**
     * Method that renders the entities.Apple
     * @param g2d Object of Graphics2D
     */
    public void renderApple(Graphics2D g2d) {

        g2d.setColor(Color.RED);
        render(g2d);
    }
}
