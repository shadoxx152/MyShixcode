import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyTank extends Tank implements Runnable, KeyListener {
    private int x;
    private int y;
    private int direction = 1;
//    private Shell shell = null;
    Vector<Shell> myShell = new Vector<>();
    private boolean isAlive = true;
    public MyTank(int x, int y) {
        super(x, y);
        this.x = x;
        this.y = y;
    }

    public void shot() {
        switch (getDirection()) {
            case 1:
                Shell shell1 = new Shell(getX() + 20, getY(), 1);
                new Thread(shell1).start();
                myShell.add(shell1);
                break;
            case 2:
                Shell shell2 = new Shell(getX() + 20, getY() + 60, 2);
                new Thread(shell2).start();
                myShell.add(shell2);
                break;
            case 3:
                Shell shell3 = new Shell(getX(), getY() + 20, 3);
                new Thread(shell3).start();
                myShell.add(shell3);
                break;
            case 4:
                Shell shell4 = new Shell(getX() + 60, getY() + 20, 4);
                new Thread(shell4).start();
                myShell.add(shell4);
                break;
        }
    }

    @Override
    public int getDirection() {
        return direction;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void die() {
        this.isAlive = false;
    }

    public void respawn() {
        this.isAlive = true;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    @Override
    public void run() {
        while (true) {
            if (!isAlive) {
                try {
//                    setX(10000);
//                    setY(10000);
                    // Wait for 3 seconds to simulate death
                    Thread.sleep(3000);

                    // Revive the tank by resetting the position and marking it alive
                    setDirection(1);
                    setX(960);
                    setY(900);
                    respawn();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Optional: Sleep for a short duration to prevent busy-waiting
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            this.setDirection(1);
            if (getY() >= 0) {
                this.setY(this.getY() - 6);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            this.setDirection(2);
            if (getY() - 1020 <= 0) {
                this.setY(this.getY() + 6);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            this.setDirection(3);
            if (getX() >= 0) {
                this.setX(this.getX() - 6);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            this.setDirection(4);
            if (getX() - 1840 <= 0) {
                this.setX(this.getX() + 6);
            }

        }

        if (e.getKeyCode() == KeyEvent.VK_J) {
            this.shot();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
