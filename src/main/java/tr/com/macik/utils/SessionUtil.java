package tr.com.macik.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
	

	public static boolean loggedIn(HttpServletRequest request) {
		HttpSession hs=request.getSession();
		if (hs.getAttribute("userid")==null)
			return false;
		else
			return true;
	}

	public static boolean getBoolean(Object attribute, boolean defaultValue) {
		if (attribute == null)
			return defaultValue;
		return (boolean) attribute;
	}

	public static boolean getBoolean(Object attribute) {
		return getBoolean(attribute, false);
	}


	public static String getString(Object attribute) {
		if (attribute == null)
			return (String) null;
		return (String) attribute;
	}
}
