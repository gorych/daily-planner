package by.gsu.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtil {

    public static final DateTimeFormatter YYYY_MM_DD_HH_MM_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    public static final DateTimeFormatter HH_MM_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static BigDecimal convertToBigDecimal(LocalDateTime localDateTime) {
        return new BigDecimal(Long.parseLong(localDateTime.format(YYYY_MM_DD_HH_MM_FORMATTER)));
    }

}