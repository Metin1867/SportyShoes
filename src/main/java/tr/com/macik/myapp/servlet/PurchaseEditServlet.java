package tr.com.macik.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tr.com.macik.gui.PersonDto;
import tr.com.macik.gui.PurchaseDto;
import tr.com.macik.myapp.pojo.Person;
import tr.com.macik.myapp.pojo.Purchase;
import tr.com.macik.myapp.pojo.PurchaseDetails;
import tr.com.macik.myapp.pojo.User;
import tr.com.macik.utils.AppLog;
import tr.com.macik.utils.ServletHTMLUtil;

/**
 * Servlet implementation class ProductEditServlet
 */
@WebServlet("/PurchaseEditServlet")
public class PurchaseEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private boolean isNew=true;
	private String cmdPars="";
	private String userid="";
	private int prsid;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AppLog.out("PurchaseEditServlet.doGet(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		isNew=true;
		cmdPars="";

		userid = request.getSession().getAttribute("userid").toString();
		System.out.println("USer Login: "+ userid);
		prsid = PersonDto.getPrsId(userid);
		
		String pchidStr = request.getParameter("pchid");
		int pchid = -1;
		Purchase pch = new Purchase();
		if (pchidStr==null) {
			isNew = true;

			pch.setBasketCreated(AppLog.currentTime());
			if (userid.startsWith("admin")) {

			} else {
				pch.setPrsID(prsid);
				//pch.setDeliveryAddress(User Delivery Address);
				//pch.setInvoiceAddress(User Invoice Address);
			}
		} else {
			isNew = false;
			pchid = Integer.valueOf(pchidStr);

			pch.setPchID(pchid);
			pch = PurchaseDto.readByID(pch);
		}
		showEditForm(pw, pch);
	}

	private void showEditForm(PrintWriter pw, Purchase pch) {
		pw.append("<h1>Purchase Maintenance</h1>");
		pw.append(ServletHTMLUtil.startFormPost("PurchaseEditServlet")); 
		pw.append(ServletHTMLUtil.getNumberInputReadOnly("Purchase Identifier", "pchid", pch.getPchID() ));
		if (userid.startsWith("admin"))
			pw.append(ServletHTMLUtil.getNumberInput("Person Identifier", "prsid", pch.getPrsID() ));
		else
			pw.append(ServletHTMLUtil.getNumberInputReadOnly("Person Identifier", "prsid", pch.getPrsID() ));
		pw.append(ServletHTMLUtil.getDateInput("Basket created", "basket_created", pch.getBasketCreated() ));
		pw.append(ServletHTMLUtil.getDateInput("Purchase activated", "purchase_activated", pch.getPurchaseActivated() ));
		pw.append(ServletHTMLUtil.getNumberInput("Delivery Address", "delivery_address", pch.getDeliveryAddress() ));
		pw.append(ServletHTMLUtil.getDateInput("Delivery Date", "delivery_date", pch.getDeliveryDate() ));
		pw.append(ServletHTMLUtil.getNumberInput("Invoice Address", "invoice_address", pch.getInvoiceAddress() ));
		pw.append(ServletHTMLUtil.getDateInput("Payment Date", "payment_date", pch.getPaymentDate() ));
		pw.append(ServletHTMLUtil.getSubmitInput("Save"));
		pw.append(ServletHTMLUtil.getSubmitInput("Cancel"));
		pw.append(ServletHTMLUtil.endForm()); 
		
		cmdPars += "pchid="+pch.getPchID();
	
		if (!isNew) {
			PurchaseDetails pcd = new PurchaseDetails();
			pcd.setPchID(pch.getPchID());
			pcd.setEnabled(false);	// don't search	
			pcd.setCount(-1);		// don't search
			showEditForm(pw, pcd);
		}
	}


	private void showEditForm(PrintWriter pw, PurchaseDetails pcd) {
		List<PurchaseDetails> purchaseDetails = PurchaseDto.search(pcd);
		cmdPars += "&caller=PurchaseEditServlet";
		cmdPars += "&pchid="+pcd.getPchID();
		pw.append("Purchase Details, Add Products | <a href='PurchaseDetailsEditServlet?"+cmdPars+"'><img src='data/useradd.png' alt='Add' width='30' height='30'></a>");
		pw.println("<br/><br/>");
		pw.append("<table>");
		pw.append("<tr>");
		// Header
		// pcdID; pchID; prdID; enabled; count; price
		
		pw.append("<th>Purchase Detail<br/>Identifier</th>");
		pw.append("<th>Purchase<br/>Identifier</th>");
		pw.append("<th>Product<br/>Identifier</th>");
		pw.append("<th>Enabled</th>");
		pw.append("<th>Count</th>");
		pw.append("<th>Price</th>");
		pw.append("<th>actions</th>");
		pw.append("</tr>");
		// Data
		if (purchaseDetails != null && purchaseDetails.size()>0)
			for (PurchaseDetails purchaseDetail : purchaseDetails) {
				pw.append("<tr>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(purchaseDetail.getPcdID())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(purchaseDetail.getPchID())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(purchaseDetail.getPrdID())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(purchaseDetail.isEnabled())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(purchaseDetail.getCount())).append("</td>").println();
				pw.append("<td>").append(ServletHTMLUtil.getValue(purchaseDetail.getPrice())).append("</td>").println();
				// Menu Edit/Delete Row
				String cmdPars = "&pchid="+purchaseDetail.getPchID();
				cmdPars += "&pcdid="+purchaseDetail.getPcdID();
				AppLog.keyVal("<a href='PurchaseDetailsEditServlet?"+cmdPars+"'>", "Command Parse Arguments", cmdPars);
				pw.append("<td>").append("<a href='PurchaseDetailsEditServlet?"+cmdPars+"'><img src='data/useredit.png' alt='Edit' width='30' height='30'></a>").println();
				cmdPars += "&msg="+purchaseDetail.getPchID()+"/"+purchaseDetail.getPrdID();
				AppLog.keyVal("<a href='PurchaseDetailsDeleteServlet?"+cmdPars+"'>", "Command Parse Arguments", cmdPars);
				pw.append("|<a href='PurchaseDetailsDeleteServlet?"+cmdPars+"'><img src='data/userdelete.png' alt='Delete' width='30' height='30'></a>");
				pw.append("</td>").println();
				pw.append("</tr>").println();
			}
		pw.append("<table>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AppLog.out("PurchaseEditServlet.doPost(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		String action = request.getParameter("submit");
		if ("doGet".equals(action)) {
			AppLog.out("Skip to doGet(...)");
			doGet(request, response);
		} else {
			if (!"Cancel".equals(action)) {
				int pchid = ServletHTMLUtil.getIntValue(request.getParameter("pchid"));
				int prsid = ServletHTMLUtil.getIntValue(request.getParameter("prsid"));
				Date basketCreated = ServletHTMLUtil.getDate(request.getParameter("basket_created"));
				Date purchaseActivated = ServletHTMLUtil.getDate(request.getParameter("purchase_activated"));
				int deliveryAddress = ServletHTMLUtil.getIntValue(request.getParameter("delivery_address"));
				Date deliveryDate = ServletHTMLUtil.getDate(request.getParameter("delivery_date"));
				int invoiceAddress = ServletHTMLUtil.getIntValue(request.getParameter("invoice_address"));
				Date paymentDate = ServletHTMLUtil.getDate(request.getParameter("payment_date"));
				Purchase pch = new Purchase(pchid, prsid, basketCreated, purchaseActivated, deliveryAddress, deliveryDate, invoiceAddress, paymentDate);
				
				if (isNew) {
					AppLog.keyVal("Purchase", "INSERT");
					PurchaseDto.create(pch);
				} else {
					AppLog.keyVal("Purchase", "UPDATE");
					PurchaseDto.update(pch);
				}
			}
			
			String redirect="PurchasesServlet";
			AppLog.keyVal("Redirect to", redirect);
			RequestDispatcher rd = request.getRequestDispatcher(redirect);
			rd.forward(request, response);
		}		
	}

}
