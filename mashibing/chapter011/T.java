package mashibing.chapter011;

import java.util.concurrent.TimeUnit;

/**
 * @description: 程序在执行过程中，如果出现异常，锁会被释放吗？，默认会的，所以在并发处理过程中，
 * 有异常要多加小心，不然可能会发生不一致的情况。
 * 比如在一个 web app 处理过程中，多个 servlet 线程同时访问一个资源，如果异常处理不合适，
 * 在第一个县城中抛出异常，其他线程就会进入同步代码块，有可能会访问到异常产生时的数据。因此要小心处理同步业务逻辑中的异常
 * @author: lixueliang
 * @create: 2019-12-02 19:14
 **/
public class T {
    private int count = 0;
    synchronized void m(){
        System.out.println(Thread.currentThread().getName()+"start");
        while(true){
            count ++;
            System.out.println(Thread.currentThread().getName()+" count = "+count);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(count == 5){
                // 这里会抛出异常，锁就会被释放，若不想释放，需要 catch 循环将继续
                int i = 1/0;
            }
        }
    }

    public static void main(String[] args) {
        T t = new T();
        new Thread(()->t.m(),"t1").start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->t.m(),"t2").start();
    }
}
