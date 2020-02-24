package red.lixiang.tools.jdk;

/**
 *  这个类,位运算的展示
 * @author lixiang
 * @date 2020/2/12
 **/
public class BinaryTools {
    public static void main(String[] args) {
        // 如果拿到的是String , 可以直接使用charAt来获取
        String s  = "1101";
        // 从左往右第二位
        System.out.println(s.charAt(1));

        // 如果拿到的是二进制, 可以通过 右移后和 1 进行与操作 &
        int i = 0b1101;
        // 从左往右第三位
        int r = (i>>(3-1)) & 1;
        System.out.println(Integer.toBinaryString(r));
    }
}
