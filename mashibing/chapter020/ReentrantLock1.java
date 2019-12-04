package mashibing.chapter020;

import java.util.concurrent.TimeUnit;

/**
 * @description: JDK 中提供的手工锁，reentrantLock（重入锁） 用来代替 synchronized
 * 本例中由于 m1 锁定 this ，只有 m1 执行完毕后，m2 才能执行
 * 这里复习 synchronized 最原始的语义
 * @author: lixueliang
 * @create: 2019-12-03 09:15
 **/
public class ReentrantLock1 {
    synchronized void m1() {
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
    }

    synchronized void m2() {
        System.out.println("m2 ...");
    }

    public static void main(String[] args) {
        ReentrantLock1 reentrantLock1 = new ReentrantLock1();
        new Thread(reentrantLock1::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(reentrantLock1::m2).start();
    }
}
