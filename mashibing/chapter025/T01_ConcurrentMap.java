package mashibing.chapter025;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
 * @description: 查找跳表 map 的实现原理
 * 证明什么样的容器效率高，多线程并发下
 * ConcurrentSkipListMap 高并发并且需要排序的时候使用
 *
 * 并发不高的情况下也可以选用 Collections.synchronizedXXX 传入一个不加锁容器，返回一个加锁的容器
 * @author: lixueliang
 * @create: 2019-12-03 21:38
 **/
public class T01_ConcurrentMap {
    public static void main(String[] args) {
//        Map<String, String> map = new ConcurrentHashMap<>();
//        Map<String, String> map = new ConcurrentSkipListMap<>();

        Map<String, String> map = new Hashtable<>();
//        Map<String, String> map = new HashMap<>();
//        Map<String, String> map = new TreeMap<>();

    }
}
