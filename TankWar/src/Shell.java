import jdk.jshell.JShell;

public class Shell implements Runnable{
    private int x;
    private int y;
    private int direction;
    private final int speed = 10;
    boolean isAlive = true;

    public Shell(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public void run() {
        while (isAlive) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (direction) {
                case 1:
                    y -= speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
                case 4:
                    x += speed;
                    break;
            }
            if (!(x >= 0 && x <= 1920 && y >= 0 && y <= 1080)) {
                isAlive = false;
                break;
            }
        }
    }

    public int getDirection() {
        return direction;
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
