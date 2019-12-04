package mashibing.chapter024;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * @description: 火车票售卖业务情景
 * 有 N 张火车票，每张票都有一个编号，同时又 10 个窗口对外售票
 * 请完成一个程序模拟上述场景
 * <p>
 * 分析下面程序会产生那些问题？
 * 重复销售？超量销售？
 *
 * 使用同步容器 vector 代替 arraylist，仍然存在问题，判断与实现分离了，if 和 remove
 * @author: lixueliang
 * @create: 2019-12-03 21:05
 **/
public class TicketSeller2 {
    static Vector<String> tickets = new Vector<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("票编号：" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (tickets.size() > 0) {
                    // 加一些模拟性的 sleep ，模拟业务逻辑
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("销售了---" + tickets.remove(0));
                }
            }).start();
        }
    }
}
