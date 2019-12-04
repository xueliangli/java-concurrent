package mashibing.chapter022;

import java.util.concurrent.TimeUnit;

/**
 * @description: 线程局部变量
 * @author: lixueliang
 * @create: 2019-12-03 11:04
 **/
public class ThreadLocal1 {
    /**
     * @Description: 写上 volatile 不会发生问题，不写有可能会出现问题
     */
    volatile static Person p = new Person();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(p.name);
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.name = "lisi";
        }).start();
    }
}

class Person {
    String name = "zahngsan";
}
