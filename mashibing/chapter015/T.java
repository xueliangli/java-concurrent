package mashibing.chapter015;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 如果只是处理 ++-- 等运算，java 里提供了 AtomXXX 原子类
 * AtomXXX 原子类本身是原子性的，但是不能保证多个方法连续调用是原子性的
 * 写个程序证明多个方法不构成原子性
 * 证明可见性
 * 证明比 synchronized 高效
 * @author: lixueliang
 * @create: 2019-12-02 20:01
 **/
public class T {
    AtomicInteger count = new AtomicInteger(0);

    void m() {
        for (int i = 0; i < 10000; i++) {
            // if count.get() < 1000
            count.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        T t = new T();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(t::m, "thread - " + i));
        }
        threads.forEach(Thread::start);
        threads.forEach((o) -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(t.count);
    }
}
