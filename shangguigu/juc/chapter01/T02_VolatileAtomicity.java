package shangguigu.juc.chapter01;

import java.util.concurrent.TimeUnit;

/**
 * @description: 1 验证 volatile 的可见性
 * 1.1 对比添加 volatile 前后程序的不同
 * <p>
 * 2 volatile 不保证原子性。不可分割，中间不能加塞，要么同时成功，要么同时失败
 * @author: lixueliang
 * @create: 2019-12-05 13:50
 **/
public class T02_VolatileAtomicity {
    /**
     * @Description: 资源类，内含有线程共享的资源
     */
    static class T {
        // 对比区别
        volatile int number = 0;

        public void setNumber() {
            this.number = 60;
        }

        /**
         * @Description: 加上 synchronized 才能保证原子性加起来的值为 20000
         */
        public synchronized void addOne() {
            // 底层被分解为三个操作
            number++;
        }
    }

    public static void main(String[] args) {
        T t = new T();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 1000; j++) {
                    t.addOne();
                }
                System.out.println(Thread.currentThread().getName() + "\t number value: " + t.number);
            }, "thread---" + i).start();
        }

        // 等待上面的计算完成，什么时候完成？活跃的线程数大于 2 说明没有完成，因为 main 和 gc 线程个占一个位置
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println("---------------");
        System.out.println(Thread.currentThread().getName() + "\t number value: " + t.number);
    }
}
