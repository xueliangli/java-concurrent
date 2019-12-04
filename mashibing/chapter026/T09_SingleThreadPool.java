package mashibing.chapter026;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: 第三种线程池：里面只有一个线程，作用是保证线程是顺序执行的，先来的先执行
 * 如果好多线程来执行并不保证前后的顺序
 * @author: lixueliang
 * @create: 2019-12-04 11:38
 **/
public class T09_SingleThreadPool {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            final int j = i;
            service.execute(() -> {
                System.out.println(j + " " + Thread.currentThread().getName());
            });
        }
    }
}
