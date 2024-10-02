public class Bomb {
    private int x;
    private int y;
    private int life;
    private boolean isAlive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        this.life = 10;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void bombDown() {
        if (life > 0) {
            life--;
        } else {
            isAlive = false;
        }
    }

}
