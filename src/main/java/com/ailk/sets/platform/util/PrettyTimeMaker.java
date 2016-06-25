package com.ailk.sets.platform.util;

import java.util.Date;
import java.util.Locale;

import org.ocpsoft.prettytime.PrettyTime;

public class PrettyTimeMaker {
	private static PrettyTime prettyTime = new PrettyTime(Locale.SIMPLIFIED_CHINESE);
	
//	public static void main(String[] args)
//	{
//		Long time = System.currentTimeMillis()+3600*24*30*1000;
//		System.out.println(prettyTime.format(new Date(time)));;
//	}
	
	public static String format(Date date)
	{
		return prettyTime.format(date);
	}
}
