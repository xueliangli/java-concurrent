package mashibing.chapter021;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @description: 生产者消费者模式，写一个固定容量的同步容器，拥有 put 和 get 方法以及 getCount 方法
 * 能够支持 2 个生产者线程和 10 个消费者线程的阻塞调用
 * 使用 wait/notify 来实现，阻塞式的同步容器，满了不能加，空了不能取
 * @author: lixueliang
 * @create: 2019-12-03 10:24
 **/
public class MyContainer1<T> {
    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    public synchronized void put(T t) {
        // 为什么这里用 while 而不用 if，wait 绝大多数都是和 while 结合在一起使用的，当一个线程没有执行到 add 的时候另外一个线程已经加进去了一个值
        while (lists.size() == MAX) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        lists.add(t);
        count++;
        // 通知消费者线程，如果用 notify 时可能叫醒的一个线程是生产者，容器满了就一直 wait ，死锁
        this.notifyAll();
    }

    public synchronized T get() {
        T t = null;
        while (lists.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        t = lists.removeFirst();
        count--;
        // 通知生产者线程
        this.notifyAll();
        return t;
    }

    public static void main(String[] args) {
        MyContainer1<String> myContainer1 = new MyContainer1<>();
        // 启动消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println(Thread.currentThread().getName() + " " + myContainer1.get());
                }
            }, "c" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    myContainer1.put(Thread.currentThread().getName() + " " + j);
                }
            }, "p" + i).start();
        }
    }
}
