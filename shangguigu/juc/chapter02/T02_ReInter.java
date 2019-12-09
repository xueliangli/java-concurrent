package shangguigu.juc.chapter02;

/**
 * @description: 可重入鎖的驗證，ReenterLock 和 synchronized 都是可重入锁，目的是防止死锁
 * @author: lixueliang
 * @create: 2019-12-06 11:47
 **/
public class T02_ReInter {
    static class T {
        public synchronized void m1() {
            System.out.println(Thread.currentThread().getName() + "\t 调用了第一个同步方法 ... ");
            m2();
        }

        public synchronized void m2() {
            System.out.println(Thread.currentThread().getName() + "\t 调用了第二个同步方法 ... ");
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
