package tr.com.macik.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String msg = "Profile process started."; 
		String page = "index.html"; 
		HttpSession hsess=request.getSession();

		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath()).println();
		pw.append("<b>Login Servlet initiated ...<b>");
		pw.append(request.getParameter("cmd")).println();

		Object usrLogin = hsess.getAttribute("userid");
		Object isEmployee = hsess.getAttribute("employee");

		if (usrLogin != null) {
			if ("true".equals(isEmployee)) {
				pw.write("<a href='UserAdminServlet'>Users</a>");
				pw.write("<a href='EmployeeServlet'>Employee</a>");
				pw.write("<a href='ProductServlet'>Product</a>");
				pw.write("<a href='CategoryServlet'>Category</a>");
			} else {
				
			}
			pw.write("<a href='ShopServlet'>Shop</a>");
			pw.write("<a href='OrderServlet'>Order</a>");
			pw.write("<a href='LogoutServlet'>Logout</a>");
		} else {
			System.out.println("Redirect to main page");
			response.sendRedirect(page);
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
