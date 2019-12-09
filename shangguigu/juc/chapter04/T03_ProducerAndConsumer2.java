package shangguigu.juc.chapter04;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 三种方式实现生产者消费者模式，实现的功能是生产一个消费一个。使用 Lock 实现，与马士兵课程中的 MyContainer 类似。只不过这里的最大容量为 1
 * 1 synchronized wait notify
 * <p>
 * 2 ReentrantLock await signalAll （Condition）
 * <p>
 * 3 阻塞队列
 * @author: lixueliang
 * @create: 2019-12-07 14:44
 **/
public class T03_ProducerAndConsumer2 {
    /**
     * @Description: 共享的资源类
     */
    static class T {
        private int number = 0;
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public void increment() {
            lock.lock();
            try {
                while (number != 0) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                number++;
                System.out.println(Thread.currentThread().getName() + "\t number: " + number);
                condition.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void decrement() {
            lock.lock();
            try {
                while (number == 0) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                number--;
                System.out.println(Thread.currentThread().getName() + "\t number: " + number);
                condition.signalAll();
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
            System.out.println("... thread---producer start ... ");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 10; i++) {
                t.increment();
            }
        }, "thread---producer").start();

        new Thread(() -> {
            System.out.println("... thread---consumer start ... ");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 10; i++) {
                t.decrement();
            }
        }, "thread---consumer").start();
    }
}
