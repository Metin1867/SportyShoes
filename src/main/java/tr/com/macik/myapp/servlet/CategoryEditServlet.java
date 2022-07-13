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
import tr.com.macik.utils.ServletHTMLUtil;

/**
 * Servlet implementation class CategoryEditServlet
 */
@WebServlet("/CategoryEditServlet")
public class CategoryEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private boolean isNew=true;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CategoryEditServlet.doGet(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		String catidStr = request.getParameter("catid");
		int catid = -1;
		Category cat = new Category();
		if (catidStr!=null) {
			catid = Integer.valueOf(catidStr);
			isNew = false;
			
			cat.setCatID(catid);
			cat = CategoryDto.readByID(cat);
			if (cat==null)
				cat = new Category();
		}
		showEditForm(pw, cat);
	}

	private void showEditForm(PrintWriter pw, Category cat) {
		pw.append("<h1>Category Maintenance</h1>");
		if (cat == null) {
			cat = new Category();
			System.out.println("Category Maintenance: INSERT Form");
		} else {
			System.out.println("Category Maintenance: UPDATE Form");
		}
		pw.append(ServletHTMLUtil.startFormPost("CategoryEditServlet")); 
		pw.append(ServletHTMLUtil.getNumberInputReadOnly("Category Identifier", "catid", cat.getCatID() ));
		pw.append(ServletHTMLUtil.getTextInput("Label", "category_label", cat.getCategoryLabel() ));
		pw.append(ServletHTMLUtil.getTextInput("Description", "category_description", cat.getCategoryDescription() ));
		pw.append(ServletHTMLUtil.getByteInput("Image", "category_image", cat.getCategoryImage() ));
		pw.append(ServletHTMLUtil.getSubmitInput("Save"));
		pw.append(ServletHTMLUtil.getSubmitInput("Cancel"));
		pw.append(ServletHTMLUtil.endForm()); 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UserEditServlet.doPost(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		String action = request.getParameter("submit");
		if (!"Cancel".equals(action)) {
			int catid = ServletHTMLUtil.getIntValue(request.getParameter("catid"));
			String categoryLabel = request.getParameter("category_label");
			String categoryDescription = request.getParameter("category_description");
			byte[] categoryImage = ServletHTMLUtil.getByteValue(request.getParameter("category_image"));
			Category cat = new Category(catid, categoryLabel, categoryDescription, categoryImage);
			
			if (isNew) {
				System.out.println("Category INSERT");
				CategoryDto.create(cat);
			} else {
				System.out.println("Category UPDATE");
				CategoryDto.update(cat);
			}
		}
		System.out.println("Redirect to CategoriesServlet");
		RequestDispatcher rd = request.getRequestDispatcher("CategoriesServlet");
		rd.forward(request, response);
		
	}

}
