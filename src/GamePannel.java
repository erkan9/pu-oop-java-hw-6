import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GamePannel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH  = 400;
    public static final int HEIGHT = 400;

    private Graphics2D g2d;
    private BufferedImage image;

    private Thread thread;
    private boolean running;
    private long targetTime;

    private final int SIZE = 10;
    Entity head;
    ArrayList<Entity> snake;

    public GamePannel() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        thread = new Thread(this);
        thread.start();
    }

    private void setFPS(int fps) {

        targetTime = 1000 / fps;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {

        if (running)  {

            return;
        }
        init();

        long startTime;
        long elapsed;
        long wait;

        while (running) {

            startTime = System.nanoTime();

            update();
            requestRender();

            elapsed = System.nanoTime() - startTime;

            wait = (targetTime - elapsed) / 1_000_000;

            if (wait > 0) {

                try {

                    Thread.sleep(wait);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }
    }

    private void update() {
    }

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

        setUplevel();
        setFPS(10);
    }

    private void setUplevel() {

        snake = new ArrayList<Entity>();

        head = new Entity(SIZE);

        head.setPosition(WIDTH / 2 ,HEIGHT / 2 );

        snake.add(head);

        for (int i = 1; i < 10; i++) {

            Entity e = new Entity(SIZE);

            e.setPosition(head.getX() + (i * SIZE), head.getY());

            snake.add(e);
        }
    }

    public void render(Graphics2D g2d) {

        g2d.clearRect(0,0, WIDTH, HEIGHT);
        g2d.setColor(Color.GREEN);

        for (Entity e : snake) {

            e.render(g2d);
        }
    }
}
