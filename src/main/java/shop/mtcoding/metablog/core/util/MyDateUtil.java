package shop.mtcoding.metablog.core.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyDateUtil {
    public static String toStringFormat(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
