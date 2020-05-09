package red.lixiang.tools.jdk;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author lixiang
 * @date 2020/5/8
 **/
public class NetTools {

    public static boolean ping(String ip){
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(ip);
            boolean reachable = inetAddress.isReachable(1000);
            if(reachable){
                ToolsLogger.out(inetAddress);
            }else {
                ToolsLogger.info("ip {},reach false",ip);
            }
            return reachable;

        } catch (IOException e) {
           return false;
        }
    }

    public static List<String>  potentialIpList(String ip){
        //先把ip截开
        String partIp = ip.substring(0,ip.lastIndexOf(".")+1);
        return IntStream.range(1, 255).boxed().map(x -> partIp + x).collect(Collectors.toList());
    }

    /**
     * 获取本机的ip地址列表
     * @return
     */
    public static List<String> getLocalIpList(){
        List<InetAddress> localHost = getLocalHost();
       return localHost.stream().map(InetAddress::getHostAddress).collect(Collectors.toList());
    }

    /**
     *     获取本机的网络地址
     * @return
     */
    private static List<InetAddress> getLocalHost() {
        List<InetAddress> resultList = new ArrayList<>();
        try {
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (inetAddr.isLoopbackAddress() || inetAddr instanceof Inet6Address) {
                        // 排除loopback类型地址
                        continue;
                    }
                    resultList.add(inetAddr);

                }
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if(resultList.isEmpty()){
                resultList.add(jdkSuppliedAddress);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static List<String> reachableIpList(String ip){
        List<String> potentialIpList = potentialIpList(ip);
        return potentialIpList.stream().filter(NetTools::ping).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> list = reachableIpList("192.168.88.108");
        System.out.println(list);

    }
}
