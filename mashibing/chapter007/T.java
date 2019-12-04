package mashibing.chapter007;

/**
 * @description: 同步方法和非同步方法是否可以同时调用，在方法一执行过程之中，方法二可不可以运行，执行方法二的时候不需要看那个锁
 * @author: lixueliang
 * @create: 2019-11-29 22:52
 **/
public class T {
    private synchronized void m1(){
        System.out.println(Thread.currentThread().getName() + " m1 start ... ");
        try {
            Thread.sleep(10*1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m1 end ! ");
    }

    private void m2(){
        try{
            Thread.sleep(5*1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m2 ");
    }

    public static void main(String[] args) {
        T t = new T();
        new Thread(()->t.m1(),"t1").start();
        new Thread(()->t.m2(),"t2").start();

        //new Thread(t::m1,"t1").start();
        //new Thread(t::m2,"t2").start();

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.m1();
            }
        });
        */
    }
}
