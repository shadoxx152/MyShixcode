public class Tank {
    /***
     * Tank property : 'x' position, 'y' position, direction.
     */
    private int x;
    private int y;
    private int direction;
    boolean isAlive = true;

    /***
     * constructor
     * @param x x position
     * @param y y position
     */
    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /***
     * Getter & Setter
     */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

}
