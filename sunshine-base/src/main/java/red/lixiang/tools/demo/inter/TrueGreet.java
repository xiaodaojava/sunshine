package red.lixiang.tools.demo.inter;

import red.lixiang.tools.demo.Greet;
import red.lixiang.tools.demo.GreetCopy;

/**
 * @author lixiang
 * @date 2020/6/17
 **/
public class TrueGreet  implements Greet, GreetCopy {

    /**
     * 加油的接口定义
     * @return
     */
    @Override
    public void cheer() {
        System.out.println("加油加油");
    }
}
