package mashibing.chapter026;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description: 第五种线程池：每个线程对维护一个自己的队列，当当前的任务执行完成后会主动去拿还没执行的任务
 * 能够效率更高的执行任务，多劳多得
 * @author: lixueliang
 * @create: 2019-12-04 13:34
 **/
public class T11_WorkStealingPool {
    public static void main(String[] args) {
        // 底层是通过 ForkJoinPool 实现的
        ExecutorService service = Executors.newWorkStealingPool();
        System.out.println(Runtime.getRuntime().availableProcessors());

        service.execute(new R(1000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        // 第五个任务一定是由第一个先结束的线程来执行
        service.execute(new R(2000));

        // 由于产生的是精灵线程（守护线程、后台线程），主线程不阻塞的话看不到输出，如果不让主函数通过这个方式阻塞住，是看不到输出的，在后台不断的运行，jvm 不退出就不停止
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 简单的实现 Runnable 的任务
     */
    static class R implements Runnable {
        int time;

        R(int t) {
            this.time = t;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(time + " " + Thread.currentThread().getName());
        }
    }
}
