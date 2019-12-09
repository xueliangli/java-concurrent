package shangguigu.juc.chapter02;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 验证 ReentrantLock 锁的可重入性质
 * @author: lixueliang
 * @create: 2019-12-06 20:47
 **/
public class T03_ReentrantLock {
    static class T {
        Lock lock = new ReentrantLock();

        void m1() {
            try {
                lock.lock();
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "\t 调用了 m1 方法");
                m2();
            } finally {
                lock.unlock();
                lock.unlock();
            }
        }

        void m2() {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "\t 调用了 m2 方法");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        T t = new T();

        new Thread(() -> {
            System.out.println("... thread---1 start ... ");
            t.m1();
        }, "thread---1").start();

        new Thread(() -> {
            System.out.println("... thread---2 start ... ");
            t.m1();
        }, "thread---2").start();
    }
}
