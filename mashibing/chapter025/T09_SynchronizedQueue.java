package mashibing.chapter025;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @description: 同步队列，没有容量的队列，容量为零，来的元素必须消费者消费掉
 * 一种特殊的 transferQueue ，在 transfer 中如果没有消费者还可以通过 offer put add 等向队列中添加元素，所以是有容量的
 * @author: lixueliang
 * @create: 2019-12-03 23:08
 **/
public class T09_SynchronizedQueue {
    public static void main(String[] args) {
        BlockingQueue<String> stringBlockingQueue = new SynchronousQueue<>();

        new Thread(()->{
            try {
                System.out.println(stringBlockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 阻塞等待消费者消费，是一种特殊的 transfer ，底层是通过 transfer 实现的
        try {
            stringBlockingQueue.put("aaa");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            stringBlockingQueue.add("bbb");
        } catch (Exception e) {
            System.out.println("不能使用 add");
        }
        System.out.println(stringBlockingQueue.size());
    }
}
