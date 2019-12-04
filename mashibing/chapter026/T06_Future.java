package mashibing.chapter026;

import java.util.concurrent.*;

/**
 * @description: 认识什么是 Future
 * 未来的结果，代表 Callable 的返回值，线程里面可以执行 Callable 任务
 * @author: lixueliang
 * @create: 2019-12-04 10:34
 **/
public class T06_Future {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // FutureTask 与 RunnableTask 区别
        FutureTask<Integer> task = new FutureTask<>(()->{
            TimeUnit.MILLISECONDS.sleep(500);
            return 1000;
        });
        // 将来会有一个结果，在这等着
        new Thread(task).start();

        // 阻塞等结果才能执行
        System.out.println(task.get());

        // **************
        ExecutorService service = Executors.newFixedThreadPool(5);
        Future<Integer> f = service.submit(()->{
            TimeUnit.MILLISECONDS.sleep(500);
            return 1;
        });
        System.out.println(f.isDone());
        System.out.println(f.get());
        System.out.println(f.isDone());
    }
}
