package es.kairosds.pricepractice.domain.util;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import es.kairosds.pricepractice.application.exceptions.DateTimeParseException;

public class FormatUtil {
    
    private FormatUtil() {}

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");

    public static LocalDateTime dateParse(String date) {
        try {
            return LocalDateTime.parse(date, dtf);
        } catch (java.time.format.DateTimeParseException e) {
            throw new DateTimeParseException("Invalid date format: " + date);
        }
    }

    public static String toFormat(LocalDateTime date) {
        return date.format(dtf);
    }

    private static final NumberFormat numberFormatter = NumberFormat.getNumberInstance(new Locale("es", "ES"));

    static {
        numberFormatter.setMinimumFractionDigits(2);
        numberFormatter.setMaximumFractionDigits(2);
    }

    public static String formatDouble(Double num) {
        return numberFormatter.format(num);
    }
}
