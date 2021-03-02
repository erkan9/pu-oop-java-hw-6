package game;

import gamecontrols.Click;
import gamecontrols.Key;
import gamefigures.Apple;
import gamefigures.Entity;
import gamefigures.Obstacle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import static javax.swing.JOptionPane.showMessageDialog;

//TODO refactor

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH_AND_HEIGHT = 400;

    private final Random random = new Random();
    private Entity snakeHead;
    private Apple apple;

    private Graphics2D g2d;
    private BufferedImage image;

    private int numOfGameObstacle = random.nextInt(10);
    private int snakeX, snakeY;
    private int points;

    public static final byte BOX_SIZE = 10;
    private byte snakeLength          = 1;

    private ArrayList<Entity> snake;
    private ArrayList<Obstacle> gameObstacles = new ArrayList<>(numOfGameObstacle);

    private boolean isGameRunning;
    private boolean isGameOver;
    private boolean isWin;

    public static boolean isGoingUp;
    public static boolean isGoingDown;
    public static boolean isGoingRight;
    public static boolean isGoingLeft;

    public static boolean restartGame;


    /**
     * Constructor for the Game Panel
     */
    public GamePanel() {

        showMessageDialog(null, "Snake Controls\n1 Left Click = Left\n2 Left Click = Right" +
                "\n1 Right Click = Up\n2 Right Click = Down");

        setPreferredSize(new Dimension(WIDTH_AND_HEIGHT, 400));
        setFocusable(true);
        requestFocus();

        Click click = new Click();
        this.addMouseListener(click);

        Key key = new Key();
        this.addKeyListener(key);
    }

    /**
     * Makes this Component displayable by connecting it to a native screen resource
     */
    @Override
    public void addNotify() {

        super.addNotify();

        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Method used to create a thread, starting the thread causes the object's run method
     * to be called in that separately executing thread
     */
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
     * Method that sets 10 obstacles on the board
     */
    private void setObstacleCoordinates() {

        for (int i = 0; i < numOfGameObstacle; i++ ){

           Obstacle obstacle = new Obstacle(BOX_SIZE);
           obstacle.setObstacles();

           gameObstacles.add(obstacle);
        }
    }

    /**
     * Method that checks the waitTime
     */
    private void checkWaitTime() {

        long startTime;
        long elapsedTime;
        long waitTime;
        int targetTime = 100;

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
     * Method that checks if game is over and restart game
     */
    private void checkIfGameOver() {

        if(isGameOver){

            if (restartGame){

                resetBoard();
            }
        }
    }

    /**
     * Method that Updates the board
     */
    private void boardUpdater() {

        //checkIfGameOver();

        if(isGameOver){

            if (restartGame){

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

        if(isGoingUp && snakeY == 0) {

            snakeY = -BOX_SIZE;
            snakeX = 0;
        }
        else if(isGoingDown && snakeY == 0) {

            snakeY = BOX_SIZE;
            snakeX = 0;
        }
        else if(isGoingLeft && snakeX == 0) {

            snakeY = 0;
            snakeX = -BOX_SIZE;
        }
        else if(isGoingRight && snakeX == 0 && snakeY != 0) {

            snakeY = 0;
            snakeX = BOX_SIZE;
        }
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

            snakeHead.setCoordinateX(WIDTH_AND_HEIGHT);
        }
        if(snakeHead.getCoordinateX() > WIDTH_AND_HEIGHT) {

            snakeHead.setCoordinateX(0);
        }
        if(snakeHead.getCoordinateY() < 0) {

            snakeHead.setCoordinateY(WIDTH_AND_HEIGHT);
        }
        if(snakeHead.getCoordinateY() > WIDTH_AND_HEIGHT) {

            snakeHead.setCoordinateY(0);
        }
    }

    /**
     * Check if the Snake tried to eat an entities.Obstacle
     */
    private void isSnakeAteObstacle() {

        if(gameObstacles.stream().anyMatch(a ->a.isSnakeHitSomething(snakeHead))){

            isGameOver = true;
        }
    }

    /**
     * Method that Updates Points and Length of snake when entities.Apple is eaten
     */
    private void isSnakeAteApple() {

        if(apple.isSnakeHitSomething(snakeHead)){

            points += 10;
            snakeLength++;

            apple.setApple();

            Entity e = new Entity(BOX_SIZE);

            e.setPosition(-100, -100);

            snake.add(e);
        }
    }

    /**
     * Method that Renders an Image
     */
    private void requestRender() {

        Graphics g = getGraphics();

        g.drawImage(image,0,0,null);
        g.dispose();

        render();
    }

    /**
     * Method that sets board and Game values at the beginning of the game
     */
    private void boardGameValues() {

        image = new BufferedImage(WIDTH_AND_HEIGHT, WIDTH_AND_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();

        isGameRunning = true;

        isGameOver = false;

        isWin = false;

        resetBoard();
    }

    /**
     * Method that Resets the Board
     */
    private void resetBoard() {

        setStartValues();

        drawSnake();

        apple.setApple();

        gameObstacles = new ArrayList<>(numOfGameObstacle);

        setObstacleCoordinates();

    }

    /**
     * Method that stops the snake after the board is reset
     */
    private void stopMouseAtStart() {

        isGoingDown = false;
        isGoingUp = false;
        isGoingLeft = false;
        isGoingRight = false;
    }

    /**
     * Method that sets the values
     */
    private void setStartValues() {

        numOfGameObstacle = random.nextInt(10);

        snake = new ArrayList<>(numOfGameObstacle);

        snakeHead = new Entity(BOX_SIZE);

        snakeHead.setPosition(WIDTH_AND_HEIGHT / 2 , WIDTH_AND_HEIGHT / 2 );

        snake.add(snakeHead);

        apple = new Apple(BOX_SIZE);

        setObstacleCoordinates();

        points = 0;
        snakeLength = 1;

        snakeX = 0;
        snakeY = 0;

        stopMouseAtStart();

        isGameOver = false;
    }

    /**
     * Method that draws the snake at the Start of the Game
     */
    private void drawSnake() {

        //For loop to change snake's restartGame length
        for(int snakeStartSize = 0; snakeStartSize < 0; snakeStartSize++) {

            Entity e = new Entity(BOX_SIZE);

            e.setPosition(snakeHead.getCoordinateX() + (snakeStartSize + BOX_SIZE), snakeHead.getCoordinateY());

            snake.add(e);
        }
    }

    /**
     * Method that Renders the Snake, the entities.Apple and Game messages
     */
    public void render() {

        renderSnake();

        apple.renderApple(g2d);

        gameMessageDisplay();

        informationDisplay();

        renderObstacles();
    }

    /**
     * Method that renders the Snake
     */
    private void renderSnake() {

        g2d.clearRect(0,0, WIDTH_AND_HEIGHT, WIDTH_AND_HEIGHT);
        g2d.setColor(Color.GREEN);

        for (Entity e : snake) {

            e.render(g2d);
        }
    }

    /**
     * Renders all the Obstacles on the board
     */
    private void renderObstacles() {

        for(Obstacle obstacle : gameObstacles){

            obstacle.renderObstacle(g2d);
        }
    }

    /**
     * Method that shows Strings on the board
     */
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
                g2d.drawString("GAME OVER", 165, 150);
            }
            g2d.drawString("Press Enter to Restart", 140 ,170 );
        }

        g2d.setColor(Color.WHITE);

        if(snakeX == 0 && snakeY == 0){

            g2d.drawString("Click to Start!", 160 ,150 );
        }
    }
}