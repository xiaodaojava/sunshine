package red.lixiang.tools.demo.inter;

import red.lixiang.tools.demo.Greet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author lixiang
 * @date 2020/6/17
 **/
public class TestMain {

    public static void main(String[] args) {
        Greet greet = new TrueGreet();
        greet.cheer();
    }
}
