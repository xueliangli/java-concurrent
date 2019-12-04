package mashibing.chapter020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: JDK 中提供的手工锁，reentrantLock（重入锁） 用来代替 synchronized
 * 本例中由于 m1 锁定 this ，只有 m1 执行完毕后，m2 才能执行
 * 这里复习 synchronized 最原始的语义
 * <p>
 * 使用 ReentrantLock 可以完成同样的功能
 * 需要注意的是，必须要手动释放锁
 * 使用 synchronized 锁定如果遇到异常的话，jvm 会自动释放锁，但是 lock 必须手动释放锁，因此经常在 finally 中进行锁的释放
 * synchronized 锁由 jvm 进行管理,手动上锁，自动释放
 * <p>
 * ReentrantLock 与 synchronized 的区别：使用 ReentrantLock 可以进行尝试锁定“”"tryLock"，或者在指定时间内无法锁定，
 * 线程可以决定是否继续等待
 * <p>
 * ReentrantLock 除了可以调用 tryLock 还可以调用 lockInterruptibly，可以对线程 interrupt 方法做出响应
 * 在一个线程等待锁的过程中可以被打断
 * @author: lixueliang
 * @create: 2019-12-03 09:15
 **/
public class ReentrantLock4 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                System.out.println("t1 end");
            } catch (InterruptedException e) {
                System.out.println("t1 interrupted ! ");
            } finally {
                lock.unlock();
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            boolean locked = false;
            try {

                // 使用原来的锁定方法， t2 会一直等待获得不了锁
//                lock.lock();

                // 可以对 interrupt 方法做出响应
                lock.lockInterruptibly();
                locked = true;
                System.out.println("t2 start");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                System.out.println("t2 interrupted ! ");
            } finally {
                if (locked) {
                    lock.unlock();
                }
            }
        });
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();
    }
}
