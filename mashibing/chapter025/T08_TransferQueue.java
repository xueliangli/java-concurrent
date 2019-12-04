package mashibing.chapter025;

import java.util.concurrent.LinkedTransferQueue;

/**
 * @description: 生产者在将元素放入容器时先去看时候存在消费者，如果存在则不放入队列直接给消费者
 * 用在更高的并发情况下，找不到消费者的时候会阻塞
 * @author: lixueliang
 * @create: 2019-12-03 22:59
 **/
public class T08_TransferQueue {
    public static void main(String[] args) {
        LinkedTransferQueue<String> stringLinkedTransferQueue = new LinkedTransferQueue<>();
        // 先执行这个县城再 transfer 没有问题，反过来会阻塞
        new Thread(() -> {
            try {
                System.out.println(stringLinkedTransferQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            // 如果不用 transfer 而用 put add 就不会阻塞，消息队列 netty 等用的比较多
            stringLinkedTransferQueue.transfer("aaa");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        new Thread(() -> {
//            try {
//                System.out.println(stringLinkedTransferQueue.take());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }
}
