package gamecontrols;

import game.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TimerTask;

//For some reason mouse released is not working properly, that is why booleans are set to false every time

public class Click implements MouseListener {

    java.util.Timer timer = new java.util.Timer("doubleClickTimer", false);

    int eventCnt = 0;

    /**
     * Constructor
     */
    public Click() {}

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Method that sets the booleans to true after pressed mouse
     * @param e Object of MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {

        delayDuringClick(e);
    }

    /**
     * Method that sets the booleans to false after released mouse
     * @param e Object of MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON1) {

            if (eventCnt == 2) {

                GamePanel.isGoingRight = false;
            }
            if (eventCnt == 1) {

                GamePanel.isGoingLeft = false;
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {

            if (eventCnt == 2) {

                GamePanel.isGoingDown = false;
            }
            if (eventCnt == 1) {

                GamePanel.isGoingUp = false;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Method that adds delay to mouse clicks
     * @param e Object of MouseEvent
     */
    private void delayDuringClick(MouseEvent e) {

        eventCnt = e.getClickCount();

        if (e.getClickCount() == 1) {

            timer.schedule(new TimerTask() {

                @Override
                public void run() {

                    checkClicksLogic(e);

                    eventCnt = 0;
                }
            }, 200);
        }
    }

    /**
     * Method that checks how many times is clicked ant to which Mouse button
     * @param e Object of MouseEvent
     */
    private void checkClicksLogic(MouseEvent e) {

        if (eventCnt == 1) {

            if (e.getButton() == MouseEvent.BUTTON3) {

                setGoingUpCoordinates();
            }
            if (e.getButton() == MouseEvent.BUTTON1) {

                setGoingLeftCoordinates();
            }

        } else if (eventCnt > 1) {

            if (e.getButton() == MouseEvent.BUTTON3) {

                setGoingDownCoordinates();
            }
            if (e.getButton() == MouseEvent.BUTTON1) {

                setGoingRightCoordinates();
            }
        }
    }

    /**
     * Method that sets coordinates when mouse is going Up
     * FOR SOME REASON mouseRelease IS NOT WORKING
     */
    private void setGoingUpCoordinates() {

        GamePanel.isGoingUp = true;
        GamePanel.isGoingDown = false;
        GamePanel.isGoingLeft = false;
        GamePanel.isGoingRight = false;
    }

    /**
     * Method that sets coordinates when mouse is going Left
     * FOR SOME REASON mouseRelease IS NOT WORKING
     */
    private void setGoingLeftCoordinates() {

        GamePanel.isGoingDown = false;
        GamePanel.isGoingLeft = true;
        GamePanel.isGoingUp = false;
        GamePanel.isGoingRight = false;
    }

    /**
     * Method that sets coordinates when mouse is going Down
     * FOR SOME REASON mouseRelease IS NOT WORKING
     */
    private void setGoingDownCoordinates() {

        GamePanel.isGoingDown = true;
        GamePanel.isGoingLeft = false;
        GamePanel.isGoingUp = false;
        GamePanel.isGoingRight = false;
    }

    /**
     * Method that sets coordinates when mouse is going Right
     * FOR SOME REASON mouseRelease IS NOT WORKING
     */
    private void setGoingRightCoordinates() {

        GamePanel.isGoingRight = true;
        GamePanel.isGoingDown = false;
        GamePanel.isGoingLeft = false;
        GamePanel.isGoingUp = false;
    }
}