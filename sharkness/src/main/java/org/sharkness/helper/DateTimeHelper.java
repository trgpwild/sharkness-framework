package org.sharkness.helper;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.sharkness.logging.support.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.datetime.DateFormatter;

public class DateTimeHelper {

	private static Logger getLogger() {
		return LoggerFactory.getLogger();
	}
	
	public static String getDateFormatPattern(Locale locale) {
		DateFormat formatter = getDateFormat(locale);
		return ((SimpleDateFormat)formatter).toLocalizedPattern();
	}

	public static DateFormat getDateFormat(Locale locale) {
		if (locale == null) locale = Locale.getDefault();
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
		return formatter;
	}
	
	public static Date parseStringToDate(String dateStr) {
		try {
			return new DateFormatter().parse(dateStr, LocaleContextHolder.getLocale());
		} catch (Exception e) {
			getLogger().error("DateTimeHelper.parseStringToDate", e);
			return null;
		}
	}

	public static java.sql.Date parseStringToSqlDate(String dateStr) {
		return new java.sql.Date(parseStringToDate(dateStr).getTime());
	}

	public static Calendar parseStringToCalendar(String dateStr) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(parseStringToDate(dateStr));
		return calendar;
	}
	
	public static Timestamp parseStringToTimestamp(String dateStr) {
		return new Timestamp(parseStringToDate(dateStr).getTime());
	}

	public static Time parseStringToTime(String dateStr) {
		return new Time(parseStringToDate(dateStr).getTime());
	}

    public static Calendar lowCalendar(Calendar date) {
        return toOnlyDate(date);
    }
    
    public static Calendar highCalendar(Calendar date) {
        date = toOnlyDate(date);
        date.roll(Calendar.DATE, true);
        date.roll(Calendar.MILLISECOND, false);
        return date;
    }
    
    public static Date lowDate(Date date) {
    	return lowCalendar(parseDateToCalendar(date)).getTime();
    }

    public static Date highDate(Date date) {
    	return highCalendar(parseDateToCalendar(date)).getTime();
    }
    
    public static Calendar parseDateToCalendar(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	return cal;
    }
    
    public static Calendar toOnlyDate(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date;
    }

}