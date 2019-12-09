package shangguigu.juc.chapter04;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 通过字节码探究 Lock 与 synchronized 的区别
 * @author: lixueliang
 * @create: 2019-12-07 10:54
 **/
public class T01_LockSynchronizedDiff {
    Object o = new Object();
    Lock lock = new ReentrantLock();

    void m1() {
        synchronized (o) {
            System.out.println("syn");
        }
        lock.lock();
        try {
            System.out.println("lock");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

    }
}
