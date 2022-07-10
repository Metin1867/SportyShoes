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

import tr.com.macik.gui.EmployeeDto;
import tr.com.macik.myapp.pojo.Employee;
import tr.com.macik.utils.ServletHTMLUtil;

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet("/EmployeesServlet")
public class EmployeesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesServlet() {
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

		String usrLogin = (String) hsess.getAttribute("userid");
		boolean isEmployee = (boolean) hsess.getAttribute("employee");
		System.out.println("Credentials: " + usrLogin + "/" + isEmployee);

		if (usrLogin != null && isEmployee) {
			response.setContentType("text/html");
			List<Employee> employees = EmployeeDto.readAll();
			printEmployees(pw, employees);
		} else {
			System.out.println("Redirect to main page");
			response.sendRedirect(page);
		}
		
	}

	private void printEmployees(PrintWriter pw, List<Employee> employees) {
		// Menu Main/Refresh/Insert
		pw.append("<h1>Employee Master List</h1><br>").println();
		pw.append("<a href='Login'><img src='data/menu3.png' alt='Menu' width='28' height='28'></a>");
		pw.append(" | <a href='StudentsServlet'><img src='data/refresh.png' alt='Refresh' width='28' height='28'></a>");
		pw.append(" | <a href='StudentEditServlet'><img src='data/useradd.png' alt='Add' width='30' height='30'></a>");
		pw.println("<br/><br/>");
		pw.append("<table>");
		pw.append("<tr>");
		// Header
		// empID; prsID; socialSecurityNumber; civilStatus	// unknown, single, married, divorced
		// numberOfChild; phone; email; emergencyContact; emergencyNumber;
		
		pw.append("<th>Employee<br>Identifier</th>");
		pw.append("<th>Person<br>Identifier</th>");
		pw.append("<th>Social Securoty Number</th>");
		pw.append("<th>Civil Status</th>");
		pw.append("<th>Number of Childs</th>");
		pw.append("<th>Phone</th>");
		pw.append("<th>Email</th>");
		pw.append("<th>Emergency Contact</th>");
		pw.append("<th>Emergency Phone</th>");
		pw.append("<th>actions</th>");
		pw.append("</tr>");
		// Data
		System.out.println(employees);
		for (Employee emp : employees) {
			pw.append("<tr>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(emp.getEmpID())).append("</td>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(emp.getPrsID()).toString()).append("</td>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(emp.getSocialSecurityNumber())).append("</td>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(emp.getCivilStatus())).append("</td>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(emp.getNumberOfChild())).append("</td>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(emp.getPhone())).append("</td>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(emp.getEmail())).append("</td>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(emp.getEmergencyContact())).append("</td>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(emp.getEmergencyNumber())).append("</td>").println();
			// Menu Edit/Delete Row
			String cmdPars = "empid="+emp.getEmpID();
			pw.append("<td>").append("<a href='EmployeeEditServlet?"+cmdPars+"'><img src='data/useredit.png' alt='Edit' width='30' height='30'></a>").println();
			cmdPars = cmdPars+"&msg="+emp.getPrsID()+" "+emp.getEmail();
			pw.append("|<a href='EmployeeDeleteServlet?"+cmdPars+"'><img src='data/userdelete.png' alt='Delete' width='30' height='30'></a>");
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
