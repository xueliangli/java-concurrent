package shangguigu.juc.chapter04;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 使用阻塞队列实现生产者消费者模式
 * <p>
 * Volatile/CAS/atomicInteger/BlockingQueue/线程交互/原子引用
 * @author: lixueliang
 * @create: 2019-12-07 14:49
 **/
public class T05_ProducerAndConsumer3 {
    static class T {
        /**
         * @Description: 当 flag 为 true 时进行生产和消费，false 时退出生产和消费
         */
        private volatile boolean flag = true;
        private AtomicInteger atomicInteger = new AtomicInteger();
        BlockingQueue<String> blockingQueue = null;

        public T(BlockingQueue<String> blockingQueue) {
            this.blockingQueue = blockingQueue;
            System.out.println(blockingQueue.getClass().getName());
        }

        public void myProducer() {
            String data = null;
            boolean retVal;
            while (flag) {
                try {
                    data = atomicInteger.incrementAndGet() + "";
                    retVal = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
                    if (retVal) {
                        System.out.println(Thread.currentThread().getName() + "\t 插入队列成功");
                    } else {
                        System.out.println(Thread.currentThread().getName() + "\t 插入队列失败");
                    }
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "\t flag = false 停止生产与消费");
        }

        public void myConsumer() {
            String result = null;
            while (flag) {
                try {
                    result = blockingQueue.poll(2L, TimeUnit.SECONDS);
                    if (null == result || "".equalsIgnoreCase(result)) {
                        flag = false;
                        System.out.println(Thread.currentThread().getName() + "\t 超过两秒队列为空则退出");
                        System.out.println();
                        System.out.println();
                        return;
                    }
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + "\t 消费队列" + result + "成功");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stop(){
            this.flag = false;
        }
    }

    public static void main(String[] args) {
        T t = new T(new ArrayBlockingQueue<>(1));

        new Thread(() -> {
            System.out.println("... thread---producer start ... ");
            t.myProducer();
        }, "thread---producer").start();

        new Thread(() -> {
            System.out.println("... thread---consumer start ... ");
            t.myConsumer();
        }, "thread---consumer").start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "\t 时间到，停止生产消费");

        t.stop();
    }
}
