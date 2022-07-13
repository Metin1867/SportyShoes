package tr.com.macik.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tr.com.macik.gui.CategoryDto;
import tr.com.macik.myapp.pojo.Category;

/**
 * Servlet implementation class CategoryDeleteServlet
 */
@WebServlet("/CategoryDeleteServlet")
public class CategoryDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int catid;
	String msg;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryDeleteServlet() {
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
			Category cat = new Category();
			cat.setCatID(catid);
			CategoryDto.delete(cat);

			RequestDispatcher rd = request.getRequestDispatcher("CategoriesServlet");
			rd.forward(request, response);
		} else {
			catid = Integer.valueOf(request.getParameter("catid"));
			msg = request.getParameter("msg");

			pw.append("<h1>Delete Confirmation</h1>").println();
			pw.append("<form action='CategoryDeleteServlet?confirmed=yes' method='post'>").println();
			pw.append("<input type='submit' value='Yes'><br>").println();
			pw.append("</form>").println();   
			pw.append("<form action='CategoriesServlet'>").println();
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
