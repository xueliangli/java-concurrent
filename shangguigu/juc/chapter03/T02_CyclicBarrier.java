package shangguigu.juc.chapter03;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @description: 一种与 CountDownLatch 相反的同步机制
 * @author: lixueliang
 * @create: 2019-12-06 23:26
 **/
public class T02_CyclicBarrier {
    public static void main(String[] args) {
        m1();
    }

    private static void m1() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(
                6,
                () -> System.out.println(Thread.currentThread().getName() + "\t 完成任务。。。。。。。。。。。"));

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 执行了 ...");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "thread---" + i).start();
        }
    }
}