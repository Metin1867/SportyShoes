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

}
