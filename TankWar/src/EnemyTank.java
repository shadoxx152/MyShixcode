import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
    private int x;
    private int y;
    private int direction = 2;
    Vector<Shell> enemyShells = new Vector<>();
    boolean isAlive = true;
//    private Timer timer;
    /***
     * constructor
     * @param x x position
     * @param y y position
     */
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public int getDirection() {
        return direction;
    }

    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public int getY() {
        return super.getY();
    }

    @Override
    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
    }

//    private void startShooting() {
//        timer = new Timer();
//
//        TimerTask shootTask = new TimerTask() {
//            @Override
//            public void run() {
//                // 创建炮弹并发射
//                Shell shell = new Shell(getX() + 20, getY() + 60, direction);
//                enemyShells.add(shell);
//                new Thread(shell).start(); // 启动炮弹线程
//            }
//        };
//
//        // 安排任务在初始延迟0毫秒后开始，每4000毫秒（即4秒）执行一次
//        timer.scheduleAtFixedRate(shootTask, 0, 4000);
//    }

   public void shot() {
        switch (getDirection()) {
            case 1:
                Shell shell1 = new Shell(getX() + 20, getY(), 1);
                new Thread(shell1).start();
                enemyShells.add(shell1);
                break;
            case 2:
                Shell shell2 = new Shell(getX() + 20, getY() + 60, 2);
                new Thread(shell2).start();
                enemyShells.add(shell2);
                break;
            case 3:
                Shell shell3 = new Shell(getX(), getY() + 20, 3);
                new Thread(shell3).start();
                enemyShells.add(shell3);
                break;
            case 4:
                Shell shell4 = new Shell(getX() + 60, getY() + 20, 4);
                new Thread(shell4).start();
                enemyShells.add(shell4);
                break;
        }
    }

//    public void stopShooting() {
//        if (timer != null) {
//            timer.cancel(); // 停止定时器
//        }
//    }

    @Override
    public void run() {
//        startShooting();
        while (isAlive) {

            switch (getDirection()) {
                case 1:
                    for (int i = 0;i < 30;i++) {
                        if (getY() >= 0) {
                            setY(getY() - 6);
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    shot();
                    break;
                case 2:
                    for (int i = 0;i < 30;i++) {
                        if (getY() - 1020 <= 0) {
                            setY(getY() + 6);
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    shot();
                    break;
                case 3:
                    for (int i = 0;i < 30;i++) {
                        if (getX() > 0) {
                            setX(getX() - 6);
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    shot();
                    break;
                case 4:
                    for (int i = 0;i < 30;i++) {
                        if (getX() - 1840 <= 0) {
                            setX(getX() + 6);
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    shot();
                    break;
            }
//            try {
//                Shell shell = new Shell(getX() + 20, getY() + 60, getDirection());
//                enemyShells.add(shell);
//                new Thread(shell).start();
//                Thread.sleep(4000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("spin");
            setDirection((int) (Math.random() * 4 + 1));
        }
//        stopShooting();
    }

}
