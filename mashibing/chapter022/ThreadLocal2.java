package mashibing.chapter022;

import java.util.concurrent.TimeUnit;

/**
 * @description: 线程局部变量
 *
 * ThreadLocal 使用空间换时间，synchronized 使用时间换空间，共享内存改变不想其他线程知道的时候使用
 * 比如 hibernate 中 session 就存放在 ThreadLocal 中，避免 synchronized 的使用
 *
 * 提高了效率，可能会导致内存泄漏，很多框架中都用到了
 *
 * @author: lixueliang
 * @create: 2019-12-03 11:04
 **/
public class ThreadLocal2 {
    /**
     * @Description: 使用 ThreadLocal 代替 volatile
     */
    static ThreadLocal<Person> tl = new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(tl.get());
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 每个线程中有自己的变量，每个线程中拷贝了一份，改的都是自己的那份
            tl.set(new Person());
        }).start();
    }

    static class Person{
        String name = "zhangsan";
    }
}
