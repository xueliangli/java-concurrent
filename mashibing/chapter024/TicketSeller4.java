package mashibing.chapter024;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @description: 火车票售卖业务情景
 * 有 N 张火车票，每张票都有一个编号，同时又 10 个窗口对外售票
 * 请完成一个程序模拟上述场景
 * <p>
 * 分析下面程序会产生那些问题？
 * 重复销售？超量销售？
 * <p>
 * 使用同步容器 vector 代替 arraylist，仍然存在问题，判断与实现分离了，if 和 remove
 * <p>
 * 还是使用 ArrayList 只不过每次售卖的时候将 tickets 对象锁定
 * <p>
 * 使用上面的重入锁效率不高，可以使用一个并发容器 queue
 * @author: lixueliang
 * @create: 2019-12-03 21:05
 **/
public class TicketSeller4 {
    static Queue<String> tickets = new ConcurrentLinkedDeque<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("票编号：" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    // 底层是 cas 不加锁实现，很高效
                    String s = tickets.poll();
                    if (s == null) {
                        break;
                    } else {
                        // 这里没有进行 remove 也就是对队列的修改操作
                        System.out.println(Thread.currentThread().getName() + " 销售了---" + s);
                    }
                }
            }).start();
        }
    }
}
