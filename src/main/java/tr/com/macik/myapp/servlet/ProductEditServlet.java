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
import tr.com.macik.gui.ProductDto;
import tr.com.macik.myapp.pojo.Category;
import tr.com.macik.myapp.pojo.Product;
import tr.com.macik.utils.ServletHTMLUtil;

/**
 * Servlet implementation class ProductEditServlet
 */
@WebServlet("/ProductEditServlet")
public class ProductEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private boolean isNew=true;
	String cmdPars="";
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ProductEditServlet.doGet(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		isNew=true;
		String prdidStr = request.getParameter("prdid");
		int prdid = -1;
		Product prd = new Product();
		if (prdidStr!=null) {
			prdid = Integer.valueOf(prdidStr);
			isNew = false;
			
			prd.setPrdID(prdid);
			prd = ProductDto.readByID(prd);
			if (prd==null)
				prd = new Product();
		}
		showEditForm(pw, prd);
	}

	private void showEditForm(PrintWriter pw, Product prd) {
		pw.append("<h1>Product Maintenance</h1>");
		pw.append(ServletHTMLUtil.startFormPost("ProductEditServlet")); 
		pw.append(ServletHTMLUtil.getNumberInputReadOnly("Product Identifier", "prdid", prd.getPrdID() ));
		pw.append(ServletHTMLUtil.getNumberInput("Category Identifier", "catid", prd.getCatID() ));
		pw.append(ServletHTMLUtil.getTextInput("Product Label", "product_label", prd.getProductLabel() ));
		pw.append(ServletHTMLUtil.getTextInput("Description", "product_description", prd.getProductDescription() ));
		pw.append(ServletHTMLUtil.getTextInput("Details", "product_details", prd.getProductDetails() ));
		pw.append(ServletHTMLUtil.getByteInput("Image", "producty_image", prd.getProductImage() ));
		pw.append(ServletHTMLUtil.getCurrencyInput("Price", "price", prd.getPrice() ));
		pw.append(ServletHTMLUtil.getSubmitInput("Save"));
		pw.append(ServletHTMLUtil.getSubmitInput("Cancel"));
		pw.append(ServletHTMLUtil.endForm()); 
		
		
		cmdPars += "prdid="+prd.getPrdID();
		
		Category cat = new Category();
		cat.setCatID(prd.getCatID());
		showEditForm(pw, cat);
	}


	private void showEditForm(PrintWriter pw, Category cat) {
		Category category = CategoryDto.readByID(cat);
		cmdPars += "&caller=UserEditServlet";
		pw.println("<br/><br/>");
		if (category != null) {
			cmdPars += "&catid="+cat.getCatID();
			pw.append("<table>");
			pw.append("<tr>");
			// Header
			// catID; categoryLabel; categoryDescription; categoryImage
			
			pw.append("<th>Category<br/>Identifier</th>");
			pw.append("<th>Category Label</th>");
			pw.append("<th>Category Description</th>");
			pw.append("<th>Category Image</th>");
			pw.append("<th>actions</th>");
			pw.append("</tr>");
			// Data
			// for ( : ) {
			pw.append("<tr>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(category.getCatID())).append("</td>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(category.getCategoryLabel())).append("</td>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(category.getCategoryDescription())).append("</td>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(category.getCategoryImage())).append("</td>").println();
			// Menu Edit/Delete Row
			pw.append("<td>").append("<a href='CategorySearchServlet?"+cmdPars+"'><img src='data/useredit.png' alt='Edit' width='30' height='30'></a>").println();
			pw.append("</td>").println();
			pw.append("</tr>").println();
			// }
			pw.append("<table>");
		} else {
			pw.append("Category <a href='CategorySearchServlet?"+cmdPars+"'><img src='data/useradd.png' alt='Add' width='30' height='30'></a>");
			pw.println("<br/><br/>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ProductEditServlet.doPost(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		String action = request.getParameter("submit");
		if ("doGet".equals(action)) {
			doGet(request, response);
		} else {
			if (!"Cancel".equals(action)) {
				int prdid = ServletHTMLUtil.getIntValue(request.getParameter("prdid"));
				int catid = ServletHTMLUtil.getIntValue(request.getParameter("catid"));
				String productLabel = request.getParameter("product_label");
				String productDescription = request.getParameter("product_description");
				String productDetails = request.getParameter("product_details");
				byte[] productImage = ServletHTMLUtil.getByteValue(request.getParameter("product_image"));
				float price = (float) ServletHTMLUtil.getDoubleValue(request.getParameter("price"));
				Product prd = new Product(prdid, catid, productLabel, productDescription, productDetails, productImage, price);
				
				if (isNew) {
					System.out.println("Product INSERT");
					ProductDto.create(prd);
				} else {
					System.out.println("Product UPDATE");
					ProductDto.update(prd);
				}
			}
		}
		System.out.println("Redirect to ProductsServlet");
		RequestDispatcher rd = request.getRequestDispatcher("ProductsServlet");
		rd.forward(request, response);
		
	}

}
