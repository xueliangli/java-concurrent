package shangguigu.juc.chapter05;

import java.util.concurrent.TimeUnit;

/**
 * @description: 例子：如果此时有一个线程A，按照先锁a再获得锁b的的顺序获得锁，而在此同时又有另外一个线程B，按照先锁b再锁a的顺序获得锁。
 *
 * 关键是怎么拍出死锁错误
 * @author: lixueliang
 * @create: 2019-12-09 16:37
 **/
public class T03_DeadLock {
    static class T implements Runnable {
        private Object lock1 = new Object();
        private Object lock2 = new Object();

        public T(Object lock1, Object lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + "\t 持有锁 " + lock1 + "，尝试获得锁 " + lock2);
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + "\t 持有锁 " + lock2 + "，尝试获得锁 " + lock1);
                }
            }
        }
    }

    public static void main(String[] args) {
        String lock1 = "lock1";
        String lock2 = "lock2";
        new Thread(new T(lock1, lock2), "thread---1").start();
        new Thread(new T(lock2, lock1), "thread---2").start();
    }
}
