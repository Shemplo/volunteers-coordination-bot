package ru.itmo.nerc.vcb.db;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtils {
    
    public static final DateFormat dateFormat = new SimpleDateFormat ("dd.MM.yyyy HH:mm:ss");
    public static final DateFormat dateFormatNoSeconds = new SimpleDateFormat ("dd.MM.yyyy HH:mm");
    public static final DateFormat dateFormatShort = new SimpleDateFormat ("HH:mm:ss");
    
}
