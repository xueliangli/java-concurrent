package mashibing.chapter009;

import java.util.concurrent.TimeUnit;

/**
 * @description: 一个同步方法是不是可以调用另外一个同步方法？可以，
 * 一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁，也就是 synchronized 获得的锁是可重入的
 * @author: lixueliang
 * @create: 2019-12-02 18:58
 **/
public class T {
    synchronized void m1(){
        System.out.println("m1 start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
    }

    synchronized void m2() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2");
    }
}
