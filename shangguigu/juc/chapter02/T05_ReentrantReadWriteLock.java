package shangguigu.juc.chapter02;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description: 模拟一个缓存，验证读写分离，也就是共享锁的重要性
 * <p>
 * 一个缓存的底层是用 Map 实现的，主要有读，写，清空三个操作
 * <p>
 * 为什么加读写分离的锁？提高效率，多个读线程同时进行并不影响效率
 *
 * @author: lixueliang
 * @create: 2019-12-06 21:57
 **/
public class T05_ReentrantReadWriteLock {
    /**
     * @Description: 模拟缓存的资源类
     */
    static class T {
        Map<String, Object> map = new HashMap<>();
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        public void put(String key, Object value) {
            readWriteLock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t 正在写入：" + key);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.put(key, value);
                System.out.println(Thread.currentThread().getName() + "\t 写入完成！");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }

        public void get(String key) {
            readWriteLock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t 正在读取：");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Object value = map.get(key);
                System.out.println(Thread.currentThread().getName() + "\t 读取完成：" + value);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readWriteLock.readLock().unlock();
            }
        }

        public void clear() {
            map.clear();
        }
    }

    public static void main(String[] args) {
        T t = new T();

        for (int i = 1; i <= 5; i++) {
            int key = i;
            int value = i;
            new Thread(() -> {
                t.put("key" + key, "val" + value);
            }, "thread---" + i).start();
        }

        for (int i = 1; i <= 5; i++) {
            int key = i;
            new Thread(() -> {
                t.get("key" + key);
            }, "thread---" + i).start();
        }
    }

    /*
    未加读写锁之前的结果：
    thread---1	 正在写入：key1
    thread---2	 正在写入：key2
    thread---3	 正在写入：key3
    thread---4	 正在写入：key4
    thread---5	 正在写入：key5
    thread---1	 正在读取：
    thread---2	 正在读取：
    thread---3	 正在读取：
    thread---4	 正在读取：
    thread---5	 正在读取：
    thread---4	 读取完成：null
    thread---2	 读取完成：null
    thread---3	 读取完成：null
    thread---1	 读取完成：null
    thread---3	 写入完成！
    thread---2	 写入完成！
    thread---5	 写入完成！
    thread---1	 写入完成！
    thread---5	 读取完成：val5
    thread---4	 写入完成！
    */

}
