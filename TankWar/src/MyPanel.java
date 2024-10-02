import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable{
    //initialize our tank
    MyTank myTank;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    Vector<Bomb> bombs = new Vector<>();
    int numberOfEnemy = 3;
    int dead = 0;

    Image bomb1;
    Image bomb2;
    Image bomb3;
    /***
     * constructor
     */
    public MyPanel() {
        myTank = new MyTank(960, 900); //There are our tanks on the drawing board
        new Thread(myTank).start();
        this.addKeyListener(myTank);

        // 让面板能够聚焦以接收键盘事件
        this.setFocusable(true);
        //this.requestFocusInWindow();
        for (int i = 0;i < numberOfEnemy;i++) {
            EnemyTank enemyTank = new EnemyTank(100 + i * 100, 10);
            new Thread(enemyTank).start();
            enemyTanks.add(enemyTank);
        }

        bomb1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/bomb1.png"))).getImage();
        bomb2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/bomb2.png"))).getImage();
        bomb3 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/bomb3.png"))).getImage();
    }


    private void info(Graphics g, int num, int deadNumber) {
        g.setColor(Color.white);
        g.setFont(new Font("Times New Roman", Font.BOLD, 20));
        System.out.println(num);
        g.drawString("Cumulative defeats of enemy tanks:" + num, 1020, 30);
        g.drawString("The number of our deaths: " + deadNumber, 1020, 50);
    }

    /***
     * drawing
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1920, 1080);
        int num = 3 - numberOfEnemy;
        info(g, num, dead);
        if (myTank.getIsAlive()) {
            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirection(), 0);
        }

        for (EnemyTank tank : enemyTanks) {
            drawTank(tank.getX(), tank.getY(), g, tank.getDirection(), 1);
            for (Shell myshell : myTank.myShell) {
                if (isHit(tank, myshell)) {
                    myshell.setAlive(false);
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                    tank.setX(10000);
                    tank.setY(10000);
                    numberOfEnemy--;
                }
            }
            g.setColor(Color.blue);
            ArrayList<Shell> removeEnemyShell = new ArrayList<>();
            for (Shell shell : tank.enemyShells) {
                if (!shell.isAlive) {
                    removeEnemyShell.add(shell);
                }
                if (isHit(myTank, shell)) {
                    Bomb bomb = new Bomb(myTank.getX(), myTank.getY());
                    bombs.add(bomb);
                    myTank.die();
                    shell.setAlive(false);
                    dead++;
                    myTank.setX(10000);
                    myTank.setY(10000);
                }
                g.fillOval(shell.getX() - 6, shell.getY() - 6, 10, 10);
            }
            tank.enemyShells.removeAll(removeEnemyShell);
        }
        g.setColor(Color.red);
        ArrayList<Shell> removeMy = new ArrayList<>();
        for (Shell shell : myTank.myShell) {
            if (!shell.isAlive) {
                removeMy.add(shell);
            }
            g.fillOval(shell.getX() - 6, shell.getY() - 6, 10, 10);
        }
        myTank.myShell.removeAll(removeMy);

        if (!bombs.isEmpty()) {
            ArrayList<Bomb> removeBomb = new ArrayList<>();
            for (Bomb tBomb : bombs) {
                if (tBomb.getLife() > 6) {
                    g.drawImage(bomb1, tBomb.getX(), tBomb.getY(), 60, 60, this);
                } else if (tBomb.getLife() > 3) {
                    g.drawImage(bomb2, tBomb.getX(), tBomb.getY(), 60, 60, this);
                } else if (tBomb.getLife() > 0){
                    g.drawImage(bomb3, tBomb.getX(), tBomb.getY(), 60, 60, this);
                }

                tBomb.bombDown();

                if (tBomb.getLife() <= 0) {
                    removeBomb.add(tBomb);
                }
            }
            bombs.removeAll(removeBomb);
        }
    }
//            g.fillOval(myTank.getShell().getX() - 6, myTank.getShell().getY() - 6, 10, 10);

    public void drawTank(int x, int y, Graphics g, int direction, int type) {
        switch (type) {
            case 0:
                g.setColor(Color.red);
                break;
            case 1:
                g.setColor(Color.blue);
                break;
        }

        switch (direction) {
            case 1://up
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y);
                break;
            case 2://down
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3 : // left
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x , y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            case 4 : // right
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x , y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
        }
    }

    /***
     * Override KeyListener method
     * @param e the event to be processed
     */
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
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
            this.repaint();
        }
    }

    private boolean isHit(Tank lose, Shell shell) {
        int lx = lose.getX();
        int ly = lose.getY();
        int sx = shell.getX();
        int sy = shell.getY();
        switch (lose.getDirection()) {
            case 1:
                return (sx >= lx && sx <= lx + 40 && sy >= ly && sy <= ly + 60);
            case 2:
                return (sx >= lx && sx <= lx + 40 && sy >= ly && sy <= ly + 60);
            case 3:
                return (sx >= lx && sx <= lx + 60 && sy >= ly && sy <= ly + 40);
            case 4:
                return (sx >= lx && sx <= lx + 60 && sy >= ly && sy <= ly + 40);
        }
        return true;
    }

}
