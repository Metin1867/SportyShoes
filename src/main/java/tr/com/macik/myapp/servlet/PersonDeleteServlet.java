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
import tr.com.macik.utils.FileUtil;

/**
 * Servlet implementation class PersonDeleteServlet
 */
@WebServlet("/PersonDeleteServlet")
public class PersonDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int prsid;
	private String msg;
    private String referer = null;
    private String usridStr = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		if ("yes".equals(request.getParameter("confirmed"))) {
			System.out.println("Delete confirmed");
			Person prs = new Person();
			prs.setPrsID(prsid);
			PersonDto.delete(prs);
			User usr = new User();
			usr.setPrsID(prsid);
			for (User updUser : PersonDto.search(usr)) {
				updUser.setPrsID(0);
				PersonDto.update(usr);
			}
			RequestDispatcher rd = request.getRequestDispatcher(referer);
			rd.forward(request, response);
		} else {
			referer = request.getParameter("caller");
			usridStr = request.getParameter("usrid");
			prsid = Integer.valueOf(request.getParameter("prsid"));
			msg = request.getParameter("msg");

			pw.append("<h1>Delete Confirmation</h1>").println();
			pw.append("<form action='PersonDeleteServlet?confirmed=yes' method='post'>").println();
			pw.append("<input type='submit' value='Yes'><br>").println();
			pw.append("</form>").println();
			if (referer == null)
				referer = "PersonsServlet";
			else {
				referer += "?submit=doGet";
				referer += "&usrid="+usridStr;
			}
			pw.append("<form action='"+referer+"'>").println();
			pw.append("<input type='submit' value='No'><br>").println();
			pw.append("</form>").println();   
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
