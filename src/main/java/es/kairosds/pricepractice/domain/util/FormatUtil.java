package es.kairosds.pricepractice.domain.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatUtil {
    
    private FormatUtil() {}

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");

    public static LocalDateTime dateParse(String date) {
        return LocalDateTime.parse(date, dtf);
    }

    public static String toFormat(LocalDateTime date) {
        return date.format(dtf);
    }

    public static String formatDouble(Double num) {
        return String.format("%.2f", num);
    }
}
