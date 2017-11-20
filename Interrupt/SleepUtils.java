import java.util.concurrent.TimeUnit;

/**
 * Created by xwiam on 2017/11/20.
 */
public class SleepUtils {

    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();

            //抛出InterruptedException异常后，如果不将interrupt重置为true，再调用Thread.sleep方法时，将不再响应任何中断请求
           // Thread.currentThread().interrupt();
        }
    }
}
