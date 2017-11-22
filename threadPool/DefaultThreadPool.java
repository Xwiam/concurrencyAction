import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author xwiam on 2017/11/22.
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    private static int MAX_SIZE = 10;

    private static int DEFAULT_SIZE = 5;

    private static int MIN_SIZE = 1;

    private final LinkedList<Job> jobs = new LinkedList<>();

    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

    /**
     * 工作线程数
     */
    private int workNum = DEFAULT_SIZE;

    /**
     * 线程编号
     */
    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool() {
        initialWorkers(DEFAULT_SIZE);
    }

    public DefaultThreadPool(int num) {
        this.workNum = num > MAX_SIZE ? MAX_SIZE : num <MIN_SIZE ? MIN_SIZE :num;
        initialWorkers(workNum);
    }

    @Override
    public void execute(Job job) {
        if (job != null) {
            synchronized (jobs) {
                jobs.add(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutDown() {
        for (Worker worker : workers) {
            worker.shutDown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs) {
            if (num + this.workNum > MAX_SIZE) {
                num = MAX_SIZE - this.workNum;
            }
            initialWorkers(num);
            this.workNum += num;
        }
    }

    @Override
    public void removeWorkers(int num){
        synchronized (jobs) {
            if (num > this.workNum) {
                throw new IllegalArgumentException("beyond workNum");
            }
            int count = 0;
            while (count < num) {
                Worker worker = workers.get(count);
                if (workers.remove(worker)) {
                    worker.shutDown();
                    count++;
                }
            }
            this.workNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    public void initialWorkers(int num) {
        for (int i = 0;i < num;i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(new Worker(), "Thread-Pool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }



    class Worker implements Runnable {

        private volatile boolean running = true;
        @Override
        public void run() {
            while (running){
                Job job = null;
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void shutDown() {
            running = false;
        }
    }
}
