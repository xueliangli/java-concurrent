package mashibing.chapter016;

import java.util.concurrent.TimeUnit;

/**
 * @description: 细粒度锁比粗粒度锁执行效率要高很多
 * @author: lixueliang
 * @create: 2019-12-02 20:09
 **/
public class T {
    int count = 0;

    synchronized void m1() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 业务逻辑只有下面一行代码需要加锁时，不应该给整个方法加锁
        count++;
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void m2() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            // 业务逻辑只有下面一行代码需要加锁时，不应该给整个方法加锁
            count++;
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
