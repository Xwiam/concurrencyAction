/**
 * @author  xwiam on 2017/11/22.
 */
public class Job implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}