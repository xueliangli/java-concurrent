package mashibing.chapter025;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @description: 有界阻塞式队列
 * 在线程池中装的是一个个任务
 * @author: lixueliang
 * @create: 2019-12-03 22:34
 **/
public class T06_ArrayBlockingQueue {
    static BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);

    static Random r = new Random();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            try {
                blockingQueue.put("a" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            // 会抛出异常
            blockingQueue.add("aaa");
        } catch (Exception e) {
            System.out.println("add 错误");
        }
        // 不会抛异常，也不会添加，可以指定时间段进行阻塞 offer(,1,TimeUnit.SECONDS)
        blockingQueue.offer("aaa");
//        try {
              // 满了会阻塞
//            blockingQueue.put("aaa");
//        } catch (InterruptedException e) {
//            System.out.println("put 阻塞了");
//        }
        System.out.println(blockingQueue);
    }
}
