import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xwiam on 2017/11/21.
 */
public class WaitNotify {

    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) {
        new Thread(new Wait(),"Wait").start();
        SleepUtils.second(1);
        new Thread(new Notify(), "Notify").start();
    }

    static class Wait implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + "flag is true.wa@" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println(Thread.currentThread() + "flag is false.running@" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    static class Notify implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread() + "hold lock.notify @" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag = false;
                SleepUtils.second(5);
            }
            synchronized (lock) {
                System.out.println(Thread.currentThread() + "hold lock again.sleep @" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.second(5);
            }
        }
    }

}
