package cn.lawwing.jisporttactics.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程操作类
 */
public class ThreadUtils {
    private static final String TAG = "ThreadUtils";
    private static ExecutorService executorService;


    private ThreadUtils() {
        executorService = new ThreadPoolExecutor(2, 5, 30L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    }

    private static class ThreadUtilHolder {
        private static final ThreadUtils INSTANCE = new ThreadUtils();
    }

    public static ThreadUtils getInstance() {
        return ThreadUtilHolder.INSTANCE;
    }

    public void execute(Runnable runnable) {
        if (executorService != null && runnable != null) {
            executorService.execute(runnable);
        }
    }


}
