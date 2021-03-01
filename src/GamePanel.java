import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//TODO implement snake movement with MOUSE
//TODO implement pause control
//TODO refactor

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH  = 400;
    public static final int HEIGHT = 400;

    private Graphics2D g2d;
    private BufferedImage image;

    private boolean isGameRunning;

    Entity snakeHead;
    Apple apple;
    Obstacle obstacle;
    private ArrayList<Entity> snake;
    public static final byte SIZE = 10;
    private int points;
    private byte snakeLength = 1;

    private boolean isGameOver;
    private boolean isWin = false;

    private int snakeX, snakeY;

    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;
    private boolean start;

    public GamePanel() {

        setPreferredSize(new Dimension(WIDTH, 400));
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
    }
    @Override
    public void addNotify() {

        super.addNotify();

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int k = e.getKeyCode();

        if(k == KeyEvent.VK_UP) {

            up = true;
        }
        if(k == KeyEvent.VK_DOWN) {

            down = true;
        }
        if(k == KeyEvent.VK_LEFT) {

            left = true;
        }
        if(k == KeyEvent.VK_RIGHT) {

            right = true;
        }
        if(k == KeyEvent.VK_ENTER) {

            start = true;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int k = e.getKeyCode();

        if(k == KeyEvent.VK_UP) {

            up = false;
        }
        if(k == KeyEvent.VK_DOWN) {

            down = false;
        }
        if(k == KeyEvent.VK_LEFT) {

            left = false;
        }
        if(k == KeyEvent.VK_RIGHT) {

            right = false;
        }
        if(k == KeyEvent.VK_ENTER) {

            start = false;
        }
    }

    @Override
    public void run() {

        boardGameValues();

        while (isGameRunning) {

            boardUpdater();

            requestRender();

            checkWaitTime();
        }
    }

    /**
     * Method that checks the waitTime
     */
    private void checkWaitTime() {

        long startTime;
        long elapsedTime;
        long waitTime;
        final byte targetTime = 100;

        startTime = System.nanoTime();
        elapsedTime = System.nanoTime() - startTime;

        waitTime = targetTime - elapsedTime / 1_000_000;

        if (waitTime > 0) {

            try {

                Thread.sleep(waitTime);

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    /**
     * Method that Updates the board
     */
    private void boardUpdater() {

        if(isGameOver){

            if (start){

                resetBoard();
            }
            return;
        }

        snakeMovementLogic();

        snakePositionSetterInMove();

        isPointsEnoughToFinishGame();

        isSnakeBiteItself();

        isSnakeAteApple();

        isSnakeAteObstacle();

        checkLogic();
    }

    /**
     * Method that sets Snake Box Positions during Movement
     */
    private void snakePositionSetterInMove() {

        if (snakeX != 0 || snakeY != 0) {

            for (int i = snake.size() - 1; i > 0; i--) {

                snake.get(i).setPosition(snake.get(i - 1).getCoordinateX(), snake.get(i - 1).getCoordinateY());
            }

            snakeHead.move(snakeX, snakeY);
        }
    }

    /**
     * Method that does the Movement Logic of the Snake
     */
    private void snakeMovementLogic() {

        if(up && snakeY == 0) {

            snakeY = -SIZE;
            snakeX = 0;
        }
        else if(down && snakeY == 0) {

            snakeY = SIZE;
            snakeX = 0;
        }
        else if(left && snakeX == 0) {

            snakeY = 0;
            snakeX = -SIZE;
        }
        else if(right && snakeX == 0 && snakeY != 0) {

            snakeY = 0;
            snakeX = SIZE;
        }
    }

    /**
     * Method that Resets the Board when the Game is Over
     */
    private void resetIfGameOver() {

        if(isGameOver){

            if (start){

                resetBoard();
            }
        }
        //return isGameOver;
    }

    /**
     * Method that checks if the player reached enough points to finish the game
     */
    private void isPointsEnoughToFinishGame() {

        int maxPoints = 300;

        if(points >= maxPoints) {

            isGameOver = true;
            isWin = true;
        }
    }

    /**
     * Method that checks if the snake bite itself
     */
    private void isSnakeBiteItself() {

        for(Entity e : snake) {

            if(e.isSnakeHitSomething(snakeHead)) {

                isGameOver = true;
                break;
            }
        }
    }

    /**
     * Method that checks logic
     */
    private void checkLogic() {

        if(snakeHead.getCoordinateX() < 0) {

            snakeHead.setCoordinateX(WIDTH);
        }
        if(snakeHead.getCoordinateX() > WIDTH) {

            snakeHead.setCoordinateX(0);
        }
        if(snakeHead.getCoordinateY() < 0) {

            snakeHead.setCoordinateY(HEIGHT);
        }
        if(snakeHead.getCoordinateY() > HEIGHT) {

            snakeHead.setCoordinateY(0);
        }
    }

    /**
     * Check if the Snake tried to eat an Obstacle
     */
    private void isSnakeAteObstacle() {

        if(obstacle.isSnakeHitSomething(snakeHead)){

            isGameOver = true;
        }
    }


    /**
     * Method that Updates Points and Length of snake when Apple is eaten
     */
    private void isSnakeAteApple() {

        if(apple.isSnakeHitSomething(snakeHead)){

            points += 10;
            snakeLength++;

            apple.setApple();

            Entity e = new Entity(SIZE);

            e.setPosition(-100, -100);

            snake.add(e);
        }
    }

    /**
     * Method that Renders an Image
     */
    private void requestRender() {

        render();

        Graphics g = getGraphics();

        g.drawImage(image,0,0,null);
        g.dispose();
    }

    /**
     * Method that sets board and Game values at the beginning of the game
     */
    private void boardGameValues() {

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();

        isGameRunning = true;

        isGameOver = false;

        resetBoard();
    }

    /**
     * Method that Resets the Board
     */
    private void resetBoard() {

        setStartValues();

        drawSnake();

        apple.setApple();

        //set obstackles from here

        obstacle.setObstacles();
    }

    /**
     * Method that sets the values
     */
    private void setStartValues() {

        snake = new ArrayList<>();

        snakeHead = new Entity(SIZE);

        snakeHead.setPosition(WIDTH / 2 ,HEIGHT / 2 );

        snake.add(snakeHead);

        apple = new Apple(SIZE);

        //here the new obstacles are set, create with for obstacles here
        obstacle = new Obstacle(SIZE);

        points = 0;
        snakeLength = 1;

        snakeX = 0;
        snakeY = 0;

        isGameOver = false;
    }

    /**
     * Method that draws the snake at the Start of the Game
     */
    private void drawSnake() {

        //For loop to change snake's start length
        for(int i = 0; i < 0; i++) {

            Entity e = new Entity(SIZE);

            e.setPosition(snakeHead.getCoordinateX() + (i + SIZE), snakeHead.getCoordinateY());

            snake.add(e);
        }
    }

    /**
     * Method that Renders the Snake, the Apple and Game messages
     */
    public void render() {

        renderSnake();

        apple.renderApple(g2d);

        gameMessageDisplay();

        informationDisplay();

        obstacle.renderObstacle(g2d);
    }

    private void renderSnake() {

        g2d.clearRect(0,0, WIDTH, HEIGHT);
        g2d.setColor(Color.GREEN);

        for (Entity e : snake) {

            e.render(g2d);
        }
    }

    private void informationDisplay() {

        g2d.drawString("Points: " + points, 7 ,11 );
        g2d.drawString("Length: " + snakeLength, 340 ,11 );
    }

    /**
     * Method that display messages at the Start,End of the game
     */
    private void gameMessageDisplay() {

        if (isGameOver) {

            if(isWin){

                g2d.drawString("YOU ARE A WINNER", 140 ,150 );
            }
            else {
                g2d.drawString("GAME OVER", 160, 150);
            }
            g2d.drawString("Press Enter to Restart", 134 ,170 );
        }

        g2d.setColor(Color.WHITE);

        if(snakeX == 0 && snakeY == 0){

            g2d.drawString("Press Arrow key to Start!", 140 ,150 );
        }
    }
}