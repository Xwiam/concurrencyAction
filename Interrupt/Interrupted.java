import java.util.concurrent.TimeUnit;

/**
 * Created by xwiam on 2017/11/20.
 */
public class Interrupted {

    public static void main(String[] args) throws Exception{
        //sleepThread不停地尝试睡眠
        Thread sleepThread = new Thread(new SleepRunner(), "sleepRunner");

        //busyThread不停地运行
        Thread busyThread = new Thread(new BusyRunner(), "busyRunner");
        sleepThread.start();
        busyThread.start();

        //休眠5秒，让sleepThread和busyThread充分运行
        TimeUnit.SECONDS.sleep(20);

        sleepThread.interrupt();
        busyThread.interrupt();
        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
        //防止sleepThread和busyThread立即退出
        SleepUtils.second(3);
    }


    static class SleepRunner implements Runnable {

        @Override
        public void run() {
            while (true) {
                SleepUtils.second(10);
            }
        }
    }

    static class BusyRunner implements Runnable {
        @Override
        public void run() {
            while (true) {

            }
        }
    }

}
