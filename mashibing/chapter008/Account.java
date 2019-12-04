package mashibing.chapter008;

import java.util.concurrent.TimeUnit;

/**
 * @description: 银行账户：对业务的写方法加锁；对业务读方法不加锁；容易产生脏读问题
 * 在哪些方法上加锁不加锁是根据那段代码的业务逻辑指定的，只对写进行加锁可能读到在写中还没有完成的数据
 * @author: lixueliang
 * @create: 2019-11-29 23:10
 **/
public class Account {
    private String name;
    private double balance;

    private synchronized void set(String name, double balance) {
        this.name = name;

        // 在这两个赋值之间可能调用其他的业务逻辑，比如获得值，如果不睡这两秒钟，是不能看出脏读现象的
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.balance = balance;
    }

    private /*synchronized*/ double getBalance(String name) {
        return this.balance;
    }

    public static void main(String[] args) {
        Account a = new Account();
        new Thread(() -> a.set("zhangsan", 100.0)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(a.getBalance("zhangsan"));

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(a.getBalance("zhangsan"));
    }
}
