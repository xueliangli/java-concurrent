package mashibing.chapter004;

/**
 * @description: synchronized 用在静态方法上，相当于锁定的是这个类
 * @author: lixueliang
 * @create: 2019-11-29 22:35
 **/
public class T {
    private static int count = 10;

    /**
     * @Description: 这里等同于 synchronized(mashibing.chapter004.T.class)
     */
    public synchronized static void m() {
        count--;
        System.out.println(Thread.currentThread().getName() + "count = " + count);
    }

    public static void mm() {
        // 这里可不可以用 this，静态属性和方法没有对象 new 出来。锁定当前类的 class 对象
        synchronized (T.class) {
            count--;
        }
    }
}
