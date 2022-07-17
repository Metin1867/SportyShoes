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

import tr.com.macik.gui.PurchaseDto;
import tr.com.macik.myapp.pojo.Purchase;
import tr.com.macik.utils.ServletHTMLUtil;
import tr.com.macik.utils.SessionUtil;

/**
 * Servlet implementation class PurchaseServlet
 */
@WebServlet("/PurchasesServlet")
public class PurchasesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchasesServlet() {
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
			List<Purchase> purchases = PurchaseDto.readAll();
			printPurchases(pw, purchases);
		} else {
			System.out.println("Redirect to main page");
			response.sendRedirect(page);
		}
		
	}

	private void printPurchases(PrintWriter pw, List<Purchase> purchases) {
		// Menu Main/Refresh/Insert
		pw.append("<h1>Purchase Master List</h1><br>").println();
		pw.append("<a href='pages/profile.jsp'><img src='data/menu3.png' alt='Menu' width='28' height='28'></a>");
		pw.append(" | <a href='PurchasesServlet'><img src='data/refresh.png' alt='Refresh' width='28' height='28'></a>");
		pw.append(" | <a href='PurchaseEditServlet'><img src='data/useradd.png' alt='Add' width='30' height='30'></a>");
		pw.println("<br/><br/>");
		pw.append("<table>");
		pw.append("<tr>");
		// Header
		// pchID; prsID; basketCreated; purchaseActivated; deliveryAddress; deliveryDate;
		// invoiceAddress; paymentDate
		
		pw.append("<th>Purchase<br>Identifier</th>");
		pw.append("<th>Person<br>Identifier</th>");
		pw.append("<th>Created at</th>");
		pw.append("<th>Activated</th>");
		pw.append("<th>Delivery Address</th>");
		pw.append("<th>Delivery Date</th>");
		pw.append("<th>Invoice Address</th>");
		pw.append("<th>Payment Date</th>");
		pw.append("<th>actions</th>");
		pw.append("</tr>");
		// Data
		if (purchases != null && purchases.size()>0)
			for (Purchase purchase : purchases) {
				pw.append("<tr>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(purchase.getPchID())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(purchase.getPrsID())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(purchase.getBasketCreated())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(purchase.getPurchaseActivated())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(purchase.getDeliveryAddress())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(purchase.getDeliveryDate())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(purchase.getInvoiceAddress())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(purchase.getPaymentDate())).append("</td>").println();
				// Menu Edit/Delete Row
				String cmdPars = "pchid="+purchase.getPchID();
				pw.append("<td>").append("<a href='PurchaseEditServlet?"+cmdPars+"'><img src='data/useredit.png' alt='Edit' width='30' height='30'></a>").println();
				cmdPars = cmdPars+"&msg="+purchase.getBasketCreated();
				pw.append("|<a href='PurchaseDeleteServlet?"+cmdPars+"'><img src='data/userdelete.png' alt='Delete' width='30' height='30'></a>");
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
