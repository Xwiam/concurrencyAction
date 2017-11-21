import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xwiam on 2017/11/21.
 */
public class ConnectionPoolTest {

    private static CountDownLatch start = new CountDownLatch(1);
    private static CountDownLatch end;
    private static ConnectionPool connectionPool = new ConnectionPool(10);
    public static void main(String[] args) throws InterruptedException{
        int threadCount = 100;
        end = new CountDownLatch(threadCount);
        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0;i < threadCount;i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }
        start.countDown();
        end.await();
        System.out.println("total invoke" + (threadCount * count));
        System.out.println("got connection : " + got);
        System.out.println("notGot connection :" + notGot);
    }
    static class ConnectionRunner implements Runnable {
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }
        @Override
        public void run() {
            try {
                start.await();
            } catch (Exception ex) {

            }
                while (count > 0) {
                    try {
                        Connection connection = connectionPool.fetchConnection(1000L);
                        if (connection != null) {
                            try {
                                connection.createStatement();
                                connection.commit();
                            } finally {
                                connectionPool.releaseConnection(connection);
                                got.incrementAndGet();
                            }
                        } else {
                            notGot.incrementAndGet();
                        }
                    } catch (Exception ex) {
                    } finally {
                        count--;
                    }
                }
            end.countDown();
        }
    }

}
