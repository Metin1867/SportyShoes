package tr.com.macik.unittests;

import javax.servlet.http.HttpSession;

import org.springframework.mock.web.MockHttpServletRequest;


public class SessionTest {
	private static HttpSession oneSession;
	private static HttpSession getSession() {
		if (oneSession == null)
			oneSession = new MockHttpServletRequest().getSession();
		return oneSession;
	}

	public static void main(String[] args) {
		HttpSession sess = getSession();
		
		sess.setAttribute("booleanTestTrue", true);
		sess.setAttribute("booleanTestFalse", false);
		sess.setAttribute("booleanTestNull", null);
		// sess.setAttribute("booleanTestNothing", null);
		
		Object objTrue = sess.getAttribute("booleanTestTrue");
		Object objNothing = sess.getAttribute("booleanTestNothing");
		System.out.println("ClassName: "+objTrue.getClass().getName());
		System.out.println("booleanTestTrue: "+objTrue);
		System.out.println("booleanTestFalse: "+sess.getAttribute("booleanTestFalse"));
		System.out.println("booleanTestNull: "+sess.getAttribute("booleanTestNull"));
		if (objNothing==null)
			System.out.println("Null Value!");
		else
			System.out.println("ClassName: "+objNothing.getClass().getName());
		System.out.println("booleanTestNothing: "+objNothing);
	}
}
