package tr.com.macik.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tr.com.macik.gui.PersonDto;
import tr.com.macik.myapp.pojo.User;
import tr.com.macik.utils.ServletHTMLUtil;
import tr.com.macik.utils.SessionUtil;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UsersServlet")
public class UsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = "index.html"; 
		HttpSession hsess=request.getSession();

		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath()).println();
		pw.append("<b>User Servlet initiated ...<b>");
		pw.append(request.getParameter("cmd")).println();

		String usrLogin = (String) hsess.getAttribute("userid");
		boolean isEmployee = SessionUtil.getBoolean(hsess.getAttribute("employee"));

		if (usrLogin != null && isEmployee) {
			response.setContentType("text/html");
			List<User> users = PersonDto.readAllUser();
			printUsers(pw, users);
		} else {
			System.out.println("Redirect to main page");
			response.sendRedirect(page);
		}
		
	}

	private void printUsers(PrintWriter pw, List<User> users) {
		// Menu Main/Refresh/Insert
		pw.append("<h1>User Master List</h1><br>").println();
		pw.append("<a href='pages/profile.jsp'><img src='data/menu3.png' alt='Menu' width='28' height='28'></a>");
		pw.append(" | <a href='UsersServlet'><img src='data/refresh.png' alt='Refresh' width='28' height='28'></a>");
		pw.append(" | <a href='UserEditServlet'><img src='data/useradd.png' alt='Add' width='30' height='30'></a>");
		pw.println("<br/><br/>");
		pw.append("<table>");
		pw.append("<tr>");
		// Header
		// usrID; usrLogin;	usrPassword; prsID = -1;
		// activated; lastSuccessLogin; lastFailedLogin; counterLogin = 0;

		pw.append("<th>User<br>Identifier</th>");
		pw.append("<th>User Login</th>");
		pw.append("<th>Password</th>");
		pw.append("<th>Person<br>Identifier</th>");
		// pw.append("<th>activated</th>");
		pw.append("<th>Last Success Login</th>");
		pw.append("<th>Last Failed Login</th>");
		pw.append("<th>Counter Login</th>");
		pw.append("<th>actions</th>");
		pw.append("</tr>");
		// Data
		System.out.println(users);
		if (users != null && users.size()>0)
			for (User user : users) {
				pw.append("<tr>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(user.getUsrID())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(user.getUsrLogin())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(user.getUsrPassword())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(user.getPrsID()).toString()).append("</td>").println();
				// pw.append("<td>").append(ServletHTMLUtil.getValue(user.getActivated())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(user.getLastSuccessLogin())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(user.getLastFailedLogin())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(user.getCounterLogin())).append("</td>").println();
				// Menu Edit/Delete Row
				String cmdPars = "usrid="+user.getUsrID();
				pw.append("<td>").append("<a href='UserEditServlet?"+cmdPars+"'><img src='data/useredit.png' alt='Edit' width='30' height='30'></a>").println();
				cmdPars = cmdPars+"&msg="+user.getPrsID()+" "+user.getUsrLogin();
				pw.append("|<a href='UserDeleteServlet?"+cmdPars+"'><img src='data/userdelete.png' alt='Delete' width='30' height='30'></a>");
				pw.append("</td>").println();
				pw.append("</tr>").println();
			}
		pw.append("<table>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
