package shangguigu.juc.chapter05;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description: 创建三种基本的线程池的测试
 * <p>
 * 新建线程一共有三种方式：
 * 1 继承 Thread 类
 * 2 实现 Runnable 接口（无返回值，不抛异常）
 * 3 实现 Callable 接口（有返回值，抛异常）
 * 4 使用线程池
 * @author: lixueliang
 * @create: 2019-12-09 14:53
 **/
public class T01_NewThreadPool {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        ExecutorService threadPool1 = Executors.newSingleThreadExecutor();
        ExecutorService threadPool2 = Executors.newCachedThreadPool();
        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {

                    System.out.println(Thread.currentThread().getName() + "\t 执行任务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("... " + Thread.currentThread().getName() + " do something for 5 seconds ... ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---------------");
        try {
            for (int i = 1; i <= 10; i++) {
                threadPool1.execute(()->{
                    System.out.println(Thread.currentThread().getName() + "\t 开始执行任务");

                });
             }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool1.shutdown();
        }

        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("... " + Thread.currentThread().getName() + " do something for 5 seconds ... ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--------------");
        try {
            for (int i = 1; i <= 10; i++) {
                threadPool2.execute(()->{
                    System.out.println(Thread.currentThread().getName() + "\t 开始执行任务");

                });
             }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool2.shutdown();
        }
    }
}
