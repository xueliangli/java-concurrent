package mashibing.chapter026;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @description: 第一种形式的线程池：固定个数的线程池。线程池的一个小的应用，并行计算
 * 计算有多少个质数
 * @author: lixueliang
 * @create: 2019-12-04 10:56
 **/
public class T07_ParallelComputing {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        List<Integer> results = getPrime(1, 200000);
        long end = System.currentTimeMillis();
        System.out.println("1 到 20 万共有 " + results.size() + " 个质数");
        System.out.println("串行计算需要的时间：" + (end - start) + " 毫秒");
        System.out.println("---------------");

        final int cpuCoreNum = 4;
        ExecutorService service = Executors.newFixedThreadPool(cpuCoreNum);

        MyTask t1 = new MyTask(1, 80000);
        MyTask t2 = new MyTask(80001, 130000);
        MyTask t3 = new MyTask(130001, 170000);
        MyTask t4 = new MyTask(170001, 200000);

        Future<List<Integer>> f1 = service.submit(t1);
        Future<List<Integer>> f2 = service.submit(t2);
        Future<List<Integer>> f3 = service.submit(t3);
        Future<List<Integer>> f4 = service.submit(t4);

        start = System.currentTimeMillis();
        List<Integer> l1 = f1.get();
        List<Integer> l2 = f2.get();
        List<Integer> l3 = f3.get();
        List<Integer> l4 = f4.get();
        int res = l1.size() + l2.size() + l3.size() + l4.size();
        end = System.currentTimeMillis();
        System.out.println("1 到 20 万共有 " + res + " 个质数");
        System.out.println("串行计算需要的时间：" + (end - start) + " 毫秒");
    }

    static class MyTask implements Callable<List<Integer>> {
        int startPos;
        int endPos;

        public MyTask(int startPos, int endPos) {
            this.startPos = startPos;
            this.endPos = endPos;
        }

        @Override
        public List<Integer> call() throws Exception {
            List<Integer> r = getPrime(startPos, endPos);
            return r;
        }
    }

    static boolean isPrime(int num) {
        for (int i = 2; i <= num / 2; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    static List<Integer> getPrime(int start, int end) {
        List<Integer> results = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (isPrime(i)) {
                results.add(i);
            }
        }
        return results;
    }
}
