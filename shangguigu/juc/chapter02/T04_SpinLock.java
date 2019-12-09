package shangguigu.juc.chapter02;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @description: 理解自旋锁：不会立即阻塞，而是采用循环的方式去尝试获得锁，这样的好处是减少了线程上下文切换的消耗。缺点是循环消耗 CPU
 * 下面设计一个自己的自旋锁
 * @author: lixueliang
 * @create: 2019-12-06 20:56
 **/
public class T04_SpinLock {
    static class T {
        AtomicReference<Thread> atomicReference = new AtomicReference<>();

        /**
         * @Description: 加锁方法
         */
        void myLock() {
            Thread thread = Thread.currentThread();
            while (!atomicReference.compareAndSet(null, thread)) {

            }
        }

        /**
         * @Description: 释放锁方法
         */
        void myUnLock() {
            Thread thread = Thread.currentThread();
            atomicReference.compareAndSet(thread, null);
        }
    }

    public static void main(String[] args) {
        T t = new T();

        new Thread(() -> {
            System.out.println("... thread---1 start ... ");
            t.myLock();
            try {
                System.out.println("... " + Thread.currentThread().getName() + " do something for 5 seconds ... ");
                TimeUnit.SECONDS.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("... " + Thread.currentThread().getName() + " do something end ... ");
            t.myUnLock();
        }, "thread---1").start();

        try {
            System.out.println("保证前面的线程先启动");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("... thread---2 start ... ");
            t.myLock();
            try {
                System.out.println("... " + Thread.currentThread().getName() + " do something for 1 seconds ... ");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("... " + Thread.currentThread().getName() + " do something end ... ");
        }, "thread---2").start();
    }
}
