package red.lixiang.tools.jdk;

import java.util.UUID;

/**
 * 一些随机字符串,随机数的工具类
 *
 * @Author lixiang
 * @CreateTime 2019/9/17
 **/
public class RandomTools {
    /**
     * 返回指定范围内的随机数字
     *
     * @param n
     * @return
     */
    private static int getRandomInt(int n) {
        return (int) (Math.random() * n);
    }

    /**
     * 返回大写字母
     *
     * @return
     */
    private static String getCapitalChar() {
        return (char) (getRandomInt(100) % 26 + 65) + "";
    }

    /**
     * 返回大写字母
     *
     * @return
     */
    private static String getLowerChar() {
        return (char) (getRandomInt(100) % 26 + 97) + "";
    }

    /**
     * 生成随机字符串，不区分大小写，不包含数字
     *
     * @param length 字符串长度
     * @return
     */
    public static String getComCharStr(int length) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (getRandomInt(10) % 2 == 0) {
                buffer.append(getLowerChar());
            } else {
                buffer.append(getCapitalChar());
            }
        }
        return buffer.toString();
    }

    /**
     * 生成只含大写字母的字符串
     *
     * @param length 字符串长度
     */
    public static String getCapitalStr(int length) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            buffer.append(getCapitalChar());
        }
        return buffer.toString();
    }

    /**
     * 获取只含数字的字符串
     *
     * @param length 字符串长度
     * @return
     */
    public static String getNumStr(int length) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            buffer.append(getRandomInt(100) % 10);
        }
        return buffer.toString();
    }

    /**
     * 生成token
     * 采用uuid，生成32位的随机串
     */
    public static String getTokenStr() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("\\-", "");
    }
}
