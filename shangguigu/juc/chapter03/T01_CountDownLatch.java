package shangguigu.juc.chapter03;

import java.util.concurrent.CountDownLatch;

/**
 * @description: 通过 CountDownLatch 模拟战国七雄中其他六国被秦国所灭最后统一的过程
 * @author: lixueliang
 * @create: 2019-12-06 22:45
 **/
public class T01_CountDownLatch {
    /**
     * @Description: 需要用汉字来代替之前遍历用到的数字，使用枚举类来作为临时的数据库，方便高效
     */
    enum CountryEnum {
        /**
         * @Description: 战国被灭六雄
         */
        ONE(1, "齐"), TWO(2, "楚"), THREE(3, "燕"), FOUR(4, "赵"), FIVE(5, "魏"), SIX(6, "韩");

        private Integer retCode;
        private String retMessage;

        CountryEnum(Integer retCode, String retMessage) {
            this.retCode = retCode;
            this.retMessage = retMessage;
        }

        /**
         * @Description: 通过这样可以解耦合，在 main 中不用写很多的 if 判断
         */
        public static CountryEnum getCountryByIndex(Integer i){
            CountryEnum[] countryEnums = CountryEnum.values();
            for (CountryEnum c : countryEnums) {
                if(c.getRetCode().equals(i)){
                    return c;
                }
            }
            return null;
        }

        public Integer getRetCode() {
            return retCode;
        }

        public String getRetMessage() {
            return retMessage;
        }
    }

    public static void main(String[] args) {
        CountDownLatch count = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "国被灭了 ... ");
                count.countDown();
            }, CountryEnum.getCountryByIndex(i).getRetMessage()).start();
        }

        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "\t 秦国统一全国");
    }
    /*
    不加 CountDownLatch 的结果：

    楚国被灭了 ...
    齐国被灭了 ...
    main	 秦国统一全国
    燕国被灭了 ...
    韩国被灭了 ...
    赵国被灭了 ...
    魏国被灭了 ...
    */
}
