package com.vector.libtools;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadManager {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;
    //      对 volatile 变量的写操作，不允许和它之前的读写操作打乱顺序；对 volatile 变量的读操作，不允许和它之后的读写乱序
    private static volatile ThreadProxyPool sThreadProxyPool = null;

    public static ThreadProxyPool getThreadProxyPool() {
        ThreadProxyPool inst = sThreadProxyPool;
        if (inst == null) {
            synchronized (ThreadManager.class) {
                inst = sThreadProxyPool;
                if (inst == null) {
                    inst = new ThreadProxyPool(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE);
                    sThreadProxyPool = inst;
                }
            }
        }
        return inst;
    }

    public static class ThreadProxyPool {
        protected static final String TAG = ThreadProxyPool.class.getSimpleName();
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;
        private ThreadPoolExecutor threadPoolExecutor;

        public ThreadProxyPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        //创建线程工程
        private static final ThreadFactory sThreadFactory = new ThreadFactory() {
            //线程安全的
            private final AtomicInteger mCount = new AtomicInteger(1);

            public Thread newThread(Runnable r) {
                return new Thread(r, TAG + " #" + mCount.getAndIncrement());
            }
        };
        //存放任务的队列
        private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>();

        //在子线程中执行任务的方法
        public void execute(Runnable runnable) {
            if (runnable == null) {
                return;
            }
            if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
                threadPoolExecutor = new ThreadPoolExecutor(
                        //核心线程数
                        corePoolSize,
                        //最大线程数
                        maximumPoolSize,
                        //线程存活时间
                        keepAliveTime,
                        //存活时间单位
                        TimeUnit.MILLISECONDS,
                        //存放任务的队列
                        sPoolWorkQueue,
                        //创建线程工程
                        sThreadFactory,
                        //异常处理方式
                        new AbortPolicy());
            }

            threadPoolExecutor.execute(runnable);
        }

        public void cancel(Runnable runnable) {
            if (runnable != null && threadPoolExecutor != null && !threadPoolExecutor.isShutdown()) {
                //从线程池中将任务移除出去
                threadPoolExecutor.getQueue().remove(runnable);
            }
        }

        public void shutdown() {
            if (threadPoolExecutor != null && !threadPoolExecutor.isShutdown()) {
                threadPoolExecutor.shutdown();
            }
        }

        public void shutdownNow() {
            if (threadPoolExecutor != null && !threadPoolExecutor.isShutdown()) {
                threadPoolExecutor.shutdownNow();
            }
        }
    }

}
