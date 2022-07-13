package tr.com.macik.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

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
 * Servlet implementation class PersonEditServlet
 */
@WebServlet("/PersonEditServlet")
public class PersonEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private boolean isNew=true;
    private String referer = null;
    private String usridStr = "";
    
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PersonEditServlet.doGet(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		referer = request.getParameter("caller");
		usridStr = request.getParameter("usrid");
		String prsidStr = request.getParameter("prsid");
		int prsid = -1;
		Person prs = new Person();
		if (prsidStr!=null) {
			prsid = Integer.valueOf(prsidStr);
			isNew = false;
			
			prs.setPrsID(prsid);
			prs = PersonDto.readByID(prs);
			if (prs==null) {
				prs = new Person();
				isNew = true;
			}
		}
		showEditForm(pw, prs);
	}

	private void showEditForm(PrintWriter pw, Person prs) {
		pw.append("<h1>Person Maintenance</h1>");
		if (prs == null) {
			prs = new Person();
			System.out.println("Person Maintenance: INSERT Form");
		} else {
			System.out.println("Person Maintenance: UPDATE Form");
		}
		pw.append(ServletHTMLUtil.startFormPost("PersonEditServlet"));
		pw.append(ServletHTMLUtil.getNumberInputReadOnly("Person Identifier", "prsid", prs.getPrsID() ));
		pw.append(ServletHTMLUtil.getTextInput("Salutation", "salutation", prs.getSalutation() ));
		pw.append(ServletHTMLUtil.getTextInput("Firstname", "firstname", prs.getFirstname() ));
		pw.append(ServletHTMLUtil.getTextInput("Lastname", "lastname", prs.getLastname() ));
		pw.append(ServletHTMLUtil.getTextInput("Birthday", "birthday", prs.getBirthday() ));
		pw.append(ServletHTMLUtil.getSubmitInput("Save"));
		pw.append(ServletHTMLUtil.getSubmitInput("Cancel"));
		pw.append(ServletHTMLUtil.endForm()); 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PersonEditServlet.doPost(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		String action = request.getParameter("submit");
		if (!"Cancel".equals(action)) {
			int prsid = ServletHTMLUtil.getIntValue(request.getParameter("prsid"));
			String salutation = request.getParameter("salutation");
			String firstName = request.getParameter("firstname");
			String lastName = request.getParameter("lastname");
			Date birthday = ServletHTMLUtil.getDate(request.getParameter("birthday"));
			Person prs = new Person(prsid, salutation, firstName, lastName, birthday);
			
			if (isNew) {
				System.out.println("Person INSERT");
				prs = PersonDto.create(prs);
				if (usridStr!=null) {
					User usr = new User();
					usr.setUsrID(Integer.valueOf(usridStr));
					usr = PersonDto.readByID(usr);
					usr.setPrsID(prs.getPrsID());
					PersonDto.update(usr);
				}
					
			} else {
				System.out.println("Person UPDATE");
				PersonDto.update(prs);
			}
		}
		if (referer == null)
			referer = "PersonsServlet";
		else {
			referer += "?submit=doGet";
			referer += "&usrid="+usridStr;
		}
		System.out.println(referer);
		RequestDispatcher rd = request.getRequestDispatcher(referer);
		rd.forward(request, response);
		
	}

}
