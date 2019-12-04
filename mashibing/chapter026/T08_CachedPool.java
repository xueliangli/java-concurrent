package mashibing.chapter026;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description: 第二种线程池：缓存的线程池
 * 来一个任务如果没有空闲的就新启动一个线程，知道内存不够，这些线程默认空闲 60 秒之后就会自动销毁
 * @author: lixueliang
 * @create: 2019-12-04 11:31
 **/
public class T08_CachedPool {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println(service);

        for (int i = 0; i < 2; i++) {
            service.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }

        System.out.println(service);

        try {
            TimeUnit.SECONDS.sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(service);
    }
}
