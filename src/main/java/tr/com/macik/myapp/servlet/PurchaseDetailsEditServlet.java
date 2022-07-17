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
import tr.com.macik.myapp.pojo.Purchase;
import tr.com.macik.myapp.pojo.PurchaseDetails;
import tr.com.macik.myapp.pojo.User;
import tr.com.macik.utils.AppLog;
import tr.com.macik.utils.ServletHTMLUtil;

/**
 * Servlet implementation class ProductEditServlet
 */
@WebServlet("/PurchaseDetailsEditServlet")
public class PurchaseDetailsEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private boolean isNew=true;
	private int pchid;
	private String userid="";
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseDetailsEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AppLog.out("PurchaseDetailsEditServlet.doGet(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		isNew=true;
		userid = request.getSession().getAttribute("userid").toString();
		System.out.println("USer Login: "+ userid);
		pchid = Integer.valueOf(request.getParameter("pchid"));
		String pcdidStr = request.getParameter("pcdid");
		int pcdid = -1;
		PurchaseDetails pcd = new PurchaseDetails();
		if (pcdidStr!=null) {
			pcdid = Integer.valueOf(pcdidStr);
			isNew = false;

			pcd.setPcdID(pcdid);
			pcd = PurchaseDto.readByID(pcd);
		} else {
			pcd.setPchID(pchid);
		}
		showEditForm(pw, pcd);
	}

	private void showEditForm(PrintWriter pw, PurchaseDetails pcd) {
		pw.append("<h1>Purchase Details Maintenance</h1>");
		pw.append(ServletHTMLUtil.startFormPost("PurchaseDetailsEditServlet")); 
		pw.append(ServletHTMLUtil.getNumberInputReadOnly("Purchase Details Identifier", "pcdid", pcd.getPcdID() ));
		pw.append(ServletHTMLUtil.getNumberInputReadOnly("Purchase Identifier", "pchid", pcd.getPchID() ));
		pw.append(ServletHTMLUtil.getNumberInput("Product Identifier", "prdid", pcd.getPrdID() ));
		pw.append(ServletHTMLUtil.getBooleanInput("Enabled", "enabled", pcd.isEnabled() ));
		pw.append(ServletHTMLUtil.getNumberInput("Count", "count", pcd.getCount() ));
		pw.append(ServletHTMLUtil.readonly().getCurrencyInput("Price", "price", pcd.getPrice() ));
		pw.append(ServletHTMLUtil.getSubmitInput("Save"));
		pw.append(ServletHTMLUtil.getSubmitInput("Cancel"));
		pw.append(ServletHTMLUtil.endForm()); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AppLog.out("PurchaseDetailsEditServlet.doPost(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		String action = request.getParameter("submit");
		if ("doGet".equals(action)) {
			AppLog.out("Skip to doGet(...)");
			doGet(request, response);
		} else {
			if (!"Cancel".equals(action)) {
		        // Enumeration<String> parameterNames = 
				AppLog.keyVal(request);
		        
				int pcdid = ServletHTMLUtil.getIntValue(request.getParameter("pcdid"));
				int pchid = ServletHTMLUtil.getIntValue(request.getParameter("pchid"));
				int prdid = ServletHTMLUtil.getIntValue(request.getParameter("prdid"));
				boolean enabled = ServletHTMLUtil.getBooleanValue(request.getParameter("enabled"));
				int count = ServletHTMLUtil.getIntValue(request.getParameter("count"));
				float price = (float) ServletHTMLUtil.getDoubleValue(request.getParameter("price"));
				PurchaseDetails pcd = new PurchaseDetails(pcdid, pchid, prdid, enabled, count, price);
				
				if (isNew) {
					AppLog.keyVal("PurchaseDetails", "INSERT");
					PurchaseDto.create(pcd);
				} else {
					AppLog.keyVal("PurchaseDetails", "UPDATE");
					PurchaseDto.update(pcd);
				}
			}
		}
		String redirect="PurchaseEditServlet?submit=doGet&pchid="+pchid;
		AppLog.keyVal("Redirect to", redirect);
		RequestDispatcher rd = request.getRequestDispatcher(redirect);
		rd.forward(request, response);
	}

}
