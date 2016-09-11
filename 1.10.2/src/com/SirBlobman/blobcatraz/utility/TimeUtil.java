package com.SirBlobman.blobcatraz.utility;

import java.util.Calendar;

public class TimeUtil
{
	public static int year()
	{
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}
	
	public static int month()
	{
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH);
	}
	
	public static int day()
	{
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int hour12()
	{
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR);
		if(hour == 0) hour = 12;
		return hour;
	}
	
	public static int hour24()
	{
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	public static int minute()
	{
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MINUTE);
	}
	
	public static int second()
	{
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.SECOND);
	}
	
	public static String meridiem()
	{
		Calendar c = Calendar.getInstance();
		int m = c.get(Calendar.AM_PM);
		String meridiem = m == 0 ? "AM" : "PM";
		return meridiem;
	}
	
	public static String time12()
	{
		String time = "%02d:%02d:%02d %s";
		int hour = hour12();
		int minute = minute();
		int second = second();
		String meridiem = meridiem();
		time = String.format(time, hour, minute, second, meridiem);
		return time;
	}
	
	public static String time24()
	{
		String time = "%02d:%02d:%02d";
		int hour = hour24();
		int minute = minute();
		int second = second();
		time = String.format(time, hour, minute, second);
		return time;
	}
	
	public static String date()
	{
		String date = "%02d/%02d/%04d";
		int month = month();
		int day = day();
		int year = year();
		date = String.format(date, month, day, year);
		return date;
	}
}