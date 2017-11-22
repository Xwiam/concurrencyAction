/**
 * Created by xwiam on 2017/11/22.
 */
public interface ThreadPool<Job extends Runnable> {

    /**
     * 执行一个job，该job实现Runnable接口
     * @param job
     */
    void execute(Job job);

    /**
     * 关闭线程池
     */
    void shutDown();

    /**
     * 添加工作者线程
     * @param num
     */
    void addWorkers(int num);

    /**
     * 移除工作者线程
     * @param num
     */
    void removeWorkers(int num);

    /**
     * 获取job数目
     * @return
     */
    int getJobSize();

}
