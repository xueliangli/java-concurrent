package mashibing.chapter026;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @description: 与线程池无关，作为扩展内容
 * 面试遇到问题多往多线程里扯一扯，说不定就会多看自己一眼
 * @author: lixueliang
 * @create: 2019-12-04 14:41
 **/
public class T14_ParallelStreamAPI {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 10000; i++) {
            nums.add(1000000 + r.nextInt(1000000));
        }
        System.out.println("数组和为：" + nums);

        long start = System.currentTimeMillis();
        nums.forEach(n -> isPrime(n));
        long end = System.currentTimeMillis();
        System.out.println("使用普通方法计算所用时间：" + (end - start) + " 毫秒");

        // 使用 parallelStream 计算
        start = System.currentTimeMillis();
        nums.parallelStream().forEach(T14_ParallelStreamAPI::isPrime);
        end = System.currentTimeMillis();

        System.out.println("使用并行流计算所用时间：" + (end - start) + " 毫秒");
    }

    static boolean isPrime(int num) {
        for (int i = 2; i <= num / 2; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
