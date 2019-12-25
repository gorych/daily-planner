package by.gsu.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtil {

    public static final DateTimeFormatter YYYY_MM_DD_HH_MM_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    public static BigDecimal convertToBigDecimal(LocalDateTime localDateTime) {
        return new BigDecimal(Integer.parseInt(localDateTime.format(YYYY_MM_DD_HH_MM_FORMATTER)));
    }

}
