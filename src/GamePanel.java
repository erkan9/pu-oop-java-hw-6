import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//TODO implement snake movement with MOUSE
//TODO implement pause control
//TODO end game when points are 300
//TODO refactor

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH  = 400;
    public static final int HEIGHT = 400;

    private Graphics2D g2d;
    private BufferedImage image;

    private boolean running;

    Entity head;
    Entity apple;
    ArrayList<Entity> snake;
    private final byte SIZE = 10;
    private int points;
    private byte snakeLength = 3;

    private boolean gameOver;

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

        init();

        while (running) {

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

        if(gameOver){

            if (start){

                resetBoard();
            }
            return;
        }

        snakeMovementLogic();

        snakePositionSetterInMove();

        isSnakeBiteItself();

        isSnakeAteApple();

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

            head.move(snakeX, snakeY);
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

        if(gameOver){

            if (start){

                resetBoard();
            }
        }
        //return gameOver;
    }

    /**
     * Method that checks if the snake bite itself
     */
    private void isSnakeBiteItself() {

        for(Entity e : snake) {

            if(e.isSnakeHitSomething(head)) {

                gameOver = true;
                break;
            }
        }
    }

    /**
     * Method that checks logic
     */
    private void checkLogic() {

        if(head.getCoordinateX() < 0) {

            head.setCoordinateX(WIDTH);
        }
        if(head.getCoordinateX() > WIDTH) {

            head.setCoordinateX(0);
        }
        if(head.getCoordinateY() < 0) {

            head.setCoordinateY(HEIGHT);
        }
        if(head.getCoordinateY() > HEIGHT) {

            head.setCoordinateY(0);
        }
    }

    /**
     * Method that Updates Points and Length of snake when Apple is eaten
     */
    private void isSnakeAteApple() {

        if(apple.isSnakeHitSomething(head)){

            points += 10;
            snakeLength++;

            setApple();

            Entity e = new Entity(SIZE);

            e.setPosition(-100, -100);

            snake.add(e);
        }
    }

    /**
     * Method that Renders an Image
     */
    private void requestRender() {

        render(g2d);

        Graphics g = getGraphics();

        g.drawImage(image,0,0,null);
        g.dispose();
    }

    private void init() {

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        running = true;
        resetBoard();
        gameOver = false;
    }

    /**
     * Method that Resets the Board
     */
    private void resetBoard() {

        setStartValues();

        drawSnake();

        setApple();
    }

    /**
     * Method that sets the values
     */
    private void setStartValues() {

        snake = new ArrayList<>();

        head = new Entity(SIZE);

        head.setPosition(WIDTH / 2 ,HEIGHT / 2 );

        snake.add(head);

        apple = new Entity(SIZE);

        points = 0;
        snakeLength = 3;

        snakeX = 0;
        snakeY = 0;

        gameOver = false;
    }

    /**
     * Method that draws the snake at the Start of the Game
     */
    private void drawSnake() {

        for(int i = 0; i < 2; i++) {

            Entity e = new Entity(SIZE);

            e.setPosition(head.getCoordinateX() + (i + SIZE), head.getCoordinateY());

            snake.add(e);
        }
    }

    /**
     * Method that sets an apple on the Board
     */
    public void setApple() {

        int appleXCoordinate = (int) (Math.random() * (WIDTH - SIZE));
        int appleYCoordinate = (int) (Math.random() * (HEIGHT - SIZE));

        appleXCoordinate = appleXCoordinate - (appleXCoordinate % SIZE);
        appleYCoordinate = appleYCoordinate - (appleYCoordinate % SIZE);

        apple.setPosition(appleXCoordinate, appleYCoordinate);
    }

    /**
     * Method that Renders the Snake, the Apple and Game messages
     * @param g2d Object of Graphics2D
     */
    public void render(Graphics2D g2d) {

        renderSnake();

        renderApple();

        gameMessageDisplay();

        informationDisplay();
    }

    private void renderSnake() {

        g2d.clearRect(0,0, WIDTH, HEIGHT);
        g2d.setColor(Color.GREEN);

        for (Entity e : snake) {

            e.render(g2d);
        }
    }

    private void renderApple() {

        g2d.setColor(Color.RED);
        apple.render(g2d);
    }

    private void informationDisplay() {

        g2d.drawString("Points: " + points, 7 ,11 );
        g2d.drawString("Length: " + snakeLength, 340 ,11 );
    }

    private void gameMessageDisplay() {

        if (gameOver) {
            g2d.drawString("GAME OVER", 160 ,150 );
            g2d.drawString("Press Enter to Restart", 134 ,170 );
        }
        g2d.setColor(Color.WHITE);

        if(snakeX == 0 && snakeY == 0){

            g2d.drawString("Click any key to Start!", 140 ,150 );
        }
    }
}