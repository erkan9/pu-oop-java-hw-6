package gamefigures;

import game.GamePanel;

import java.awt.*;

public class Obstacle extends Entity{

    /**
     * Constructor
     *
     * @param size Size of the entities.Entity
     */
    public Obstacle(int size) {
        super(size);
    }

    /**
     * Method that sets the Obstacles and its coordinates
     */
    public void setObstacles() {

        int obstacleXCoordinate;
        int obstacleYCoordinate;

            obstacleXCoordinate = (int) (Math.random() * (GamePanel.WIDTH_AND_HEIGHT - GamePanel.BOX_SIZE));
            obstacleYCoordinate = (int) (Math.random() * (GamePanel.WIDTH_AND_HEIGHT - GamePanel.BOX_SIZE));

            obstacleXCoordinate = obstacleXCoordinate - (obstacleXCoordinate % GamePanel.BOX_SIZE);
            obstacleYCoordinate = obstacleYCoordinate - (obstacleYCoordinate % GamePanel.BOX_SIZE);

            setPosition(obstacleXCoordinate, obstacleYCoordinate);
    }

    /**
     * Method that renders the Obstacles for the board
     * @param g2d Object of Graphics2D
     */
    public void renderObstacle(Graphics2D g2d) {

        g2d.setColor(Color.WHITE);
        render(g2d);
    }
}