package shangguigu.juc.chapter04;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 一道通过 Condition 解决的典型题目
 * <p>
 * 题目：多线程间的顺序调用，实现 A->B->C 三个线程启动，要求 A 打印 5 次，B 打印 10 次，C 打印 15 次，循环十轮
 * @author: lixueliang
 * @create: 2019-12-07 14:50
 **/
public class T04_Condition {
    static class T {
        /**
         * @Description: A 1 B 2 C 3
         */
        private int n = 1;
        Lock lock = new ReentrantLock();
        Condition a = lock.newCondition();
        Condition b = lock.newCondition();
        Condition c = lock.newCondition();

        public void print5Times() {
            lock.lock();
            try {
                while (n != 1) {
                    a.await();
                }
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                }
                n = 2;
                b.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void print10Times() {
            lock.lock();
            try {
                while (n != 2) {
                    b.await();
                }
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                }
                n = 3;
                c.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void print15Times() {
            lock.lock();
            try {
                while (n != 3) {
                    c.await();
                }
                for (int i = 0; i < 15; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                }
                n = 1;
                a.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        T t = new T();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                t.print5Times();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                t.print10Times();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                t.print15Times();
            }
        }, "C").start();
    }
}
