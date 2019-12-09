package shangguigu.juc.chapter01;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @description: 理解 CAS 操作
 * 1 CAS 是什么？
 * 比较交换，如果线程工作内存的期望值与主内存中值一样就对物理内存进行修改；如果不一样就不进行修改并返回主内存目前的值
 * <p>
 * 2 底层原理
 * 自旋锁；unsafe 类
 * unsafe 类直接操纵的是指针，里面的大多数方法都是 native 修饰的。根据内存偏移地址来获取数据。
 * <p>
 * 3 遇到的问题 ABA
 * <p>
 * 4 怎么解决 ABA 问题，原子引用, AtomicStampedReference
 *
 * @author: lixueliang
 * @create: 2019-12-05 19:45
 **/
public class T07_CAS {
    static class T {
        String name;
        int age;

        public T(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    public static void main(String[] args) {
        // 主线程拿到的是 0
        AtomicInteger atomicInteger = new AtomicInteger(0);

        try {
            System.out.println("do something ... ");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 如果符合期望值就可以修改改成修改值
        System.out.println(atomicInteger.compareAndSet(0, 2019) + "\t current data: " + atomicInteger.get());
        try {
            System.out.println("do something ... ");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(atomicInteger.compareAndSet(0, 2018) + "\t current data: " + atomicInteger.get());

        atomicInteger.getAndIncrement();

        System.out.println("测试原子引用：--------------");
        T t1 = new T("aaa", 1);
        T t2 = new T("bbb", 2);
        AtomicReference<T> atomicReference = new AtomicReference<>();
        atomicReference.set(t1);
        System.out.println(atomicReference.compareAndSet(t1, t2) + "\t" + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(t1, t2) + "\t" + atomicReference.get().toString());


    }
}
