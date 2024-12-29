package red.lixiang.tools.jdk;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * 时间处理，都使用DateTimeTools
 * 里面使用的是 LocalDateTime 和 LocalDate
 * @CreateTime 2024/10/29
 **/
public class DateTimeTools {

    // 定义日期时间格式
    static DateTimeFormatter defaultLocalDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime stringToLocalDateTime(String date)
    {
        return LocalDateTime.parse(date, defaultLocalDateTimeFormatter);
    }


    public static String localDateTimeToString(LocalDateTime date)
    {
        return date.format(defaultLocalDateTimeFormatter);
    }

}
