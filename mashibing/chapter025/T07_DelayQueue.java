package mashibing.chapter025;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @description: 无界队列，但是加进去的每一个任务得等一段时间后才能被拿出
 * 可以用来做定时执行任务
 * @author: lixueliang
 * @create: 2019-12-03 22:43
 **/
public class T07_DelayQueue {
    /**
     * @Description: 加入容器的元素必须实现 Delayed 接口，才能记录时间，查看 put 方法的文档
     */
    static BlockingQueue<MyTask> tasks = new DelayQueue<>();

    static Random r = new Random();

    static class MyTask implements Delayed {
        long runningTime;

        MyTask(long rt) {
            this.runningTime = rt;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(runningTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            // 等价于 return Long.compare(this.getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
            if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
                return -1;
            } else if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public String toString() {
            return "MyTask{" +
                    "runningTime=" + runningTime +
                    '}';
        }
    }

    public static void main(String[] args) {
        long now = System.currentTimeMillis();
        // 五个任务，不同开始时间执行
        MyTask t1 = new MyTask(now + 1000);
        MyTask t2 = new MyTask(now + 1500);
        MyTask t3 = new MyTask(now + 2000);
        MyTask t4 = new MyTask(now + 2500);
        MyTask t5 = new MyTask(now + 500);

        try {
            tasks.put(t1);
            tasks.put(t2);
            tasks.put(t3);
            tasks.put(t4);
            tasks.put(t5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(tasks);

        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(tasks.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
