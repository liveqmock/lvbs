package com.daishumovie.utils;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class DateUtil {


	public static final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat y_m_d = new SimpleDateFormat("yyyy年MM月dd日");
	public static final SimpleDateFormat dd = new SimpleDateFormat("dd");
	public static final SimpleDateFormat ym = new SimpleDateFormat("yyyy年MM月");
	public static final SimpleDateFormat md = new SimpleDateFormat("MM月dd日");
	public static final SimpleDateFormat BASIC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat YMDHH = new SimpleDateFormat("yyyy-MM-dd HH");
	public static final String DATAFORMAT_SEPARATOR = "~";
	public static final String DATAFORMAT_YYYYMMDDHHmmss = "yyyy-MM-dd HH:mm:ss";
	public static final String DATAFORMAT_YYYYMMDD = "yyyy-MM-dd";
	public static final String DATAFORMAT_MMDD = "MM-dd";
	public static final String DATAFORMAT_YYYY_MM_DD = "yyyy.MM.dd";
	public static final String DATAFORMAT_YYYYMMDD_MINI = "yyyyMMdd";
	public static final String DATAFORMAT_YYYYMMDDHH = "yyyyMMddHH";
	public static final String DATAFORMAT_YYYYMMD_HH = "yyyy-MM-dd HH";
	public static final String DATAFORMAT_YYYYMM="yyyy-MM";
	public static final String DATAFORMAT_HH="HH";
	public static final String DATA_LAST_TIME_IN_DAY="23:59:59";
	public static final String TIMEFORMAT_MM_SS = "%s'%s''";
	/**
	 * 获取日期
	 * 说明: n=0为本小时, 1为明下一小时, -1为上一小时
	 */
	public static Calendar calendar = Calendar.getInstance();

	public static SimpleDateFormat yyMMddFormatter = new SimpleDateFormat(DATAFORMAT_YYYYMMDD);
	public static SimpleDateFormat yy_MM_ddFormatter = new SimpleDateFormat(DATAFORMAT_YYYY_MM_DD);
	public static SimpleDateFormat yyMMddHHFormatter = new SimpleDateFormat(DATAFORMAT_YYYYMMDDHH);
	public static SimpleDateFormat yyMMddhhmmssFormatter = new SimpleDateFormat(DATAFORMAT_YYYYMMDDHHmmss);
	public static SimpleDateFormat yyMMFormatter = new SimpleDateFormat(DATAFORMAT_YYYYMM);
	public static SimpleDateFormat HHFormatter = new SimpleDateFormat(DATAFORMAT_HH);
	public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat(DATAFORMAT_YYYYMMDD_MINI);
	public static SimpleDateFormat mmDDFormatter = new SimpleDateFormat(DATAFORMAT_MMDD);

//	public static Date addHours(Date date, int hour) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		// 添加天数
//		calendar.add(Calendar.HOUR_OF_DAY, hour);
//
//		return calendar.getTime();
//	}
	/**H
	 * 获取指点日期后的多少天
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 添加天数
		calendar.add(Calendar.DAY_OF_YEAR, days);

		return calendar.getTime();
	}


	public static int subDays(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}


	public static Date cacheDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		int year = calendar.get(Calendar.YEAR);
		int month = (int)calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DATE);

		try {
			Date startT = BASIC.parse(year+"-"+month+"-"+day +" 09:20:00 236");
			return startT;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static int getFristDayIsWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static int getDayOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return lastDay;
	}


	public static String getDDByDay(String date){
		return date.substring(8, 10);
	}

	public static String getCurrentTime(){
		return HHmm.format(new Date());
	}
	public static String getLastDayOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);
		int month = (int)calendar.get(Calendar.MONTH)+1;
		return year+"-"+(month < 10 ? ("0"+month) : month) +"-"+lastDay;
	}

	/**
	 * 比较两字符串大小
	 * @param before
	 * @param today
	 * @return
	 */
	public static boolean isCompareSize(String before,String today){
		int result= before.compareTo(today);

		return result < 0 ? true : false;
	}
	/**
	 * 获取指点日期后的月
	 * @param date
	 * @return
	 */
	public static Date addMonth(Date date, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 添加天数
		calendar.add(Calendar.MONTH, month);

		return calendar.getTime();
	}
	public static final Random random = new Random();

	public static Date getDateHour(int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, n);
		return calendar.getTime();
	}

	//取两个时间的随机时间
	public static Date GetRandomTime(Date minTime, Date maxTime)
	{
		long differ = maxTime.getTime() - minTime.getTime();
		long new_time = (long)(1+Math.random()*(differ-1+1));
		Date new_data = new Date(minTime.getTime()+new_time);
		return new_data;
	}

	/**
	 * 转换日期
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date formatDate(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		return null;
	}
	/////////////////////////////////add by handong start///////////////////////////////////////////////////////////////

	public static String formatHHSS(Integer seconds){
		if (seconds <= 0)
			return "";
		if (seconds > 0 && seconds <60)
			return seconds+"'";
		else if (seconds >= 60){
			int minute = seconds/60;
			int second = seconds - minute * 60;
			return minute+"'"+second+"''";
		}
		return "";
	}
	/**
	 * 获得当前年份
	 *
	 * @return
	 */
	public static int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 获得当前月份
	 *
	 * @return
	 */
	public static int getMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得今天在本年的第几天
	 *
	 * @return
	 */
	public static int getDayOfYear() {
		return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
	}



	/**
	 * 获得今天在本周的第几天
	 *
	 * @return
	 */
	public static int getDayOfWeek() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获得今天是这个月的第几周
	 *
	 * @return
	 */
	public static int getWeekOfMonth() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK_IN_MONTH);
	}
	public static int getWeekOfMonth(Date date) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
	}

	/**
	 * 得到两个日期间的间隔天数.
	 *
	 * @param start 开始时间 后被偏移至00:00:00
	 * @param end 结束时间 后被偏移至00:00:00
	 * @return 时间间隔
	 */
	public static int getPeriodOfDay(Date start, Date end) {
		if (start == null || end == null) {
			throw new NullPointerException("date is null");
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date startLocal = cal.getTime();
		cal.setTime(end);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date endLocal = cal.getTime();
		return (int) ((startLocal.getTime() - endLocal.getTime()) / 86400000);
	}

	public static int getPeriodOfHour(Date start, Date end) {
		if (start == null || end == null) {
			throw new NullPointerException("date is null");
		}
		return (int) ((end.getTime() - start.getTime()) / 3600000);
	}

	public static boolean thanGreater(Date start, Date end){
		if (start == null || end == null) {
			throw new NullPointerException("date is null");
		}
		return start.getTime() - end.getTime() > 0 ? true : false;
	}
	public static String getDistanceDay(Date start, Date end){
		if (start == null || end == null) {
			throw new NullPointerException("date is null");
		}
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = start.getTime() - end.getTime();
		if (diff < 0)
			return "0分钟";
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		return day + "天" + hour + "小时" + min + "分钟";
	}

	public static String getDistance(Date start, Date end){
		if (start == null || end == null) {
			throw new NullPointerException("date is null");
		}
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = start.getTime() - end.getTime();
		if (diff <= 0)
			return "刚刚";
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;


		if (day > 365) {
			return DateFormatUtils.format(end, "yyyy-MM-dd");
		}
		if (day > 0) {
			return DateFormatUtils.format(end, "MM-dd HH:mm");
		} else if (day == 0 && hour > 0) {
			return String.valueOf(hour+"小时前");
		}else{
			return String.valueOf(min+"分钟前");
		}

	}

	/**
	 * 针对活动列表页和详情页显示规则
	 *
	 *   剩余时间显示规则：大于等于24小时，显示n天，小于24小时，显示n小时，不足一小时显示n分钟
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getRestTime(Date start, Date end){
		if (start == null || end == null) {
			throw new NullPointerException("date is null");
		}
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = start.getTime() - end.getTime();
		if (diff < 0)
			return "0分钟";
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		if (day >=1) {
			return day + "天";
		} else if (day == 0 && hour >=1) {
			return hour  + "小时";
		}else if(hour == 0 && min >=1){
			return min + "分钟";
		}else{
			return 1 + "分钟";
		}
	}


	/**
	 * 获取当日的小时
	 *
	 * @return
	 */
	public static int getHourOfDay() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}
	/**
	 * 获取当日的小时
	 * @return
	 */
	public static int getHourOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	/**
	 * *
	 * <p/>
	 * 系统当前时间yyyy-MM-dd
	 */
	public static String getCurrentDateYYMMDD() {
		return yyMMddFormatter.format(Calendar.getInstance().getTime());
	}

	/**
	 * *
	 * <p/>
	 * 系统当前时间yyyyMMddHH
	 */
	public static String getCurrentDateYYMMDDHH() {
		return yyMMddHHFormatter.format(Calendar.getInstance().getTime());
	}

	/**
	 * 获取时间格式
	 * @param date
	 * @return
	 */
	public static String getCurrentDateYYMMDD(Date date){
		return yyMMddFormatter.format(date);
	}

	/**
	 * 获取月份
	 * @param date
	 * @return
	 */
	public static String getCurrentDateYYMM(Date date){
		return yyMMFormatter.format(date);
	}
	/**
	 * 获取年月
	 *
	 * @return
	 */
	public static String getCurrentDateYYMM() {
		return yyMMddFormatter.format(Calendar.getInstance().getTime()).substring(0, 7);
	}

	/**
	 * 获取本日在本月中的第几周
	 * @return
	 */
	public static int getDayOfWeekInMonth(Date date)throws Exception {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
	}
	/**
	 * 获取系统时间yyyy-MM-dd hh mm ss
	 *
	 * @return
	 */
	public static String getCurrentDateYYMMDDmmss() {
		return yyMMddhhmmssFormatter.format(Calendar.getInstance().getTime());
	}

	/**
	 * 获取系统时间yyyy-MM-dd hh mm ss
	 *
	 * @return
	 */
	public static String getCurrentDateYYMMDDmmss(Date date) {
		return yyMMddhhmmssFormatter.format(date.getTime());
	}

	/**
	 * 获得当前timestamp
	 *
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		return Timestamp.valueOf(getCurrentDateYYMMDDmmss());
	}

	/**
	 * 获取本周第一天
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek() {
		String strTemp = "";
		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 1);
		strTemp = c.get(1) + "-";
		if (c.get(2) + 1 < 10)
			strTemp += "0";
		strTemp = strTemp + (c.get(2) + 1) + "-";
		if (c.get(5) < 10)
			strTemp += "0";
		strTemp += c.get(5);
		return strTemp;
	}

	public static String getMondayOfThisWeek(Date date) {
       /* Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);*/
		String strTemp = "";
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 1);
		strTemp = c.get(1) + "-";
		if (c.get(2) + 1 < 10)
			strTemp += "0";
		strTemp = strTemp + (c.get(2) + 1) + "-";
		if (c.get(5) < 10)
			strTemp += "0";
		strTemp += c.get(5);
		return strTemp;
	}
	/**
	 * 获取本周最后一天
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek() {
		String strTemp = "";
		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 7);
		strTemp = c.get(1) + "-";
		if (c.get(2) + 1 < 10)
			strTemp += "0";
		strTemp = strTemp + (c.get(2) + 1) + "-";
		if (c.get(5) < 10)
			strTemp += "0";
		strTemp += c.get(5);
		return strTemp;
	}

	public static String getSundayOfThisWeek(Date date) {
		// calendar.setTime(date);
		String strTemp = "";
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 7);
		strTemp = c.get(1) + "-";
		if (c.get(2) + 1 < 10)
			strTemp += "0";
		strTemp = strTemp + (c.get(2) + 1) + "-";
		if (c.get(5) < 10)
			strTemp += "0";
		strTemp += c.get(5);
		return strTemp;
	}


	public static int getActualMaxDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static String addDays(String day, int amount) {
		return DateUtil.getCurrentDateYYMMDD(
				DateUtils.addDays(
						DateUtil.formatDate(day, DateUtil.DATAFORMAT_YYYYMMDD), amount));
	}

//	/**
//	 * @param date
//	 * @return
//	 */
//	public static String formatDate(long date) {
//		return DateFormatUtils.ISO_DATE_FORMAT.format(date);
//	}

	/**
	 * 日期操作
	 * 说明: n=0为当前日, 1为后一天, -1为前一天
	 *
	 * @param n
	 * @return
	 */
	public static final Date getDate(Date date, int n) {
		return DateUtils.addDays(date, n);
	}

	/**
	 * 日期操作
	 * 说明: n=0为当前月, 1为后一月, -1为前一月
	 *
	 * @param n
	 * @return
	 */
	public static final Date getMonth(Date date, int n) {
		return DateUtils.addMonths(date, n);
	}

	/**
	 * 日期操作
	 * 说明: n=0为当前周, 1为后一周, -1为前一周
	 *
	 * @param n
	 * @return
	 */
	public static final Date getWeek(Date date, int n) {
		return DateUtils.addWeeks(date, n);
	}

	/**
	 * 分钟操作
	 * @param date
	 * @param n
	 * @return
	 */
	public static final Date getMinute(Date date,int n){
		return DateUtils.addMinutes(date,n);
	}

	/**
	 * 秒操作
	 * @param date
	 * @param n
	 * @return
	 */
	public static final Date getSeconds(Date date,int n){
		return DateUtils.addSeconds(date,n);
	}
	/**
	 * 日期操作
	 * 说明: n=0为当前时, 1为后一时, -1为前一时
	 *
	 * @param n
	 * @return
	 */
	public static final Date getHour(Date date, int n) {
		return DateUtils.addHours(date, n);
	}

	public static int getCurrentDateHH(Date date){
		return Integer.valueOf(HHFormatter.format(date));
	}

	/**
	 * 根据月和周获得周日.
	 * @param month
	 * @param week
	 * @return
	 */
	public static String getSundayOfThisWeek(String month, int week) {
		Calendar c = Calendar.getInstance();
		c.setTime(formatDate(month, DATAFORMAT_YYYYMM));
		c.set(Calendar.WEEK_OF_MONTH, week);
		return getSundayOfThisWeek(c.getTime());
	}

	/**
	 * 根据月和周获得周日.
	 * @param month
	 * @param week
	 * @return
	 */
	public static String getMondayOfThisWeek(String month, int week) {
		Calendar c = Calendar.getInstance();
		c.setTime(formatDate(month, DATAFORMAT_YYYYMM));
		c.set(Calendar.WEEK_OF_MONTH, week);
		return getMondayOfThisWeek(c.getTime());
	}

	/**
	 * 格式化日期数字(小于10的补0)
	 * </p>
	 * 例如 1,返回01
	 * @param date
	 * @return
	 */
	public static String formatDate(int date){
		if(date <10 && date >=0){
			return "0"+date;
		} else {
			return String.valueOf(date);
		}
	}

	/**
	 * 获得上个星期的dateShow.
	 * @param day
	 * @return
	 */
	public static String getDateShowOfLastWeek(String day) {
		String dayOfLastWeek = DateUtil.getCurrentDateYYMMDD(
				DateUtils.addWeeks(DateUtil.formatDate(day, DateUtil.DATAFORMAT_YYYYMMDD), -1));
		String monday = DateUtil.getMondayOfThisWeek(DateUtil.formatDate(dayOfLastWeek, DateUtil.DATAFORMAT_YYYYMMDD));
		String sunday = DateUtil.getSundayOfThisWeek(DateUtil.formatDate(dayOfLastWeek, DateUtil.DATAFORMAT_YYYYMMDD));
		return monday + DateUtil.DATAFORMAT_SEPARATOR + sunday;
	}

	/**
	 * 获得自然周.
	 * 一周为：星期一到星期日.
	 * 每月第一周为：这个月的第一个星期一所在的周.
	 * @param date
	 * @return
	 */
	public static DateBean getActualWeekOfMonth(Date date) {
		DateBean dateBean = newDateBean();
		String day = getCurrentDateYYMMDD(date);
		String firstMonday = getFirstMondayOfMonth(day);
		if (date.before(formatDate(firstMonday, DATAFORMAT_YYYYMMDD))) {
			String mondayOfThisWeek = getMondayOfThisWeek(
					formatDate(parseFirstDayOfMonth(day), DATAFORMAT_YYYYMMDD));
			if (!parseMonth(mondayOfThisWeek).equals(parseMonth(day))) {
				day = mondayOfThisWeek;
				firstMonday = getFirstMondayOfMonth(mondayOfThisWeek);
			}
		}
		dateBean.setMonth(parseMonth(day));
		dateBean.setWeek(weekOfMonth(day, firstMonday));
		return dateBean;
	}

	/**
	 * 获得一个月的星期一.
	 * @param day
	 * @return
	 */
	public static String getFirstMondayOfMonth(String day) {
		Calendar c = Calendar.getInstance();
		String firstDay = parseFirstDayOfMonth(day);
		c.setTime(formatDate(firstDay, DATAFORMAT_YYYYMMDD));
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		if (dayOfWeek == 1) {
			return firstDay;
		}
		c.add(Calendar.DATE, 8 - dayOfWeek);
		return getCurrentDateYYMMDD(c.getTime());
	}

	public static String getActualMondayOfMonth(String month, int week) {
		String firstMonday = getFirstMondayOfMonth(month + "-01");
		int total = getActualMaxDayOfMonth(formatDate(month, DATAFORMAT_YYYYMM));
		int day = (week - 1) * 7 + Integer.valueOf(firstMonday.substring(8, firstMonday.length()));
		if (total < day) {
			throw new IllegalArgumentException("week is illegal");
		}
		return month + "-" + formatDate(day);
	}

	private static String parseFirstDayOfMonth(String day) {
		return parseMonth(day) + "-01";
	}

	private static String parseMonth(String day) {
		return day.substring(0, 7);
	}

	private static int weekOfMonth(String date, String firstMonday) {
		int dayOfLastWeek = Integer.valueOf(firstMonday.substring(8, firstMonday.length())) - 1;
		int day = Integer.valueOf(date.substring(8, date.length())) - dayOfLastWeek;
		return ((day % 7 == 0) ? (day / 7) : (day / 7 + 1));
	}

	public static DateBean newDateBean() {
		return new DateBean();
	}

	public static String getDateShow(Date date) {
		String monday = getMondayOfThisWeek(date);
		String sunday = getSundayOfThisWeek(date);
		return monday + DATAFORMAT_SEPARATOR + sunday;
	}

	public static String getDurationStr(Integer duration) {
		if (duration == null || duration.intValue() == 0) {
			return "0''";
		}
		int minute = (int) (duration / 60);
		int second = duration % 60;
		return String.format(TIMEFORMAT_MM_SS, minute, second);
	}



	/**
	 * redis中将字符转成汉字
	 * @param unicode
	 */
	public static String redisUnicode2Chinese(String unicode)throws Exception{
		unicode=unicode.replaceAll("\\\\x","%");
		//String name= URLEncoder.encode(xw, "utf-8");
		//System.out.println("汉字16进制转换:"+name);
		//System.out.println(unicode);
		String dename= URLDecoder.decode(unicode, "utf-8");
		return dename;
	}
	public static String toUnicode(String s) {
		String as[] = new String[s.length()];
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			as[i] = Integer.toHexString(s.charAt(i) & 0xffff);
			s1 = s1 + "\\u" + as[i];
		}
		return s1;
	}
	// 二进制转十进制
	public  static int convertAlgorism(String nums) {
		char[] cars=nums.toCharArray();
		int result = 0;
		int num = 0;
		for (int i = cars.length - 1; 0 <= i; i--) {
			int temp = 2;
			if (num == 0) {
				temp = 1;
			} else if (num == 1) {
				temp = 2;
			} else {
				for (int j = 1; j < num; j++) {
					temp = temp * 2;
				}
			}
			int sum = Integer.parseInt(String.valueOf(cars[i]));
			result = result + (sum * temp);
			num++;
		}
		return result;
	}

	public static Date todayZeroClock(String today) {

		today = StringUtil.trim(today);
		if (today.length() == 0) {
			return null;
		}
		try {
			return BASIC.parse(today + " 00:00:00");
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date tonight(String today) {

		today = StringUtil.trim(today);
		if (today.length() == 0) {
			return null;
		}
		try {
			return BASIC.parse(today + " 23:59:59");
		} catch (ParseException e) {
			return null;
		}
	}

	public static Calendar getUtcCalendar()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(cal.getTimeInMillis() - TimeZone.getDefault().getRawOffset());
		return cal;
	}

	public static Calendar getUtcCalendar(long nowTime)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(nowTime - TimeZone.getDefault().getRawOffset());
		return cal;
	}

	public static class DateBean {

		private String month;
		private int week;

		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

		public int getWeek() {
			return week;
		}

		public void setWeek(int week) {
			this.week = week;
		}
	}

	public static int formatSecond(String old){

		String[] arr = old.split("[.]");
		if (arr.length >1){
			return Integer.valueOf(arr[0]).intValue() * 60 + Integer.valueOf(arr[1]).intValue();
		}
		return 0;
	}
	public static String specifiedFormatSecond(String old){

		String[] arr = old.split("[.]");
		if (arr.length >1){
			if (arr[0] != "0"){
				return arr[0]+"'" + arr[1]+"''";
			}else{
				return arr[1]+"''";
			}
		}
		return null;
	}

	/**
	 * 把00:00:48.09 转化成毫秒
	 * @param original
	 * @return
	 */
	public static Integer conversionMillisecond(String original){
		if (null == original || "".equals(original) || original.length() == 0) return 0;

		String[] min_second_arr = original.split("[.]");
		Integer hour_millisecond = 0;
		Integer minute_millisecond = 0;
		Integer second_millisecond = 0;
		Integer millisecond = 0;
		if(min_second_arr != null && min_second_arr.length > 0){
			if (min_second_arr[0] != null){
				String[] arr = min_second_arr[0].split("[:]");
				if (!arr[0].equals("00")){
					hour_millisecond = Integer.valueOf(arr[0]) * 60 * 60 * 1000;
				}
				if (!arr[1].equals("00")){
					minute_millisecond = Integer.valueOf(arr[1]) * 60 * 1000;
				}
				if (!arr[2].equals("00")){
					minute_millisecond = Integer.valueOf(arr[2]) * 1000;
				}
			}
			if (!min_second_arr[1].equals("00"))
				millisecond = Integer.valueOf(min_second_arr[1]);

		}
		return hour_millisecond+minute_millisecond+second_millisecond+millisecond;
	}

	private static long[] time_slot = new long[]{
			getTime(Calendar.MINUTE),
			getTime(Calendar.HOUR),
			getTime(Calendar.DATE),
			getTime(Calendar.WEEK_OF_YEAR),
			getTime(Calendar.MONTH),
			getTime(Calendar.YEAR)
	};

	/**
	 * 距离当前时间多久
	 * 秒,分钟,时 : xx 前
	 * 大于一天 : yyyy-MM-dd HH:mm:ss
	 * 大于年 : yyyy-MM-dd
	 */
	public static String timeAgo(Date crtTm){

		long time = (new Date().getTime() - crtTm.getTime())/1000;
		String showTm;
		//一分钟内

		if(time <= time_slot[0]){
			showTm = time+"秒";
		}
		//1~60分钟

		else if(time > time_slot[0] && time <= time_slot[1]){
			showTm = (time/time_slot[0])+"分钟";
		}
		//一小时 ~ 一天

		else if(time > time_slot[1] && time <= time_slot[2]){
			showTm = (time/time_slot[1])+"小时";
		}
		//一天 ~ 一周

		else if(time > time_slot[2] && time <= time_slot[5]){
			showTm = BASIC.format(crtTm);
		}
		//一年 ~ ...

		else {
			showTm = ymd.format(crtTm);
		}
		return showTm.equalsIgnoreCase("0秒前") ? "刚刚" : (showTm + "前");
	}

	/**
	 *
	 * @method 获取时间段
	 * @param type
	 * @return
	 */
	private static long getTime(int type){
		Calendar c = Calendar.getInstance();
		c.add(type,-1);
		return (new Date().getTime()-c.getTimeInMillis())/1000;
	}

	public static String getDistanceCustom(Date start, Date end, int limit, String datePattern1, String datePattern2) {
		if (start == null || end == null) {
			throw new NullPointerException("date is null");
		}
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = start.getTime() - end.getTime();
		if (diff <= 0)
			return "刚刚";
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;

		if (day > limit) {
			return DateFormatUtils.format(end, datePattern1);
		}
		if (day > 0) {
			if (datePattern2 == null) {
				return String.valueOf(day + "天前");
			}
			return DateFormatUtils.format(end, datePattern2);
		} else if (day == 0 && hour > 0) {
			return String.valueOf(hour + "小时前");
		} else {
			return String.valueOf(min + "分钟前");
		}
	}
}
