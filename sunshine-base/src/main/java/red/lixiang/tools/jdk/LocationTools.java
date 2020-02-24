package red.lixiang.tools.jdk;

/**
 * @Author lixiang
 * @CreateTime 2019/9/1
 **/
public class LocationTools {

    private static double EARTH_RADIUS = 6378.137;


    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

    public static void main(String[] args) {
        double distance = getDistance(22.73525d, 114.28152d, 22.726726d, 114.226696d);
        System.out.println(distance);
    }
}
