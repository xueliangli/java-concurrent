package mashibing.chapter005;

/**
 * @description: 分析程序的输出
 * @author: lixueliang
 * @create: 2019-11-29 22:42
 **/
public class T implements Runnable {
    private int count = 10;

    /**
     * @Description: 通过加互斥锁可以解决 ，相当于一个原子操作不可分
     */
    @Override
    public void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void main(String[] args) {
        // 在 main 线程中 new 了一个对象，好多线程共同访问这个对象
        T t = new T();
        for (int i = 0; i < 5; i++) {
            new Thread(t, "THREAD" + i).start();
        }
    }
}
