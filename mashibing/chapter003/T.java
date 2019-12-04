package mashibing.chapter003;

public class T {
    private int count = 10;

    public synchronized void m(){
        // 等同于在方法的代码要执行时 synchronized(this)
        count --;
        System.out.println(Thread.currentThread().getName() + "count = " + count);
    }
}
