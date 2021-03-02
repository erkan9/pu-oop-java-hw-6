package gamefigures;

import java.awt.*;

public class Entity {

    private int coordinateX;
    private int coordinateY;
    private final int size;

    /**
     * Constructor
     * @param size Size of the entities.Entity
     */
    public Entity(int size) {

        this.size = size;
    }

    /**
     * Method that gets the X coordinate
     * @return The X coordinate
     */
    public int getCoordinateX() {
        return this.coordinateX;
    }

    /**
     * Method that gets the Y coordinate
     * @return The Y coordinate
     */
    public int getCoordinateY() {
        return this.coordinateY;
    }

    /**
     * Method that sets the X coordinate
     */
    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    /**
     * Method that sets the Y coordinate
     */
    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    /**
     * Method that sets a Position
     * @param x X coordinates
     * @param y Y coordinates
     */
    public void setPosition(int x, int y) {

        this.coordinateX = x;
        this.coordinateY = y;
    }

    /**
     * Method that does the movement of the Entities
     * @param dx displacement X
     * @param dy displacement Y
     */
    public void move(int dx, int dy){

        this.coordinateX += dx;
        this.coordinateY += dy;
    }

    /**
     * Method that creates a new Rectangle
     * @return A new rectangle
     */
    public Rectangle getBound() {

        return new Rectangle(this.coordinateX, this.coordinateY, this.size, this.size);
    }

    /**
     * Method that checks if the Snake hit with something
     * @param o Object of entities.Entity
     * @return if snake hit with something
     */
    public boolean isSnakeHitSomething(Entity o){

        if ( o == this) {

            return false;
        }
        return getBound().intersects(o.getBound());
    }

    /**
     * Method that renders the Snake's Rectangles
     * @param g2d Object of Graphics2D
     */
    public void render(Graphics2D g2d) {

        g2d.fillRect(this.coordinateX + 1, this.coordinateY + 1,this.size - 2,this.size - 2);
    }
}