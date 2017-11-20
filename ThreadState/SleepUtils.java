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
        }
    }
}
