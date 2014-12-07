package cn.edu.tju.se.base.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTools {
	public static Date stringToDate(String str) {
		Date date = null;
		if (str==null||str.equals("")){
			return null;
		}
		SimpleDateFormat myDateFormat = new SimpleDateFormat(
				"yyyy/MM/dd");
		try {
			date = myDateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
