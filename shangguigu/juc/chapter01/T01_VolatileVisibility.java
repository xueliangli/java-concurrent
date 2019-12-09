package shangguigu.juc.chapter01;

import java.util.concurrent.TimeUnit;

/**
 * @description: 1 验证 volatile 的可见性
 * 1.1 对比添加 volatile 前后程序的不同
 * @author: lixueliang
 * @create: 2019-12-05 13:50
 **/
public class T01_VolatileVisibility {
    /**
     * @Description: 资源类，内含有线程共享的资源
     */
    static class T {
        // 对比区别
        volatile int number = 0;

        public void setNumber() {
            this.number = 60;
        }
    }

    public static void main(String[] args) {
        T t = new T();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t.setNumber();
            System.out.println(Thread.currentThread().getName() + "\t update number value: " + t.number);
        }, "t1").start();

        // 第二个线程就是我们的 main 线程，如果检测不到更新就一直在这阻塞
        while (t.number == 0) {
        }

        System.out.println(Thread.currentThread().getName() + "\t mission is over, number value: " + t.number);
    }
}
