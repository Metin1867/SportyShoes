package tr.com.macik.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class AppLog {
	final static String ASSIGNEMENT = "=";
	final static String COLON = ":";
	final static String ARROW = "->";
	final static String OBJECT_IS_NULL = "[Object is null]";
	
	static String assignementString = ASSIGNEMENT;
	static char[] keySurrounded = {'[', ']'};

	private AppLog() {
	}
	
	private static void out(String text) {
		System.out.println(text);
	}

	public static void out(Date date) {
        if (date == null)
        	out(OBJECT_IS_NULL);
        else
	    	out(dateToString(date));
	}

	public static void keyVal(String key, Date dateValue) {
		out(getKey(key)+assignementString+dateToString(dateValue));
		
	}

	private static String getKey(String key) {
		if (keySurrounded == null || keySurrounded.length==0)
			return key;
		char charBegin = keySurrounded[0];
		char charEnd = keySurrounded[0];
		if (keySurrounded.length==2)
			charEnd = keySurrounded[1];
		return charBegin+key+charEnd;
	}

	public static String dateToString(Date dateValue) {
		if (dateValue == null)
			return OBJECT_IS_NULL;
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-M-d hh:mm:ss");
        String dateStr = sdf.format(dateValue);
    	return dateStr;
	}

	public static Date currentTime() {
		return new Date(System.currentTimeMillis());
	}

}
