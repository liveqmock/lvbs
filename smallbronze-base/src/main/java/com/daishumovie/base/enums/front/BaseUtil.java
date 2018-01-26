package com.daishumovie.base.enums.front;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public enum BaseUtil {

	instance;

	public static DecimalFormat df = new DecimalFormat("0.0w");
	
	public static DecimalFormat df1 = new DecimalFormat("0w");
	
	public static DecimalFormat df2 = new DecimalFormat("0.0");
	
	public static SimpleDateFormat yyMMddFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public static SimpleDateFormat mmDDFormatter = new SimpleDateFormat("MM-dd");
	
	

	public String getDuration(Float duration) {
		if (duration != null) {
			String durationStr = "";
			int mi = duration.intValue() / 60;
			if (mi < 10) {
				durationStr += "0";
			}
			durationStr += mi + ":";

			int se = duration.intValue() % 60;
			if (se < 10) {
				durationStr += "0";
			}
			durationStr += se;
			return durationStr;
		}
		return null;
	}

	public String formatNum(Integer num, boolean returnZero) {
		if (num == null || num.intValue() == 0) {
			if (returnZero) {
				return "0";
			} else {
				return null;
			}
		}
		if (num >= 10000 && num < 1000000) {
			return df.format(num / 10000.0);
		} else if (num >= 1000000) {
			return df1.format(num / 10000.0);
		}
		return num.toString();
	}
	
	public String numberFormat(Integer n) {
		df.setRoundingMode(RoundingMode.FLOOR);
		if (n == null || n.intValue() == 0) {
			return "0";
		}
		if (n >= 10000) {
			return df2.format(n / 10000d) + "W";
		}
		if (n >= 1000) {
			return df2.format(n / 1000d) + "K";
		}
		return n + "";
	}
	
	public int getPeriodOfDay(Date start, Date end) {
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
	
	public String formatTime(Date date) {
		int diff = getPeriodOfDay(new Date(), date);
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		cal.setTime(date);
		if (diff == 0) {
			return "今日更新";
		} else if (diff == 1) {
			return "昨日更新";
		} else if (cal.get(Calendar.YEAR) == year) {
			return mmDDFormatter.format(date) + " 更新";
		} else {
			return yyMMddFormatter.format(date) + " 更新";
		}
	}
}
