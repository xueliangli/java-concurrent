package mashibing.chapter025;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description: 利用 Collections 工具类
 * @author: lixueliang
 * @create: 2019-12-03 22:13
 **/
public class T03_SynchronizedList {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        List<String> synStrings = Collections.synchronizedList(strings);
    }
}
