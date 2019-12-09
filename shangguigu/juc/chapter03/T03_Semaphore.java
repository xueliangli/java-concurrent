package shangguigu.juc.chapter03;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @description: NIO 的底层，信号量，与之前两个的区别是可以复用
 * @author: lixueliang
 * @create: 2019-12-06 23:26
 **/
public class T03_Semaphore {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t 抢到钥匙");
                    try {
                        System.out.println("... " + Thread.currentThread().getName() + " do something for 3 seconds ... ");
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("... " + Thread.currentThread().getName() + " do something end ... ");

                    System.out.println(Thread.currentThread().getName() + "\t 完成，归还钥匙");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, "thread---" + i).start();
        }

    }
}
