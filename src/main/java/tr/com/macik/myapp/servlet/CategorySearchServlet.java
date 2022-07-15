package tr.com.macik.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tr.com.macik.gui.CategoryDto;
import tr.com.macik.gui.ProductDto;
import tr.com.macik.myapp.pojo.Category;
import tr.com.macik.myapp.pojo.Product;
import tr.com.macik.utils.ServletHTMLUtil;

/**
 * Servlet implementation class CategorySearchServlet
 */
@WebServlet("/CategorySearchServlet")
public class CategorySearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String prdidStr = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategorySearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CategorySearchServlet.doGet(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		String catidStr = request.getParameter("catid");
		if (prdidStr==null)
			prdidStr = request.getParameter("prdid");
		int catid = -1;
		Category cat = new Category();
		if (catidStr!=null) {
			catid = Integer.valueOf(catidStr);
			
			cat.setCatID(catid);
			cat = CategoryDto.readByID(cat);
			if (cat==null)
				cat = new Category();
		}
		showEditForm(pw, cat);
	}

	private void showEditForm(PrintWriter pw, Category cat) {
		pw.append("<h1>Category Search</h1>");
		pw.append(ServletHTMLUtil.startFormPost("CategorySearchServlet")); 
		pw.append(ServletHTMLUtil.getNumberInput("Category Identifier", "catid", cat.getCatID() ));
		pw.append(ServletHTMLUtil.getTextInput("Label", "category_label", cat.getCategoryLabel() ));
		pw.append(ServletHTMLUtil.getTextInput("Description", "category_description", cat.getCategoryDescription() ));
		pw.append(ServletHTMLUtil.getByteInput("Image", "category_image", cat.getCategoryImage() ));
		pw.append(ServletHTMLUtil.getSubmitInput("Save"));
		pw.append(ServletHTMLUtil.getSubmitInput("Search"));
		pw.append(ServletHTMLUtil.getSubmitInput("Cancel"));
		pw.append(ServletHTMLUtil.endForm()); 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CategorySearchServlet.doPost(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		String page = null;
		response.setContentType("text/html");
		String action = request.getParameter("submit");
		if ("doGet".equals(action)) {
			doGet(request, response);
		} else if ("Save".equals(action)) {
			page = "ProductEditServlet?prdid="+prdidStr+"&submit=doGet";
			Product product = new Product();
			product.setPrdID(Integer.valueOf(prdidStr));
			product = ProductDto.readByID(product);
			int catid = ServletHTMLUtil.getIntValue(request.getParameter("catid"));
			product.setCatID(catid);
			ProductDto.update(product);

			System.out.println("Redirect to CategoriesServlet");
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		} else if ("Search".equals(action)) {
			page = "CategorySearchServlet?submit=doGet&catid=";
			int catid = ServletHTMLUtil.getIntValue(request.getParameter("catid"));
			String categoryLabel = request.getParameter("category_label");
			String categoryDescription = request.getParameter("category_description");
			byte[] categoryImage = ServletHTMLUtil.getByteValue(request.getParameter("category_image"));
			Category cat = new Category(catid, categoryLabel, categoryDescription, categoryImage);
			
			List<Category> cats = CategoryDto.search(cat);
			if (cats != null && cats.size()>0) {
				cat = cats.get(0);
				page += cat.getCatID();
			} else {
				page += "0";
			}

			System.out.println("Redirect to CategoriesServlet");
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}		
	}

}
