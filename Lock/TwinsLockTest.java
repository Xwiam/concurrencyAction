import java.util.concurrent.locks.Lock;

/**
 * Created by xwiam on 2017/11/23.
 */
public class TwinsLockTest {

    private static final Lock lock = new TwinsLock();

    public static void main(String[] args) {
        for (int i = 0;i < 10;i++) {
            Thread thread = new Thread(new Worker(),"Thread-Worker-" + i);
            thread.setDaemon(true);
            thread.start();
        }
        for (int i = 0;i < 10;i++) {
            SleepUtils.second(1);
            System.out.println();
        }
    }

   static class Worker implements Runnable {

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    SleepUtils.second(1);
                    System.out.println(Thread.currentThread().getName());
                    SleepUtils.second(1);
                }finally {
                    lock.unlock();
                }
            }
        }
    }
}
