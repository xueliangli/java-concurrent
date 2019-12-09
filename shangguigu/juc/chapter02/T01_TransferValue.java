package shangguigu.juc.chapter02;

/**
 * @description: java 中是值传递还是引用传递？试通过程序证明
 * 主线程中的引用和方法调用时的引用要区分开
 * @author: lixueliang
 * @create: 2019-12-06 11:10
 **/
public class T01_TransferValue {
    static class T {
        private Integer age;
        private String name;

        public T(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private void change1(int age) {
        age = 30;
    }

    private void change2(T t) {
        t.setName("bbb");
    }

    private void change3(String name) {
        name = "bbb";
    }

    public static void main(String[] args) {
        T01_TransferValue transferValue = new T01_TransferValue();

        int age = 20;
        transferValue.change1(age);
        System.out.println("age : \t" + age);

        T t = new T("aaa");
        transferValue.change2(t);
        System.out.println("name : \t" + t.getName());

        String name = "aaa";
        transferValue.change3(name);
        System.out.println("name : \t" + name);
    }
}
