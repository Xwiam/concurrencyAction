import java.util.concurrent.TimeUnit;

/**
 * Created by xwiam on 2017/11/20.
 */
public class ShutDownThread {

    public static void main(String[] args) throws Exception{
        Runner one = new Runner();
        Thread countThread = new Thread(one, "CountThread");
        countThread.start();

        //睡眠1秒，main线程对CountThread进行中断，使CountThread能够感知中断而结束
        TimeUnit.SECONDS.sleep(1);
        countThread.interrupt();
        Runner two = new Runner();
        countThread = new Thread(two, "CountThread");
        countThread.start();

        //睡眠1秒，main线程对CountThread进行cancle调用，改变on的值使countThread结束
        TimeUnit.SECONDS.sleep(1);
        two.cancle();
    }

    private static class Runner implements Runnable {

        private volatile boolean on = true;
        private long i;
        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println("Count i = " + i);
        }

        public void cancle() {
            on = false;
        }
    }
}
