package by.gsu.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtil {

    public static final DateTimeFormatter YYYY_MM_DD_HH_MM_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    public static final DateTimeFormatter DD_MM_YYYY_HH_MM_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public static final DateTimeFormatter DD_MM_HH_MM_FORMATTER = DateTimeFormatter.ofPattern("dd.MM HH:mm");

    public static BigDecimal convertToBigDecimal(LocalDateTime localDateTime) {
        return new BigDecimal(Long.parseLong(localDateTime.format(YYYY_MM_DD_HH_MM_FORMATTER)));
    }

}
