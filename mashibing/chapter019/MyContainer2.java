package mashibing.chapter019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: 因为不可见所以不会结束，通过添加 volatile 使 lists 可见
 * 面试不仅要答对，要答得好才能有好工作，考虑下怎么脱颖而出
 * 不是很精确（最后可能到达 6，只能通过加锁来解决），浪费 CPU（死循环）
 * @author: lixueliang
 * @create: 2019-12-02 20:40
 **/
public class MyContainer2 {
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer2 myContainer2 = new MyContainer2();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                myContainer2.add(new Object());
                System.out.println("add " + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                if (myContainer2.size() == 5) {
                    break;
                }
            }
            System.out.println("t2 结束");
        }, "t2").start();
    }
}
