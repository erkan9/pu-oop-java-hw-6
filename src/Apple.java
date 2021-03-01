import java.awt.*;

public class Apple extends Entity {

    /**
     * Constructor
     *
     * @param size Size of the Entity
     */
    public Apple(int size) {
        super(size);
    }

    /**
     * Method that sets an apple on the Board
     */
    public void setApple() {

        int appleXCoordinate = (int) (Math.random() * (GamePanel.WIDTH - GamePanel.SIZE));
        int appleYCoordinate = (int) (Math.random() * (GamePanel.HEIGHT - GamePanel.SIZE));

        appleXCoordinate = appleXCoordinate - (appleXCoordinate % GamePanel.SIZE);
        appleYCoordinate = appleYCoordinate - (appleYCoordinate % GamePanel.SIZE);

        setPosition(appleXCoordinate, appleYCoordinate);
    }

    /**
     * Method that renders the Apple
     * @param g2d Object of Graphics2D
     */
    public void renderApple(Graphics2D g2d) {

        g2d.setColor(Color.RED);
        render(g2d);
    }
}
