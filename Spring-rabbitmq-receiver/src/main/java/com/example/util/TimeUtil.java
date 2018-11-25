package com.example.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	//返回YYYY-MM-DD hh-mm-ss的日期
			public  static Date getCommTime(Long time) {
				Date date=new Date(time);
				return  date;
			}
			
			//返回时间的分钟数
			public static int getMinute(Date date) {
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date);
		        return calendar.get(Calendar.MINUTE);
		    }
			
			//返回当前的小时数
			 public static int getHour(Date date) {
			        Calendar calendar = Calendar.getInstance();
			        calendar.setTime(date);
			        return calendar.get(Calendar.HOUR_OF_DAY);
			    }

}
