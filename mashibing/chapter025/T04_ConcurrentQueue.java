package mashibing.chapter025;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @description: 用的最多，最重要，很多的异步方案都是使用队列进行解耦和
 * @author: lixueliang
 * @create: 2019-12-03 22:15
 **/
public class T04_ConcurrentQueue {
    public static void main(String[] args) {
        Queue<String> queue = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < 10; i++) {
            // 相当于 add 区别是如果是 array 实现的，容器满的时候再添加的话，add 直接抛出异常，offer 返回一个值，可以通过返回值判断是否加成功
            queue.offer("a" + i);
        }

        System.out.println(queue);
        System.out.println(queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.size());
        System.out.println(queue.peek());
        System.out.println(queue.size());
    }
}
