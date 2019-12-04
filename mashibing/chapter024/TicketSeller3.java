package mashibing.chapter024;

import java.util.ArrayList;
import java.util.List;
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
 *
 * 还是使用 ArrayList 只不过每次售卖的时候将 tickets 对象锁定
 * @author: lixueliang
 * @create: 2019-12-03 21:05
 **/
public class TicketSeller3 {
    static final List<String> TICKETS = new ArrayList<>();

    static {
        for (int i = 0; i < 1000; i++) {
            TICKETS.add("票编号：" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (TICKETS){
                    // 相当于判断和销售放到一个原子操作中
                    while (TICKETS.size() > 0) {
                        // 加一些模拟性的 sleep ，模拟业务逻辑
                        try {
                            TimeUnit.MILLISECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("销售了---" + TICKETS.remove(0));
                    }
                }
            }).start();
        }
    }
}
