package mashibing.chapter014;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 使用 synchronized 解决上一个问题
 * @author: lixueliang
 * @create: 2019-12-02 20:00
 **/
public class T {
    int count = 0;

    synchronized void m() {
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
