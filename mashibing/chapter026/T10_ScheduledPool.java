package mashibing.chapter026;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description: 第四种线程池：执行定时任务和之前的 DelayQueue 相对应
 * @author: lixueliang
 * @create: 2019-12-04 11:42
 **/
public class T10_ScheduledPool {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        service.scheduleAtFixedRate(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
            // 第一个任务马上执行，500 毫秒之后执行下一个
        }, 0, 500, TimeUnit.MILLISECONDS);
    }
}
