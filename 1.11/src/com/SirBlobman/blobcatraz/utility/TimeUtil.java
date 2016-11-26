package com.SirBlobman.blobcatraz.utility;

import java.util.Calendar;

public class TimeUtil extends Util
{
	private static Calendar c;
	private static void update() {c = Calendar.getInstance();}
	
	public static int year() {update(); return c.get(Calendar.YEAR);}
	public static int month() {update(); return c.get(Calendar.MONTH);}
	public static int day() {update(); return c.get(Calendar.DAY_OF_MONTH);}
	public static int hour12()
	{
		update();
		int h = c.get(Calendar.HOUR);
		if(h == 0) h = 12;
		return h;
	}
	public static int hour24() {update(); return c.get(Calendar.HOUR_OF_DAY);}
	public static int minute() {update(); return c.get(Calendar.MINUTE);}
	public static int second() {update(); return c.get(Calendar.SECOND);}
	public static String meridiem()
	{
		update();
		int m = c.get(Calendar.AM_PM);
		boolean b = (m == 0);
		String mm = b ? "AM" : "PM";
		return mm;
	}
	
	public static String time12()
	{
		String time = "%02d:%02d:%02d %s";
		int h = hour12();
		int m = minute();
		int s = second();
		String mm = meridiem();
		time = String.format(time, h, m, s, mm);
		return time;
	}
	
	public static String time24()
	{
		String time = "%02d:%02d:%02d";
		int h = hour24();
		int m = minute();
		int s = second();
		time = String.format(time, h, m, s);
		return time;
	}
	
	public static String date()
	{
		String date = "%02d/%02d/%04d";
		int m = month();
		int d = day();
		int y = year();
		date = String.format(date, m, d, y);
		return date;
	}
}