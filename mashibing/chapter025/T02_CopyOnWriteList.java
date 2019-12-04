package mashibing.chapter025;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @description: 写时复制容器
 * 多线程下，写的效率非常低，读的效率非常高
 * <p>
 * ArrayList Vector CopyOnWrite
 *
 * 应用事件监听器
 * @author: lixueliang
 * @create: 2019-12-03 22:02
 **/
public class T02_CopyOnWriteList {
    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();
        Random r = new Random();
        Thread[] ths = new Thread[100];

        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    list.add("a" + r.nextInt(10000));
                }
            });
        }

        runAndComputeTime(ths);

        // 验证前后数据是否一致，最后的容量是否是 100000
        System.out.println(list.size());
    }

    private static void runAndComputeTime(Thread[] ths) {
        long start = System.currentTimeMillis();
        Arrays.asList(ths).forEach(t -> t.start());
        Arrays.asList(ths).forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
