package tr.com.macik.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tr.com.macik.gui.PersonDto;
import tr.com.macik.myapp.pojo.Person;
import tr.com.macik.myapp.pojo.User;
import tr.com.macik.utils.ServletHTMLUtil;

/**
 * Servlet implementation class UserSearchServlet
 */
@WebServlet("/UserSearchServlet")
public class UserSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String usridStr = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UserSearchServlet.doGet(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		if (usridStr==null || "".equals(usridStr))
			usridStr = request.getParameter("usrid");
		int usrid = -1;
		User usr = new User();
		if (usridStr!=null) {
			usrid = Integer.valueOf(usridStr);
			
			usr.setUsrID(usrid);
			usr = PersonDto.readByID(usr);
			if (usr==null)
				usr = new User();
		}
		showEditForm(pw, usr);
	}

	private void showEditForm(PrintWriter pw, User usr) {
		Person prs = new Person();
		prs.setPrsID(usr.getPrsID());
		prs = PersonDto.readByID(prs);
		if (prs==null)
			prs = new Person();
		pw.append("<h1>User Search</h1>");
		pw.append(ServletHTMLUtil.startFormPost("UserSearchServlet")); 
		pw.append(ServletHTMLUtil.getNumberInput("User Identifier", "usrid", usr.getUsrID() ));
		pw.append(ServletHTMLUtil.getNumberInput("Person Identifier", "prsid", usr.getPrsID() ));
		pw.append(ServletHTMLUtil.getTextInput("User Login", "usr_login", usr.getUsrLogin() ));
		pw.append(ServletHTMLUtil.getTextInput("Firstname", "firstname", prs.getFirstname() ));
		pw.append(ServletHTMLUtil.getTextInput("Lastname", "lastname", prs.getLastname() ));
		pw.append(ServletHTMLUtil.getSubmitInput("Search"));
		pw.append(ServletHTMLUtil.getSubmitInput("Show"));
		pw.append(ServletHTMLUtil.getSubmitInput("Cancel"));
		pw.append(ServletHTMLUtil.endForm()); 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UserSearchServlet.doPost(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		String page = null;
		response.setContentType("text/html");
		String action = request.getParameter("submit");
		if ("doGet".equals(action)) {
			doGet(request, response);
		} else if ("Cancel".equals(action)) {
			page = "UsersServlet";
			usridStr = null;

			System.out.println("Redirect to "+page);
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		} else if ("Show".equals(action)) {
			page = "UserEditServlet?usrid="+usridStr+"&submit=doGet";
			usridStr = null;

			System.out.println("Redirect to "+page);
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		} else if ("Search".equals(action)) {
			page = "UserSearchServlet?submit=doGet&usrid=";
			usridStr = null;
			int usrid = ServletHTMLUtil.getIntValue(request.getParameter("usrid"));
			int prsid = ServletHTMLUtil.getIntValue(request.getParameter("prsid"));
			String usrLogin = request.getParameter("usr_login");
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			
			User usr = new User(usrid, usrLogin, null, prsid, false, null, null, -1);
			List<User> users = PersonDto.search(usr);
			if (users != null && users.size()==1) {
				usr = users.get(0);
				page += usr.getUsrID();
			} else {
				Person prs = new Person(prsid, null, firstname, lastname, null);
				List<Person> persons = PersonDto.search(prs);
				if (persons != null && persons.size()==1) {
					prs = persons.get(0);
					usr = new User(0, null, null, prs.getPrsID(), false, null, null, -1);
					users = PersonDto.search(usr);
					if (users != null && users.size()==1) {
						usr = users.get(0);
						page += usr.getUsrID();
					} else {
						page += "0";
					}
				} else {
					page += "0";
				}
			}

			System.out.println("Redirect to "+page);
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}		
	}

}
