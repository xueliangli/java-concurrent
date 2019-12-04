package mashibing.chapter019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @description: 最简单的方式是使用 Latch （门闩）代替 wait 和 notify 来进行通知
 * 好处是通信方式简单，同时可以指定等待时间
 * 使用 await 和 countdown 方法来代替 wait 和 notify
 * CountDownLatch 不涉及锁定，当 count 值为零时当前线程继续执行
 * 当不涉及同步，只涉及线程间通信的时候用 synchronized + wait/notify 就显得太笨重了
 * 这时候应该考虑 CountDownLatch/CyclicBarrier/Semaphore
 * @author: lixueliang
 * @create: 2019-12-02 20:40
 **/
public class MyContainer5 {
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer5 myContainer5 = new MyContainer5();

        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            System.out.println("t2 启动");
            if (myContainer5.size() != 5) {
                try {
                    // 和 wait 的作用一样，但是不锁定对象
                    latch.await();

                    // 指定时间
//                    latch.await(5 * 1000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t2 结束");
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1 启动");
            for (int i = 0; i < 10; i++) {
                myContainer5.add(new Object());
                System.out.println("add " + i);
                if (myContainer5.size() == 5) {
                    // 打开门闩，让 t2 能够执行
                    latch.countDown();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();
    }
}
