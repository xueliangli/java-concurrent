package mashibing.chapter010;

import java.util.concurrent.TimeUnit;

/**
 * @description: 一个同步方法调用另外一个同步方法，子类调用父类的同步方法
 * @author: lixueliang
 * @create: 2019-12-02 19:08
 **/
public class T {
    synchronized void m(){
        System.out.println("m start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        // 锁定的是同一个对象，不会死锁
        new TT().m();
    }

}

class TT extends T{
    @Override
    synchronized void m(){
        System.out.println("child m start");
        super.m();
        System.out.println("child m end");
    }
}
