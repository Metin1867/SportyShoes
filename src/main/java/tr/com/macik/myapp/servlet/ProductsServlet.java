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

import tr.com.macik.gui.ProductDto;
import tr.com.macik.myapp.pojo.Product;
import tr.com.macik.utils.ServletHTMLUtil;
import tr.com.macik.utils.SessionUtil;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductsServlet")
public class ProductsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductsServlet() {
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
		pw.append("<b>Products Servlet initiated ...<b>");
		pw.append(request.getParameter("cmd")).println();

		String usrLogin = (String) hsess.getAttribute("userid");
		boolean isEmployee = SessionUtil.getBoolean(hsess.getAttribute("employee"));

		if (usrLogin != null && isEmployee) {
			response.setContentType("text/html");
			List<Product> products = ProductDto.readAll();
			printProducts(pw, products);
		} else {
			System.out.println("Redirect to main page");
			response.sendRedirect(page);
		}
		
	}

	private void printProducts(PrintWriter pw, List<Product> products) {
		// Menu Main/Refresh/Insert
		pw.append("<h1>Product Master List</h1><br>").println();
		pw.append("<a href='pages/profile.jsp'><img src='data/menu3.png' alt='Menu' width='28' height='28'></a>");
		pw.append(" | <a href='ProductsServlet'><img src='data/refresh.png' alt='Refresh' width='28' height='28'></a>");
		pw.append(" | <a href='ProductEditServlet'><img src='data/useradd.png' alt='Add' width='30' height='30'></a>");
		pw.println("<br/><br/>");
		pw.append("<table>");
		pw.append("<tr>");
		// Header
		// prdID; catID; productLabel; productDescription; productDetails; productImage; price;

		pw.append("<th>Product<br>Identifier</th>");
		pw.append("<th>Category<br>Identifier</th>");
		pw.append("<th>Product Label</th>");
		pw.append("<th>Description</th>");
		pw.append("<th>Details</th>");
		pw.append("<th>Image</th>");
		pw.append("<th>Price</th>");
		pw.append("<th>actions</th>");
		pw.append("</tr>");
		// Data
		if (products != null && products.size()>0)
			for (Product product : products) {
				pw.append("<tr>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(product.getPrdID())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(product.getCatID())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(product.getProductLabel())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(product.getProductDescription())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(product.getProductDetails())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(product.getProductImage())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(product.getPrice())).append("</td>").println();
				// Menu Edit/Delete Row
				String cmdPars = "prdid="+product.getPrdID();
				pw.append("<td>").append("<a href='ProductEditServlet?"+cmdPars+"'><img src='data/useredit.png' alt='Edit' width='30' height='30'></a>").println();
				cmdPars = cmdPars+"&msg="+product.getProductLabel();
				pw.append("|<a href='ProductDeleteServlet?"+cmdPars+"'><img src='data/userdelete.png' alt='Delete' width='30' height='30'></a>");
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
