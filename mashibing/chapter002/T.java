package mashibing.chapter002;


public class T {
    private int count = 10;

    public void m(){
        // 上面一种的简便写法
        // 谁要 new 我出来就先锁定这个对象，要执行代码的时候锁定当前对象
        synchronized (this){
            count --;
            System.out.println(Thread.currentThread().getName() + "count = " + count);
        }
    }
}
