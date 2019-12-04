package mashibing.chapter012;

import java.util.concurrent.TimeUnit;

/**
 * @description: volatile 关键字，是一个变量在多个线程间可见
 * A B 线程都用到一个变量，java 默认是 A 线程中保留一份 copy ，这样如果 B 线程修改了该变量，则 A 线程未必知道
 * 使用 volatile 关键字可以让所有线程都会读到变量的修改值
 * <p>
 * 在下面的代码中，running 是存在于堆内存的 t 对象中
 * 当线程 t1 开始运行时，会把 running 的值从内存中读到 t1 线程的工作区，在运行过程中直接使用这个 copy ，并不会每次都去读取堆内存，
 * 这样，当主线程修改 running 的值之后，t1 线程感知不到，所以不会停止运行
 * <p>
 * 使用 volatile ，将会强制所有线程都去堆内存中读取 running 的值
 * <p>
 * volatile 不能保证多个线程同时修改 running 变量时带来的不一致问题，也就是说 volatile 不能替代 synchronized
 * @author: lixueliang
 * @create: 2019-12-02 19:27
 **/
public class T {
    // 对比下有无 volatile 的情况运行结果有没有区别

//     private boolean running = true;

    private volatile boolean running = true;

    void m() {
        System.out.println("m start");
        while (running) {
            // 执行一些语句的时候，slee cpu 有空闲的时候，会去主内存读一下内存
        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        T t = new T();
        new Thread(t::m, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 写数据之后进行内存缓存通知，让其他的线程读主内存中的数据
        t.running = false;
    }
}
