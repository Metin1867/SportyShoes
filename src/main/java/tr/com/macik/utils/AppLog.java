package tr.com.macik.utils;

import java.lang.reflect.Method;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class AppLog {
	final static String ASSIGNEMENT = "=";
	final static String COLON = ":";
	final static String ARROW = "->";
	final static String OBJECT_IS_NULL = "[Object is null]";
	final static String APPLOG = "APPLOG: ";
	
	static String assignementString = ASSIGNEMENT;
	static char[] keySurrounded = {'[', ']'};

	private AppLog() {
	}
	
	public static void out(String text) {
		System.out.println(APPLOG+text);
	}

	public static void out(Date date) {
        if (date == null)
        	out(OBJECT_IS_NULL);
        else
	    	out(dateToString(date));
	}

	public static void keyVal(String key, String value) {
		out(getKey(key)+assignementString+value);
		
	}

	public static void keyVal(String key, Date dateValue) {
		out(getKey(key)+assignementString+dateToString(dateValue));
		
	}

	public static void keyVal(String context, String key, String value) {
		if (context != null && !"".equals(context.trim()))
			out(context+":> "+ getKey(key)+assignementString+value);
		else
			keyVal(key, value);
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

	public static void keyVal(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
             String paramName = parameterNames.nextElement();
 
            String[] paramValues = request.getParameterValues(paramName);
            for (int i = 0; i < paramValues.length; i++) {
                String paramValue = paramValues[i];
                keyVal(paramName, paramValue);
            }
 
        }
	}
	
	public Class<?> definingClass(Class<?> clz, String method) throws NoSuchMethodException, SecurityException {
	    Method m = clz.getMethod(method);
	    return m.getDeclaringClass();
	}
	
	public Class<?> definingClass(Class<?> clz) {	
	    try {
			return clz.getDeclaringClass();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clz;
	}
}
