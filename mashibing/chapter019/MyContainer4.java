package mashibing.chapter019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: 需要在 notify 之后，t1 释放锁，t2 退出后也必须 notify ，通知 t1 继续执行
 * 整个通信的方式比较繁琐
 * @author: lixueliang
 * @create: 2019-12-02 20:40
 **/
public class MyContainer4 {
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer4 myContainer4 = new MyContainer4();

        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2 启动");
                if (myContainer4.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 结束");
                // 让 t1 继续执行
                lock.notify();
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1 启动");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    myContainer4.add(new Object());
                    System.out.println("add " + i);

                    if (myContainer4.size() == 5) {
                        // notify 不会释放锁，所以 t2 拿不到锁不能继续执行，只能等 t1 执行完
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();
    }
}
