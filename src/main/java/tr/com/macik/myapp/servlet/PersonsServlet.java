package tr.com.macik.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tr.com.macik.gui.PersonDto;
import tr.com.macik.myapp.pojo.Category;
import tr.com.macik.myapp.pojo.Person;
import tr.com.macik.utils.ServletHTMLUtil;
import tr.com.macik.utils.SessionUtil;

/**
 * Servlet implementation class PersonsServlet
 */
@WebServlet("/PersonsServlet")
public class PersonsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonsServlet() {
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
		pw.append("<b>Persons Servlet initiated ...<b>");
		pw.append(request.getParameter("cmd")).println();

		String usrLogin = (String) hsess.getAttribute("userid");
		boolean isEmployee = SessionUtil.getBoolean(hsess.getAttribute("employee"));

		if (usrLogin != null && isEmployee) {
			response.setContentType("text/html");
			List<Person> persons = PersonDto.readAllPerson();
			printPersons(pw, persons);
		} else {
			System.out.println("Redirect to main page");
			response.sendRedirect(page);
		}
		
	}

	private void printPersons(PrintWriter pw, List<Person> persons) {
		// Menu Main/Refresh/Insert
		pw.append("<h1>Person Master List</h1><br>").println();
		pw.append("<a href='pages/profile.jsp'><img src='data/menu3.png' alt='Menu' width='28' height='28'></a>");
		pw.append(" | <a href='PersonsServlet'><img src='data/refresh.png' alt='Refresh' width='28' height='28'></a>");
		pw.append(" | <a href='PersonEditServlet'><img src='data/useradd.png' alt='Add' width='30' height='30'></a>");
		pw.println("<br/><br/>");
		pw.append("<table>");
		pw.append("<tr>");
		// Header
		// prsID; salutation; firstname; lastname; birthday

		pw.append("<th>Person<br>Identifier</th>");
		pw.append("<th>Salutation</th>");
		pw.append("<th>Firstname</th>");
		pw.append("<th>Lastname</th>");
		pw.append("<th>Birthday</th>");
		pw.append("<th>actions</th>");
		pw.append("</tr>");
		// Data
		if (persons != null && persons.size()>0)
			for (Person person : persons) {
				pw.append("<tr>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(person.getPrsID())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(person.getSalutation())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(person.getFirstname())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(person.getLastname()).toString()).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(person.getBirthday()).toString()).append("</td>").println();
				// Menu Edit/Delete Row
				String cmdPars = "prsid="+person.getPrsID();
				pw.append("<td>").append("<a href='PersonEditServlet?"+cmdPars+"'><img src='data/useredit.png' alt='Edit' width='30' height='30'></a>").println();
				cmdPars = cmdPars+"&msg="+person.getFirstname()+ " " +person.getLastname();
				pw.append("|<a href='PersonDeleteServlet?"+cmdPars+"'><img src='data/userdelete.png' alt='Delete' width='30' height='30'></a>");
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
