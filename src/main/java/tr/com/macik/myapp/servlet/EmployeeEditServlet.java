package tr.com.macik.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tr.com.macik.gui.EmployeeDto;
import tr.com.macik.gui.PersonDto;
import tr.com.macik.myapp.pojo.Employee;
import tr.com.macik.myapp.pojo.User;
import tr.com.macik.utils.ServletHTMLUtil;

/**
 * Servlet implementation class EmployeeEditServlet
 */
@WebServlet("/EmployeeEditServlet")
public class EmployeeEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private boolean isNew=true;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("EmployeeEditServlet.doGet(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		String empidStr = request.getParameter("empid");
		System.out.println("Parameter empid "+empidStr);
		int empid = -1;
		Employee emp = new Employee();
		if (empidStr!=null) {
			empid = Integer.valueOf(empidStr);
			isNew = false;
			
			emp.setEmpID(empid);
			emp = EmployeeDto.readByID(emp);
			if (emp == null)
				emp = new Employee();			
		}
		System.out.println("Employee Object: " + emp);
			
		showEditForm(pw, emp);
	}

	private void showEditForm(PrintWriter pw, Employee emp) {
		pw.append("<h1>Employee Maintenance</h1>");
		pw.append(ServletHTMLUtil.startFormPost("EmployeeEditServlet")); 
		pw.append(ServletHTMLUtil.getNumberInputReadOnly("Employee Identifier", "empid", emp.getEmpID() ));
		pw.append(ServletHTMLUtil.getNumberInput("Person Identifier", "prsid", emp.getPrsID() ));
		pw.append(ServletHTMLUtil.getTextInput("Social Security Number", "social_security_number", emp.getSocialSecurityNumber() ));
		pw.append(ServletHTMLUtil.getTextInput("Civil Status", "civil_status", emp.getCivilStatus() ));
		pw.append(ServletHTMLUtil.getNumberInput("Number of child", "number_of_child", emp.getNumberOfChild() ));
		pw.append(ServletHTMLUtil.getTextInput("Phone", "phone", emp.getPhone() ));
		pw.append(ServletHTMLUtil.getTextInput("Email", "email", emp.getEmail() ));
		pw.append(ServletHTMLUtil.getTextInput("emergencyContact", "emergency_contact", emp.getEmergencyContact() ));
		pw.append(ServletHTMLUtil.getTextInput("emergencyNumber", "emergency_number", emp.getEmergencyNumber() ));
		pw.append(ServletHTMLUtil.getSubmitInput("register"));
		pw.append(ServletHTMLUtil.endForm()); 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("EmployeeEditServlet.doPost(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		int empid = ServletHTMLUtil.getIntValue(request.getParameter("empid"));
		int prsid = ServletHTMLUtil.getIntValue(request.getParameter("prsid"));
		String socialSecurityNumber = request.getParameter("social_security_number");
		String civilStatus = request.getParameter("civil_status");
		int numberOfChild = ServletHTMLUtil.getIntValue(request.getParameter("number_of_child"));
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String emergencyContact = request.getParameter("emergency_contact");
		String emergencyNumber = request.getParameter("emergency_number");
		Employee emp = new Employee(empid, prsid, socialSecurityNumber, civilStatus, numberOfChild, phone, email, emergencyContact, emergencyNumber);
		
		if (isNew) {
			System.out.println("Employee INSERT");
			EmployeeDto.create(emp);
		} else {
			System.out.println("Employee UPDATE");
			EmployeeDto.update(emp);
		}
		
		System.out.println("Redirect to EmployeesServlet");
		RequestDispatcher rd = request.getRequestDispatcher("EmployeesServlet");
		rd.forward(request, response);
		
	}

}
