package mashibing.chapter025;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @description: 解耦合，实现生产者消费者
 *
 * Queue 分两种，一种是阻塞式（满了或者空了就等待）的，一种是并发的加锁的
 * @author: lixueliang
 * @create: 2019-12-03 22:26
 **/
public class T05_LinkedBlockingQueue {
    /**
     * @Description: 无界队列
     */
    static BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

    static Random r = new Random();

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    // 如果满了就会等待
                    blockingQueue.put("a" + i);
                    TimeUnit.MILLISECONDS.sleep(r.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "p1").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (; ; ) {
                    try {
                        // 如果空了就会等待
                        System.out.println(Thread.currentThread().getName() + "take - " + blockingQueue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "c" + i).start();
        }
    }
}
