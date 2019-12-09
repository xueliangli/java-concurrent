package shangguigu.juc.chapter04;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @description: 理解 Callable 的原理和为什么这样实现
 * @author: lixueliang
 * @create: 2019-12-07 14:51
 **/
public class T06_Callable {

    static class T implements Callable<Integer> {
        @Override
        public Integer call() {
            System.out.println(Thread.currentThread().getName() + "\t come in Callable");
            try {
                System.out.println("... " + Thread.currentThread().getName() + " futureTask 计算中 ... ");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("... " + Thread.currentThread().getName() + " futureTask 计算完毕 ... ");
            return 100;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 不同的线程需要用不同的 FutureTask
        FutureTask<Integer> futureTask = new FutureTask<>(new T());

        new Thread(futureTask, "AA").start();

        int result01 = 100;
        System.out.println(Thread.currentThread().getName() + "\t 计算完毕");

        // FutureTask 计算完才能去 get 不然会阻塞
        while (!futureTask.isDone()) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName() + "\t 等待 futureTask 计算 ... ");
        }

        int result02 = futureTask.get();
        System.out.println(Thread.currentThread().getName() + "\t 结果为：" + (result01 + result02));
    }
}
