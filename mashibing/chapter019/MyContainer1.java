package mashibing.chapter019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: 面试题：实现一个容器，提供两个方法，add，size
 * 写两个线程，线程 1 添加 10 个元素到容器中，线程 2 实现监控元素的个数，当个数到 5 的时候，线程 2 给出提示并结束
 * <p>
 * 分析下列程序能否完成这个任务
 * @author: lixueliang
 * @create: 2019-12-02 20:28
 **/
public class MyContainer1 {
    List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer1 myContainer1 = new MyContainer1();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                myContainer1.add(new Object());
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
                if (myContainer1.size() == 5) {
                    break;
                }
            }
            System.out.println("t2 结束");
        }, "t2").start();
    }
}
