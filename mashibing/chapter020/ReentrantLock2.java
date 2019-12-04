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
 * @author: lixueliang
 * @create: 2019-12-03 09:15
 **/
public class ReentrantLock2 {
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

    void m2() {
        lock.lock();
        System.out.println("m2 ...");
        lock.unlock();
    }

    public static void main(String[] args) {
        ReentrantLock2 reentrantLock2 = new ReentrantLock2();
        new Thread(reentrantLock2::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(reentrantLock2::m2).start();
    }
}
