package red.lixiang.tools.jdk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * 一些和时间相关的工具类
 * @Author https://www.javastudy.cloud
 * 公众号: 程序员学习大本营
 * @CreateTime 2019/10/5
 **/
public class DateTools {

    /**
     * 获取上个月开始的时间
     * 2019-10-01 00:00:00
     * @return
     */
    public static Date lastMonthBegin(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        clearTimeForCalendar(calendar);
        Date time = calendar.getTime();
        return time;
    }

    /**
     * 获取 1970-01-01 00:00:00 这个时间点
     * @return
     */
    public static Date earlyDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,1970);
        calendar.set(Calendar.MONTH,1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        clearTimeForCalendar(calendar);
        Date time = calendar.getTime();
        return time;
    }

    /**
     * 获取上个月结束的时间点
     * 2019-10-31 23:59:59
     * @return
     */
    public static Date lastMonthEnd(){
        Calendar calendar = Calendar.getInstance();
        //先获取到这个月的第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        clearTimeForCalendar(calendar);
        //然后秒数减1就是上个月最后一刻了
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTime();
    }

    /**
     * 获取这个月开始的时间
     * 2019-11-01 00:00:00
     * @return
     */
    public static Date thisMonthBegin(){
        Calendar calendar = Calendar.getInstance();
        //先获取到这个月的第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        clearTimeForCalendar(calendar);
        return calendar.getTime();
    }

    /**
     * 获取这个月结束的时间
     * 2019-11-30 23:59:59
     * @return
     */
    public static Date thisMonthEnd(){
        Calendar calendar = Calendar.getInstance();
        //先获取到这个月的第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH,1);
        clearTimeForCalendar(calendar);
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTime();
    }

    /**
     * 获取今天开始的时间
     * 2019-11-02 00:00:00
     * @return
     */
    public static Date thisDayBegin(){
        Calendar calendar = Calendar.getInstance();
        clearTimeForCalendar(calendar);
        return calendar.getTime();
    }

    public static String thisDayBeginStr(){
        Date date = thisDayBegin();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 获取今天结束的时间
     * 2019-11-02 23:59:59
     * @return
     */
    public static Date thisDayEnd(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,1);
        clearTimeForCalendar(calendar);
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTime();
    }

    /**
     * 获取今天结束的时间
     * 2019-11-02 23:59:59
     * @return
     */
    public static String thisDayEndStr(){
        Date date = thisDayEnd();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 获取这周开始的时间
     * @return
     */
    public static Date thisWeekBegin(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,1);
        clearTimeForCalendar(calendar);
        return calendar.getTime();
    }
    public static String thisWeekBeginStr(){
        Date date = thisWeekBegin();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


    /**
     * 把date里面的时分秒给清空
     * @param date
     * @return
     */
    public static Date clearTimeForDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        clearTimeForCalendar(calendar);
        return calendar.getTime();
    }

    public static Long getSecondBetween(Date d1, Date d2){
        return Math.abs((d1.getTime()-d2.getTime())/1000);
    }

    public static Long getSecondBetween(long t1, long t2){
        return Math.abs((t1-t2)/1000);
    }

    public static String clearTimeForString(String date){
        return date.split(" ")[0]+" 00:00:00";
    }

    public static String removeTimeForString(String date){
        return date.split(" ")[0];
    }

    public static String lastTimeForString(String date){
        return date.split(" ")[0]+" 23:59:59";
    }

    /**
     * 把Calendar时间里面的时分秒给清空
     * @param calendar
     */
    public static void clearTimeForCalendar(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
    }

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long secondBetween = getSecondBetween(l, System.currentTimeMillis());
        System.out.println(secondBetween);


    }

}
