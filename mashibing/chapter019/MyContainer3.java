package mashibing.chapter019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: 使用 wait 和 notify ，前者会释放锁，后者不会释放锁，使用这两个关键词，必须进行锁定
 * 需要注意的是，使用这种方式必须保证 t2 先执行，也就是 t2 先监听
 * 需要在同步代码块中使用
 * @author: lixueliang
 * @create: 2019-12-02 20:40
 **/
public class MyContainer3 {
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer3 myContainer3 = new MyContainer3();

        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2 启动");
                if (myContainer3.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 结束");
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1 启动");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    myContainer3.add(new Object());
                    System.out.println("add " + i);

                    if (myContainer3.size() == 5) {
                        // notify 不会释放锁，所以 t2 拿不到锁不能继续执行，只能等 t1 执行完
                        lock.notify();
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
