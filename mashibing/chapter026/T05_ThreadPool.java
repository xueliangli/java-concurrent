package mashibing.chapter026;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description: 线程池的概念
 *
 * 什么叫线程池？一堆线程维护着两个队列，一个维护着任务列表还没被执行的任务队列，结束的任务队列，任务结束扔进去
 * @author: lixueliang
 * @create: 2019-12-04 10:19
 **/
public class T05_ThreadPool {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 6; i++) {
            service.execute(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println(service);
        service.shutdown();
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("------------------");
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);
    }
}
