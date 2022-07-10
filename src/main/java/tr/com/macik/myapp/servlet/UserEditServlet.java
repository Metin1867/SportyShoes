package tr.com.macik.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tr.com.macik.gui.PersonDto;
import tr.com.macik.myapp.pojo.User;
import tr.com.macik.utils.ServletHTMLUtil;

/**
 * Servlet implementation class UserEditServlet
 */
@WebServlet("/UserEditServlet")
public class UserEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private boolean isNew=true;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UserEditServlet.doGet(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		String usridStr = request.getParameter("usrid");
		int usrid = -1;
		if (usridStr!=null) {
			usrid = Integer.valueOf(usridStr);
			isNew = false;
		}
		User usr = new User(usrid, null, null, -1, false, null, null, 0);
		usr = PersonDto.readByID(usr);
		showEditForm(pw, usr);
	}

	private void showEditForm(PrintWriter pw, User usr) {
		pw.append("<h1>User Maintenance</h1>");
		if (usr == null) {
			usr = new User();
			System.out.println("User Maintenance: INSERT Form");
		} else {
			System.out.println("User Maintenance: UPDATE Form");
		}
		pw.append(ServletHTMLUtil.startFormPost("UserEditServlet")); 
		pw.append(ServletHTMLUtil.getNumberInputReadOnly("User Identifier", "usrid", usr.getUsrID() ));
		pw.append(ServletHTMLUtil.getTextInput("User Login", "usr_login", usr.getUsrLogin() ));
		pw.append(ServletHTMLUtil.getTextInput("Password", "usr_password", usr.getUsrPassword() ));
		pw.append(ServletHTMLUtil.getNumberInput("Person Identifier", "prsid", usr.getPrsID() ));
		pw.append(ServletHTMLUtil.getNumberInput("Counter Login", "counter_login", usr.getCounterLogin() ));
		pw.append(ServletHTMLUtil.getSubmitInput("register"));
		pw.append(ServletHTMLUtil.endForm()); 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UserEditServlet.doPost(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		int usrid = ServletHTMLUtil.getIntValue(request.getParameter("usrid"));
		String usrLogin = request.getParameter("usr_login");
		String usrPassword = request.getParameter("usr_password");
		int prsid = ServletHTMLUtil.getIntValue(request.getParameter("prsid"));
		int counterLogin = ServletHTMLUtil.getIntValue(request.getParameter("counter_login"));
		User usr = new User(usrid, usrLogin, usrPassword, prsid, false, null, null, counterLogin);
		// System.out.println("StudentEditServlet.doPost: " + usr);
		
		if (isNew) {
			System.out.println("User INSERT");
			PersonDto.create(usr);
		} else {
			System.out.println("User UPDATE");
			PersonDto.update(usr);
		}
		
		System.out.println("Redirect to UsersServlet");
		RequestDispatcher rd = request.getRequestDispatcher("UsersServlet");
		rd.forward(request, response);
		
	}

}
