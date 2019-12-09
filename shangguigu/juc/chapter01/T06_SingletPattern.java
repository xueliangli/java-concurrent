package shangguigu.juc.chapter01;

import java.util.concurrent.TimeUnit;

/**
 * @description: 多线程下的单例模式
 * @author: lixueliang
 * @create: 2019-12-05 18:53
 **/
public class T06_SingletPattern {
    /**
     * @Description: 单线程下的单例模式
     */
    static class Single {
        private static Single single = null;

        private Single() {
            System.out.println(Thread.currentThread().getName() + "\t 我是构造方法 Single");
        }

        // 直接加 synchronized 就可以，但是效率不高
        public static Single getSingleInstance() {
            if (single == null) {
                single = new Single();
            }
            return single;
        }
    }

    static class SingleConcurrent1 {
        // 需要在这加上 volatile 解决指令重排
        private static volatile SingleConcurrent1 singleConcurrent1 = null;

        private SingleConcurrent1() {
            System.out.println(Thread.currentThread().getName() + "\t 我是构造方法 SingleConcurrent");
        }

        public static SingleConcurrent1 getSingleConcurrent1() {
            if (singleConcurrent1 == null) {
                synchronized (SingleConcurrent1.class) {
                    if (singleConcurrent1 == null) {
                        /*
                        下面代码可能发生指令重排
                        memory = allocate() // 1 分配对象内存空间
                        instance(memory)    // 2 初始化对象
                        instance = memory   // 3 设置 instance 指向刚分配的内存地址，此时 instance != null
                        2 3 可能会调换顺序，导致有的线程拿到的地址不为空，可是指向的对象为空
                        */
                        singleConcurrent1 = new SingleConcurrent1();
                    }
                }
            }
            return singleConcurrent1;
        }
    }

    public static void main(String[] args) {
        // 验证单线程模式下的单例模式
//        System.out.println("单线程下验证：-----------");
//        System.out.println(Single.getSingleInstance() == Single.getSingleInstance());
//        System.out.println(Single.getSingleInstance() == Single.getSingleInstance());
//        System.out.println(Single.getSingleInstance() == Single.getSingleInstance());

        // 必须先将单线程的验证注释掉
//        System.out.println("多线程下验证：-----------");
//        for (int i = 1; i <= 10; i++) {
//            new Thread(() -> {
//                try {
//                    TimeUnit.MILLISECONDS.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Single.getSingleInstance();
//            }, "thread---" + i).start();
//        }

        // 指令重排可能导致出错，怎么加 volatile ，为什么加？
        System.out.println("双重锁多线程下验证：-----------");
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SingleConcurrent1.getSingleConcurrent1();
            }, "thread---" + i).start();
        }
    }
}
