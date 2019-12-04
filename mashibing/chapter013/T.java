package mashibing.chapter013;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: volatile 保证原子性吗？不保证，volatile 并不能保证多个线程同时修改 running 变量时带来的不一致问题
 * volatile 和 synchronized 的区别，找篇文章背下来
 * @author: lixueliang
 * @create: 2019-12-02 19:51
 **/
public class T {
    volatile int count = 0;

    void m() {
        for (int i = 0; i < 10000; i++) {
            count++;
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
