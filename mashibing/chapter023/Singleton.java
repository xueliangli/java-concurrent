package mashibing.chapter023;

import java.util.Arrays;

/**
 * @description: 线程安全的单例模式
 * 普通方法，使用同步方法，使用双重检验锁
 * <p>
 * 下面的方法既不加锁也能实现懒加载，通过内部类
 * @author: lixueliang
 * @create: 2019-12-03 20:56
 **/
public class Singleton {
    private Singleton() {
        System.out.println("single");
    }

    private static class Inner {
        private static Singleton s = new Singleton();
    }

    private static Singleton getInstance() {
        return Inner.s;
    }

    public static void main(String[] args) {
        Thread[] ths = new Thread[200];
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(() -> {
                Singleton.getInstance();
            });
        }
        Arrays.asList(ths).forEach(o -> o.start());
    }
}
