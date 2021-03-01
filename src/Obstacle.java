import java.awt.*;
import java.util.Random;

public class Obstacle extends Entity{

    Random random = new Random();

    /**
     * Constructor
     *
     * @param size Size of the Entity
     */
    public Obstacle(int size) {
        super(size);
    }

    public void displayObstacles(Graphics2D g2d){

        setObstacles();

        renderObstacle(g2d);
    }

    public void setObstacles() {

        int numOfObstacles = random.nextInt(10);

        int obstacleXCoordinate;
        int obstacleYCoordinate;

            obstacleXCoordinate = (int) (Math.random() * (GamePanel.WIDTH - GamePanel.SIZE));
            obstacleYCoordinate = (int) (Math.random() * (GamePanel.HEIGHT - GamePanel.SIZE));

            obstacleXCoordinate = obstacleXCoordinate - (obstacleXCoordinate % GamePanel.SIZE);
            obstacleYCoordinate = obstacleYCoordinate - (obstacleYCoordinate % GamePanel.SIZE);

            setPosition(obstacleXCoordinate, obstacleYCoordinate);
    }

    /**
     * Method that renders the Apple
     * @param g2d Object of Graphics2D
     */
    public void renderObstacle(Graphics2D g2d) {

        g2d.setColor(Color.GRAY);
        render(g2d);
    }
}
