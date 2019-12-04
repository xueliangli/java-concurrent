package mashibing.chapter001;

public class T {
    private int count = 10;
    private Object o = new Object();

    /**
     * @Description:
     * @Param:
     * @return:
     * @Author: lixueliang
     * @Date: 2019/11/29
     */
    public void m() {
        // 线程要执行下面的代码的时候需要先加锁
        // 锁的信息记录在堆内存的对象上，互斥锁，第一个线程拿到这个对象的锁，第二个线程不能进入代码块只能在这里等待，
        // 锁定的是对象
        synchronized (o) {
            count--;
            System.out.println(Thread.currentThread().getName() + "count = " + count);
        }
    }
}
