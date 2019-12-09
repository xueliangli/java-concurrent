package shangguigu.juc.chapter01;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @description: ABA 问题的演示与解决
 * 通过 AtomicStampedReference 类解决 ABA 问题
 * @author: lixueliang
 * @create: 2019-12-06 10:13
 **/
public class T08_ABA {
    static class T {
        AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);
    }

    public static void main(String[] args) {
        T t = new T();
        System.out.println("============= 演示 ABA 问题 ============");
        new Thread(() -> {
            t.atomicReference.compareAndSet(100, 101);
            t.atomicReference.compareAndSet(101, 100);
        }, "thread---1").start();

        new Thread(() -> {
            try {
                System.out.println("do something for 1 seconds ... ");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("是否执行成功：" + t.atomicReference.compareAndSet(100, 2019) + "\t 目前共享资源的值：" + t.atomicReference.get());
        }, "thread---2").start();

        try {
            System.out.println("... "+Thread.currentThread().getName() + " do something for 3 seconds ... ");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("... "+Thread.currentThread().getName() + " do something end ... ");

        System.out.println("============= 解决 ABA 问题 ============");
        new Thread(() -> {
            System.out.println("... thread---3 start ... ");
            System.out.println(Thread.currentThread().getName() + "\t 目前的版本号：" + t.atomicStampedReference.getStamp());
            try {
                System.out.println("... do something for 1 seconds ... ");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("... do something end ... ");
            t.atomicStampedReference.compareAndSet(100, 101,
                    t.atomicStampedReference.getStamp(), t.atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 目前的版本号：" + t.atomicStampedReference.getStamp());
            t.atomicStampedReference.compareAndSet(101, 100,
                    t.atomicStampedReference.getStamp(), t.atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 目前的版本号：" + t.atomicStampedReference.getStamp());
        }, "thread---3").start();

        new Thread(() -> {
            System.out.println("... thread---4 start ... ");
            // 开始拿到版本号
            int stamp = t.atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 目前的版本号：" + t.atomicStampedReference.getStamp());
            try {
                System.out.println("... do something for 3 seconds ... ");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("... do something end ... ");
            boolean flag = t.atomicStampedReference.compareAndSet(100, 2019,
                    stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + " 是否执行成功：" + flag + "\t 目前共享资源的值：" + t.atomicStampedReference.getReference());
        }, "thread---4").start();
    }
}
