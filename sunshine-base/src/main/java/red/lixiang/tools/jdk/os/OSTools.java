package red.lixiang.tools.jdk.os;

/**
 * @author lixiang
 * @date 2020/2/26
 **/
public class OSTools {

    public static boolean isWin(){
        String osName = System.getProperty("os.name");
        return osName.toLowerCase().contains("windows");
    }

    public static boolean isMac(){
        String osName = System.getProperty("os.name");
        return osName.toLowerCase().contains("mac");
    }

    /**
     * /Users/lixiang
     * @return
     */
    public static String userHomePath(){
        String userHome  = System.getProperty("user.home");
        return userHome;
    }
    public static String fileSeparator(){
        String userHome  = System.getProperty("file.separator");
        return userHome;
    }

    public static void main(String[] args) {
        String s = userHomePath();
        System.out.println(s);
    }


}
