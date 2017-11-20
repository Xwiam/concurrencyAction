/**
 * Created by xwiam on 2017/11/20.
 */
public class ThreadState {

    public static void main(String[] args) throws Exception{
        new Thread(new TimeWaiting(), "TimeWaiting").start();
        new Thread(new Waiting(),"Waiting").start();
        new Thread(new Blocking(),"Blocking-1").start();
        new Thread(new Blocking(),"Blocking-2").start();
        new Thread(new TestWaiting(),"TestWaiting-1").start();
    }

    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(100);
            }
        }
    }

    static class Waiting implements Runnable {
        @Override
        public void run() {
            synchronized (Waiting.class) {
                try {
                    Waiting.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Blocking implements Runnable {

        @Override
        public void run() {
            synchronized (Blocking.class) {
                while (true) {
                    SleepUtils.second(100);
                }
            }
        }
    }

    static class TestWaiting implements Runnable {
        @Override
        public void run() {
            synchronized (Waiting.class) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Waiting.class.notify();
            }
        }
    }

}
