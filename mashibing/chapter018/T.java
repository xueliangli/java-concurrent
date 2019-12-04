package mashibing.chapter018;

/**
 * @description: 不要以字符串常量作为锁定对象
 * 在下面的例子中，m1 和 m2 其实锁定的是同一个对象
 * 这种情况还会发生比较诡异的现象，比如用到了一个类库，在该类库中代码锁定了字符串“”"hello world"
 * 因为读不到源码，所以在自己的代码中锁定"hello world"，这时候可能会发生死锁阻塞
 * 因为自己的程序和类库中不经意间用了同一把锁
 * @author: lixueliang
 * @create: 2019-12-02 20:21
 **/
public class T {
    String s1 = "hello";
    String s2 = "hello";

    void m1() {
        synchronized (s1) {
        }
    }

    void m2() {
        synchronized (s2) {
        }
    }
}
