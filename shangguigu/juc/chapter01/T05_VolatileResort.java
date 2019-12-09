package shangguigu.juc.chapter01;

/**
 * @description: 验证 volatile 的禁止指令重排序作用
 * @author: lixueliang
 * @create: 2019-12-05 18:37
 **/
public class T05_VolatileResort {
    static class T{
        int a = 0;
        boolean flag = false;

        public void m1() {
            // 可能 flag 先执行而在 a 执行之前线程 2 就打断了，所以在线程 2 中得到的 a 的值可能两次不一样
            a = 1;
            flag = true;
        }

        public void m2() {
            if (flag) {
                a = a + 5;
                System.out.println("retValue: " + a);
            }
        }
    }

    public static void main(String[] args) {

    }
}
