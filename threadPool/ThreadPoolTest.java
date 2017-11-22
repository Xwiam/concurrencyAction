/**
 * Created by xwiam on 2017/11/22.
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPool<Job> threadPool = new DefaultThreadPool<>(10);
        for (int i = 0;i < 1000;i++) {
            Job job = new Job();
            threadPool.execute(job);
        }
    }
}
