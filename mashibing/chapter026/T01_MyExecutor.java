package mashibing.chapter026;

import java.util.concurrent.Executor;

/**
 * @description: 在并发容器的基础上 java 又进行的封装
 * 要想了解 java 线程池的框架需要先从 Executor 这个接口开始，定义一个接口里面只有一个 execute 方法，可以执行一个具体的任务，
 * 可以是方法的调用。最顶层接口
 * @author: lixueliang
 * @create: 2019-12-04 09:34
 **/
public class T01_MyExecutor implements Executor {
    public static void main(String[] args) {
        new T01_MyExecutor().execute(()-> System.out.println("hello executor"));
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
