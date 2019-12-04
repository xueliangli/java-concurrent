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
 * <p>
 * ReentrantLock 可以指定公平锁，synchronized 默认的是非公平锁，也就是当一个线程释放锁之后，
 * 多个等待的线程谁拿到锁完全随机，而不是等待时间长的得到锁，公平锁效率低但是公平
 * @author: lixueliang
 * @create: 2019-12-03 09:15
 **/
public class ReentrantLock5 extends Thread {
    /**
     * @Description: 参数为 true 表示公平锁，对比输出结果
     */
    private static ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 获得锁");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLock5 reentrantLock5 = new ReentrantLock5();
        Thread t1 = new Thread(reentrantLock5, "t1");
        Thread t2 = new Thread(reentrantLock5, "t2");
        t1.start();
        t2.start();
    }
}
