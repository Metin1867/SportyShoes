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
import tr.com.macik.myapp.pojo.Person;
import tr.com.macik.myapp.pojo.User;
import tr.com.macik.utils.ServletHTMLUtil;

/**
 * Servlet implementation class UserEditServlet
 */
@WebServlet("/UserEditServlet")
public class UserEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private boolean isNew=true;
	String cmdPars="";
       
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
		cmdPars = "";
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		isNew=true;
		String usridStr = request.getParameter("usrid");
		int usrid = -1;
		User usr = new User();
		if (usridStr!=null) {
			usrid = Integer.valueOf(usridStr);
			isNew = false;

			usr.setUsrID(usrid);
			usr = PersonDto.readByID(usr);
			if (usr == null) {
				usr = new User();
				isNew = true;
			}
		}
		showEditForm(pw, usr);
	}

	private void showEditForm(PrintWriter pw, User usr) {
		pw.append("<h1>User Maintenance</h1>");
		pw.append(ServletHTMLUtil.startFormPost("UserEditServlet")); 
		pw.append(ServletHTMLUtil.getNumberInputReadOnly("User Identifier", "usrid", usr.getUsrID() ));
		pw.append(ServletHTMLUtil.getTextInput("User Login", "usr_login", usr.getUsrLogin() ));
		pw.append(ServletHTMLUtil.getTextInput("Password", "usr_password", usr.getUsrPassword() ));
		pw.append(ServletHTMLUtil.getNumberInput("Person Identifier", "prsid", usr.getPrsID() ));
		pw.append(ServletHTMLUtil.getNumberInput("Counter Login", "counter_login", usr.getCounterLogin() ));
		pw.append(ServletHTMLUtil.getSubmitInput("Save"));
		pw.append(ServletHTMLUtil.getSubmitInput("Cancel"));
		pw.append(ServletHTMLUtil.endForm()); 

		cmdPars += "usrid="+usr.getUsrID();
		
		Person prs = new Person();
		prs.setPrsID(usr.getPrsID());
		showEditForm(pw, prs);
	}

	private void showEditForm(PrintWriter pw, Person prs) {
		Person person = PersonDto.readByID(prs);
		cmdPars += "&caller=UserEditServlet";
		pw.println("<br/><br/>");
		if (person != null) {
			cmdPars += "&prsid="+prs.getPrsID();
			pw.append("<table>");
			pw.append("<tr>");
			// Header
			// prsID; salutation; firstname; lastname; birthday
			
			pw.append("<th>Person<br/>Identifier</th>");
			pw.append("<th>Salutation</th>");
			pw.append("<th>Firstname</th>");
			pw.append("<th>Lastname</th>");
			pw.append("<th>Birthday</th>");
			pw.append("<th>actions</th>");
			pw.append("</tr>");
			// Data
			// for ( : ) {
			pw.append("<tr>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(person.getPrsID())).append("</td>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(person.getSalutation())).append("</td>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(person.getFirstname())).append("</td>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(person.getLastname())).append("</td>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(person.getBirthday())).append("</td>").println();
			// Menu Edit/Delete Row
			pw.append("<td>").append("<a href='PersonEditServlet?"+cmdPars+"'><img src='data/useredit.png' alt='Edit' width='30' height='30'></a>").println();
			pw.append("<td>").append("<a href='PersonDeleteServlet?"+cmdPars+"'><img src='data/userdelete.png' alt='Delete' width='30' height='30'></a>");
			pw.append("</td>").println();
			pw.append("</tr>").println();
			// }
			pw.append("<table>");
		} else {
			pw.append("Person <a href='PersonEditServlet?"+cmdPars+"'><img src='data/useradd.png' alt='Add' width='30' height='30'></a>");
			pw.println("<br/><br/>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UserEditServlet.doPost(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		String action = request.getParameter("submit");
		if ("doGet".equals(action)) {
			doGet(request, response);
		} else {
			if (!"Cancel".equals(action)) {
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
			}
			System.out.println("Redirect to UsersServlet");
			RequestDispatcher rd = request.getRequestDispatcher("UsersServlet");
			rd.forward(request, response);
		}
	}

}
