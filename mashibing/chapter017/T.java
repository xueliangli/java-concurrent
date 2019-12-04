package mashibing.chapter017;

import java.util.concurrent.TimeUnit;

/**
 * @description: 锁定莫个对象 o，如果 o 的属性发生变化，不影响锁的使用
 * 但是如果变成了另一个对象，则锁定的对象发生改变
 * 应该避免将锁定对象的引用变成另外一个对象
 * @author: lixueliang
 * @create: 2019-12-02 20:13
 **/
public class T {
    // 证明是锁在堆内存里面的对象上，而不是栈内存中的引用

    Object o = new Object();

    void m() {
        synchronized (o) {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        T t = new T();
        // 启动第一个线程
        new Thread(t::m, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(t::m, "t2");
        t.o = new Object();
        t2.start();
    }
}
