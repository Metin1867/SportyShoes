package tr.com.macik.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tr.com.macik.gui.PersonCommDto;
import tr.com.macik.gui.PersonDto;
import tr.com.macik.myapp.pojo.Person;
import tr.com.macik.myapp.pojo.PersonComm;
import tr.com.macik.myapp.pojo.User;
import tr.com.macik.utils.FileUtil;
import tr.com.macik.utils.ServletHTMLUtil;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String msg = "";
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath()).println();
		pw.append("<b>Register Servlet initiated ...<b>").println();
		pw.append(request.getParameter("cmd")).println();
		String referer = request.getHeader("Referer");
		referer = FileUtil.toFilename(referer);
		String page = "";				
		if ("Cancel".equals(request.getParameter("cmd"))) {
			if ("register.html".equals(referer)) {
				System.out.println("Redirect to main page");
				response.sendRedirect("index.html");
			} else {
				System.out.print("Redirect to "); System.out.println(referer);
				response.sendRedirect(referer);
			}
		} else {
			String pwd = request.getParameter("pwd");
			if (!"".equals(pwd) && pwd.equals(request.getParameter("pwdverify"))) {
				// try to register the person
				msg = registerPerson(request.getParameter("salutation"),
								request.getParameter("firstname"),
								request.getParameter("lastname"),
								ServletHTMLUtil.getDate(request.getParameter("birthday")),
								request.getParameter("email"),
								request.getParameter("phone"),
								pwd);

				page = "/pages/registered.jsp";
			} else {
				msg = "Error: Password verification failes!";
				page = "/pages/error.jsp";				
			}

			System.out.println("Servlet Forward to "+page+"/"+msg);
			request.setAttribute("msg",msg);
			RequestDispatcher rd = request.getRequestDispatcher(page);				
			rd.forward(request, response);
		}
	}

	private String registerPerson(String salutation, String firstname, String lastname
							, Date birthday, String email, String phone, String pwd) {
		String msg = "";
		// search the User
		User user = PersonDto.readUserByLogin(email);
		
		// search the Person
		Person person = new Person(0, null, firstname, lastname, null);
		List<Person> persons = PersonDto.search(person);
		PersonComm comm;
		List<PersonComm> comms = null;
		if (persons.size()>0) {
			person = persons.get(0);

			// search the Communications for existing Person
			comm = new PersonComm(0, person.getPrsID(), null, null, null, null);
			comms = PersonCommDto.search(comm);
		}

		System.out.println("User: " + user);
		if (persons.size()>0) {
			System.out.println("Person: " + person);
			if (comms != null)
				for (PersonComm commsDetail : comms)
					System.out.println("Communication: " + commsDetail);
			msg = "This user is already registered!";
		} else {
			if (user != null) {
				msg = "This user is reverved for internal usage, "
						+ "please contact the system <a href=\"mailto://admin@sportyshoes.com\">administrator</a>.";
			} else {
				msg = "Register the person without avctivation.";
				// create person
				person = PersonDto.create(person);
				// create user
				user = new User(0, email, pwd, person.getPrsID(), false, (Timestamp) null, (Timestamp) null, 0);
				PersonDto.create(user);
				// create comms
				comm = new PersonComm(0, person.getPrsID(), "EMAIL", null, "Email", email);
				PersonCommDto.create(comm);
				if (phone != null && !"".equals(phone)) {
					comm = new PersonComm(0, person.getPrsID(), "PHONE", null, "Phone", phone);
					PersonCommDto.create(comm);
				}
			}
		}
		return msg;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
