import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by xwiam on 2017/11/23.
 */
public class Cache {

    static Map<String, Object> map = new HashMap<>();
    static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    static Lock r = reentrantReadWriteLock.readLock();
    static Lock w = reentrantReadWriteLock.writeLock();

    public static Object get(String key) {
        r.lock();
        try {
            return map.get(key);
        }finally {
            r.unlock();
        }
    }

    public static void set(String key,Object value) {
        w.lock();
        try{
            map.put(key, value);
        }finally {
            w.unlock();
        }
    }

    public static void clear() {
        w.lock();
        try{
            map.clear();
        }finally {
            w.unlock();
        }
    }
}
