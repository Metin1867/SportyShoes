package tr.com.macik.unittests;

import java.sql.Date;
import java.text.SimpleDateFormat;

import tr.com.macik.utils.AppLog;
import tr.com.macik.utils.ServletHTMLUtil;

public class ServletHTMLUtilTest {
	public static void main(String[] args) {
		AppLog.out(ServletHTMLUtil.getDateTime("2022-07-16 11"));
		AppLog.out(ServletHTMLUtil.getDateTime("2022-07-16 11:33"));
		AppLog.out(ServletHTMLUtil.getDateTime("2022-07-16 11:21:44"));
		AppLog.out(ServletHTMLUtil.getDate("2022-07-16 1:43:57"));
		
		// Date actualDateTime = new Date(System.currentTimeMillis());
		AppLog.keyVal("Actual Time", ServletHTMLUtil.getDate(AppLog.dateToString(AppLog.currentTime())));
		
	}

}
