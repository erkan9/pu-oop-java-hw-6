package gamecontrols;

import game.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Key implements KeyListener {

    /**
     * Constructor
     */
    public Key() {}

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Method that sets boolean values when key is pressed
     * @param e Object of KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_ENTER) {

            System.out.println("Game is Restarted");

            GamePanel.restartGame = true;
        }
    }

    /**
     * Method that sets boolean values when key is released
     * @param e Object of KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_ENTER) {

            GamePanel.restartGame = false;
        }
    }
}