package shangguigu.juc.chapter04;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @description: 验证 SynchronousQueue 无容量队列的作用
 * @author: lixueliang
 * @create: 2019-12-07 14:28
 **/
public class T02_SynchronousQueue {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        new Thread(() -> {
            System.out.println("... thread---1 start ... ");
            try {
                TimeUnit.SECONDS.sleep(1);

                System.out.println(Thread.currentThread().getName() + "\t put aa");
                blockingQueue.put("aa");

                System.out.println(Thread.currentThread().getName() + "\t put bb");
                blockingQueue.put("bb");

                System.out.println(Thread.currentThread().getName() + "\t put cc");
                blockingQueue.put("cc");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread---1").start();

        new Thread(() -> {
            System.out.println("... thread---2 start ... ");
            try {
                TimeUnit.SECONDS.sleep(1);

                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + "\t " + blockingQueue.take() + "\t 任务完成");

                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + "\t " + blockingQueue.take() + "\t 任务完成");

                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + "\t " + blockingQueue.take() + "\t 任务完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread---2").start();
    }
}
