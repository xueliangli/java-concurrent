package shangguigu.juc.chapter01;

/**
 * @description: 反汇编查看 i ++ 操作
 * @author: lixueliang
 * @create: 2019-12-05 15:22
 **/
public class T03_NumberPlusPlus {
    volatile int n = 0;

    public void add() {
        n++;
    }
}
