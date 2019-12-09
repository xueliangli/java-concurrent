package shangguigu.juc.chapter05;

import java.util.concurrent.*;

/**
 * @description: 实际工作中自己实现线程池
 * <p>
 * 通过代码理解线程池的工作方式
 * @author: lixueliang
 * @create: 2019-12-09 15:59
 **/
public class T02_MyThreadPoolExecutor {
    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                2,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());

        try {
            for (int i = 1; i <= 10; i++) {
                int index = i;
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 开始执行任务" + "\t" + index);

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
    /*
    n = 9 AbortPolicy
    pool-1-thread-1	 开始执行任务
    pool-1-thread-1	 开始执行任务
    pool-1-thread-1	 开始执行任务
    pool-1-thread-1	 开始执行任务
    pool-1-thread-2	 开始执行任务
    pool-1-thread-3	 开始执行任务
    pool-1-thread-4	 开始执行任务
    pool-1-thread-5	 开始执行任务
    java.util.concurrent.RejectedExecutionException: Task shangguigu.juc.chapter05.T02_MyThreadPoolExecutor$$Lambda$1/1096979270@7ba4f24f rejected from java.util.concurrent.ThreadPoolExecutor@3b9a45b3[Running, pool size = 5, active threads = 4, queued tasks = 0, completed tasks = 4]
    	at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2063)
    	at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:830)
    	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1379)
    	at shangguigu.juc.chapter05.T02_MyThreadPoolExecutor.main(T02_MyThreadPoolExecutor.java:25)

    n = 10 CallerRunsPolicy
    pool-1-thread-1	 开始执行任务
    main	 开始执行任务
    main	 开始执行任务
    pool-1-thread-1	 开始执行任务
    pool-1-thread-1	 开始执行任务
    pool-1-thread-1	 开始执行任务
    pool-1-thread-2	 开始执行任务
    pool-1-thread-3	 开始执行任务
    pool-1-thread-5	 开始执行任务
    pool-1-thread-4	 开始执行任务

    n = 10 DiscardOldestPolicy
    pool-1-thread-2	 开始执行任务	2
    pool-1-thread-2	 开始执行任务	5
    pool-1-thread-2	 开始执行任务	9
    pool-1-thread-2	 开始执行任务	10
    pool-1-thread-1	 开始执行任务	1
    pool-1-thread-3	 开始执行任务	6
    pool-1-thread-4	 开始执行任务	7
    pool-1-thread-5	 开始执行任务	8

    n = 10 DiscardPolicy
    pool-1-thread-1	 开始执行任务	1
    pool-1-thread-1	 开始执行任务	3
    pool-1-thread-1	 开始执行任务	4
    pool-1-thread-1	 开始执行任务	5
    pool-1-thread-2	 开始执行任务	2
    pool-1-thread-5	 开始执行任务	8
    pool-1-thread-3	 开始执行任务	6
    pool-1-thread-4	 开始执行任务	7
    */

}
