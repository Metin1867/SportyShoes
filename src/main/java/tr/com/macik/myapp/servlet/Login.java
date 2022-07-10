package tr.com.macik.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tr.com.macik.gui.EmployeeDto;
import tr.com.macik.gui.PersonDto;
import tr.com.macik.myapp.pojo.Person;
import tr.com.macik.myapp.pojo.User;
import tr.com.macik.utils.FileUtil;
import tr.com.macik.utils.SqlUtil;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("pages/profile.jsp");

		String msg = "Login process started."; 
		System.out.println("***** " + msg + " ******************************");
		String page = "index.html"; 
		HttpSession hsess=request.getSession();

		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath()).println();
		pw.append("<b>Login Servlet initiated ...<b>");
		pw.append(request.getParameter("cmd")).println();
		String referer = request.getHeader("Referer");
		referer = FileUtil.toFilename(referer);
		User user = null;
		if ("Cancel".equals(request.getParameter("cmd"))) {
			System.out.println("Login cancelled!");
		} else {
			String usrLogin = request.getParameter("user");
			String pwd = request.getParameter("pwd");
			System.out.println("Credentials " + usrLogin + "/"
							+ (pwd==null || "".equals(pwd.trim())?"???":"***") );

			// search the User
			user = PersonDto.readUserByLogin(usrLogin);
			if (user != null) {
				if (user.getUsrPassword().equals(pwd)) {
					// logged in
					hsess.setAttribute("userid", user.getUsrLogin());
					boolean isEmployee = EmployeeDto.isEmployee(user.getPrsID());
					pw.write(user.getUsrLogin() + ", is it an internal user? " + isEmployee);
					if (isEmployee) {
						Person person = new Person();
						person.setPrsID(user.getPrsID());
						person = PersonDto.readByID(person);
						hsess.setAttribute("employee", true);
						msg = "Hi " + person.getFirstname() + ", welcome!"; 

					} else if ("admin@sportyshoes.com".equals(user.getUsrLogin())) {
						hsess.setAttribute("employee", true);
						msg = "Hi Admin, welcome!"; 						
					} else {
						msg = "You are not allowed to login as employee: " + isEmployee;
						hsess.setAttribute("employee", false);
						
					}
					user.setLastSuccessLogin(SqlUtil.getCurrentDate());
					// TODO: tbd
					user.setCounterLogin(user.getCounterLogin()+1);

					System.out.println("Logged in! " + msg);
					rd.forward(request, response);
				} else {
					System.out.println("wrong password, logged out, retry again");
					user.setLastFailedLogin(SqlUtil.getCurrentDate());
					// TODO: tbd
					user.setCounterLogin(user.getCounterLogin()-1);
				}
			} else {
				msg = "User not found, processing further as anonymous user.";
				hsess.invalidate();
				System.out.println(msg);
			}
		}
		if (user==null)
			if ("login.html".equals(referer)) {
				System.out.println("Redirect to main page");
				response.sendRedirect("index.html");
			} else {
				System.out.print("Redirect to "); System.out.println(referer);
				response.sendRedirect(referer);
			}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
