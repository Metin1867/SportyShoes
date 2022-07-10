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

import tr.com.macik.gui.CategoryDto;
import tr.com.macik.myapp.pojo.Category;
import tr.com.macik.myapp.pojo.User;
import tr.com.macik.utils.ServletHTMLUtil;
import tr.com.macik.utils.SessionUtil;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoriesServlet")
public class CategoriesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoriesServlet() {
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
		pw.append("<b>Categories Servlet initiated ...<b>");
		pw.append(request.getParameter("cmd")).println();

		String usrLogin = (String) hsess.getAttribute("userid");
		boolean isEmployee = SessionUtil.getBoolean(hsess.getAttribute("employee"));

		if (usrLogin != null && isEmployee) {
			response.setContentType("text/html");
			List<Category> category = CategoryDto.readAll();
			printCategories(pw, category);
		} else {
			System.out.println("Redirect to main page");
			response.sendRedirect(page);
		}
		
	}

	private void printCategories(PrintWriter pw, List<Category> categories) {
		// Menu Main/Refresh/Insert
		pw.append("<h1>Category Master List</h1><br>").println();
		pw.append("<a href='pages/profile.jsp'><img src='data/menu3.png' alt='Menu' width='28' height='28'></a>");
		pw.append(" | <a href='CategoriesServlet'><img src='data/refresh.png' alt='Refresh' width='28' height='28'></a>");
		pw.append(" | <a href='CategoryEditServlet'><img src='data/useradd.png' alt='Add' width='30' height='30'></a>");
		pw.println("<br/><br/>");
		pw.append("<table>");
		pw.append("<tr>");
		// Header
		// catID; categoryLabel; categoryDescription; categoryImage;

		pw.append("<th>Category<br>Identifier</th>");
		pw.append("<th>Label</th>");
		pw.append("<th>Description</th>");
		pw.append("<th>Image</th>");
		pw.append("<th>actions</th>");
		pw.append("</tr>");
		// Data
		if (categories != null && categories.size()>0)
			for (Category category : categories) {
				pw.append("<tr>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(category.getCatID())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(category.getCategoryLabel())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(category.getCategoryDescription())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(category.getCategoryImage()).toString()).append("</td>").println();
				// Menu Edit/Delete Row
				String cmdPars = "catid="+category.getCatID();
				pw.append("<td>").append("<a href='CategoryEditServlet?"+cmdPars+"'><img src='data/useredit.png' alt='Edit' width='30' height='30'></a>").println();
				cmdPars = cmdPars+"&msg="+category.getCategoryLabel();
				pw.append("|<a href='CategoryDeleteServlet?"+cmdPars+"'><img src='data/userdelete.png' alt='Delete' width='30' height='30'></a>");
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
