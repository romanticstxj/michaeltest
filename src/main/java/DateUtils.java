

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {


	public static String FORMATE_YYYY_MM_DD = "yyyy-MM-dd";
	
	public static Date getYmd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR,0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/*
	 * 根据pattern转换date
	 */
	public static Date getFormatDateByPattern(String pattern, String dateStr) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		simpleDateFormat.setLenient(false);
		return simpleDateFormat.parse(dateStr);
	}

	/**
	 * 两个日期多少�?
	 * 
	 * @param date1
	 *            Date1
	 * @param date2
	 *            Date2
	 * @return long
	 */
	public static long getDateSecondSubtract(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime()) / (1000);
	}

	/**
	 * 两个日期相差几天
	 * 
	 * @param date1
	 *            Date1
	 * @param date2
	 *            Date2
	 * @return long
	 */
	public static long getDateSubtract(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
	}
	
	/**
	 * 根据pattern和date转化格式化的字符�?
	 * @param pattern 格式
	 * @param date 日期
	 * @return String 日期
	 */
	public static String getFormatStringByPattern(String pattern, Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		simpleDateFormat.setLenient(false);
		return simpleDateFormat.format(date);
	}
	
	public static Date getFormatDateByPattern(String pattern, Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		simpleDateFormat.setLenient(false);
		return date;
	}
	
	public static Date getCurrentDate(){
		return new Date();
	}
	
	public static Date getFormattedCurrentDate(){
		Date date = getFormatDateByPattern("yyyyMMdd", new Date());
		return date;
	}
}
