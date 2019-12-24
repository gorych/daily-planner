package by.gsu.util;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtil {

   public static final DateTimeFormatter YYYY_MM_DD_HH_MM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

}
