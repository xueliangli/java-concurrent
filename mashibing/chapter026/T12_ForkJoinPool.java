package mashibing.chapter026;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * @description: 第六种线程池：将大任务切分处理后将结果合并，Fork 和 Join 概念在操作系统的很多地方都有使用
 * 排序的时候可以考虑到多线程的递归排序
 * @author: lixueliang
 * @create: 2019-12-04 13:52
 **/
public class T12_ForkJoinPool {
    static int[] nums = new int[1000000];
    /**
     * @Description: 每个任务计算不超过 50000 个
     */
    static final int MAX_NUM = 50000;
    static Random r = new Random();

    static {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }

        // 利用 stream 方式对数组中所有元素进行求和
        System.out.println("对数器得到的结果：" + Arrays.stream(nums).sum());
    }

//    /**
//     * @Description: 不直接继承 ForkJoinTask 是因为比较麻烦，一般直接继承（递归的切分） RecursiveAction（无返回值）或者 RecursiveTask（有返回值）
//     * 下面只能打印不能返回值，所以在下面的演示中只是做了打印没有组合，就是让线程池帮我们做递归
//     */
//    static class AddTask extends RecursiveAction {
//        // 数组下标的起始位置与结束位置
//        int start;
//        int end;
//
//        AddTask(int start, int end) {
//            this.start = start;
//            this.end = end;
//        }
//
//        @Override
//        protected void compute() {
//            if ((end - start) <= MAX_NUM) {
//                long sum = 0L;
//                for (int i = start; i < end; i++) {
//                    sum += nums[i];
//                }
//                System.out.println("from: " + start + " to: " + end + " = " + sum);
//            } else {
//                int middle = start + (end - start) / 2;
//                AddTask subTask1 = new AddTask(start, middle);
//                AddTask subTask2 = new AddTask(middle, end);
//                // 启动两个子任务
//                subTask1.fork();
//                subTask2.fork();
//            }
//        }
//    }

    static class AddTask extends RecursiveTask<Long> {
        int start;
        int end;

        public AddTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if ((end - start) <= MAX_NUM) {
                long sum = 0L;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                return sum;
            }
            int middle = start + (end - start) / 2;
            AddTask subTask1 = new AddTask(start, middle);
            AddTask subTask2 = new AddTask(middle, end);
            subTask1.fork();
            subTask2.fork();

            return subTask1.join() + subTask2.join();
        }
    }

    public static void main(String[] args) {
        ForkJoinPool fjp = new ForkJoinPool();
        AddTask task = new AddTask(0, nums.length);
        fjp.execute(task);

        long res = task.join();
        System.out.println("多线程计算得到的结果：" + res);

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
