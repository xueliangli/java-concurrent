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
 * ReentrantLock 与 synchronized 的区别：使用 ReentrantLock 可以进行尝试锁定“”"tryLock"，或者在指定时间内无法锁定，线程可以决定是否继续等待
 * @author: lixueliang
 * @create: 2019-12-03 09:15
 **/
public class ReentrantLock3 {
    Lock lock = new ReentrantLock();

    void m1() {
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * @Description: 使用 tryLock 进行尝试锁定，不管锁定与否，方法都将继续执行
     * 可以根据 tryLock 的返回值来判断是否锁定
     * 也可以指定 tryLock 的时间，由于 tryLock(time) 抛出异常，所以要注意 unlock 的处理，必须放在 finally 中
     */
    void m2() {
//        boolean locked = lock.tryLock();
//        System.out.println("m2 ...");
//        if (locked) {
//            lock.unlock();
//        }

        boolean locked = false;

        try {
            // 等待锁等 5 秒，如果没有等到就继续往下执行
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("m2 ..." + locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLock3 reentrantLock2 = new ReentrantLock3();
        new Thread(reentrantLock2::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(reentrantLock2::m2).start();
    }
}
