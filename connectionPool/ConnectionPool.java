import java.sql.Connection;
import java.util.LinkedList;

/**
 * Created by xwiam on 2017/11/21.
 */
public class ConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for (int i = 0;i < initialSize;i++) {
                pool.add(ConnectionDriver.createConnection());
            }
        }
    }

    public  void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                pool.add(connection);
                pool.notifyAll();
            }
        }
    }

    public Connection fetchConnection(Long mills) throws InterruptedException {
        synchronized (pool) {
            if (mills < 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            }else{
                Long expire = System.currentTimeMillis() + mills;
                Long remain = mills;
                while (pool.isEmpty() && remain > 0) {
                    pool.wait(remain);
                    remain = expire - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
