package tr.com.macik.myapp.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.persistence.Id;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tr.com.macik.gui.ReportDto;
import tr.com.macik.utils.AppLog;
import tr.com.macik.utils.ServletHTMLUtil;

/**
 * Servlet implementation class PurchasesReportServlet
 */
@WebServlet("/PurchasesReportServlet")
public class PurchasesReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchasesReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AppLog.out("PurchasesReportServlet.doGet(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		String orderBy = request.getParameter("orderby").replaceAll("\"", "");
		AppLog.keyVal(request);
		String title="Unknown Report";
		if ("purchase date".equals(orderBy)) {
			title="Purchase Ordered by Purchase Date";
		} else if ("category".equals(orderBy)) {
			title="Purchase Ordered by Category";
		} else
			;
		response.setContentType("text/html");
		pw.println("<!DOCTYPE html>\n");
	    pw.println("<html>\n");
	    pw.println("<head>\n");
	    pw.println("<title>Report</title>\n");
	    pw.println("<link rel=\"stylesheet\" type=\"text/css\" HREF=\"css/style.css\">\n"); // This style sheet doesn't show effect when program run in browser
	    pw.println("</head>\n");
	    pw.println("<body>\n");

		pw.append("<h1>"+title+"</h1><hr>").println();
		String note = "The purchase report ist sorted by "+orderBy+".<br>"
					  +"<hr><br>";
		pw.append(note).println();
		pw.append("<a href='pages/report.jsp'><img src='data/menu3.png' alt='Menu' width='28' height='28'></a>");
		pw.append(" | <a href='PurchasesReportServlet?refresh&orderby=\""+orderBy+"\"'><img src='data/refresh.png' alt='Refresh' width='28' height='28'></a>");
		pw.append("<br>");

		if ("purchase date".equals(orderBy)) {
			List<PurchaseByDateReport> purchaseReport = ReportDto.getPurchaseByDate();
			printQuery(pw, purchaseReport);
		} else if ("category".equals(orderBy)) {
			List<PurchaseByCategoryReport> purchaseReport = ReportDto.getPurchaseByCategory();
			printQueryCategory(pw, purchaseReport);
		} else {
			pw.append("<h1>Report not yet implemented!</h1>");
		}
		pw.println("</body>\n");
	    pw.println("</html>\n");
	    pw.close();
	}
	
	private void printQueryCategory(PrintWriter pw, List<PurchaseByCategoryReport> purchaseReport) {
		pw.append("<table class='report'>").println();
		pw.append("<tr>").println();
		String [] header = {
				"category_identifier",
				"category",
				"product_identifier",
				"product_label",
				"count",
				"avg_unit_price",
				"product_price",
				"last_purchase_date"
	
		};
		for (String col : header) {
			if (!"".equals(col))
				pw.append("<th>").append(col.replace("_", "<br>")).append("</th>");
		}
		pw.println();
		pw.append("</tr>").println();
		for (PurchaseByCategoryReport row : purchaseReport) {
			pw.append("<tr>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getCategoryIdentifier())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getCategory())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getProductIdentifier())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getProductLabel())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getCount())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getAvgUnitPrice())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getProductPrice())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getLastPurchaseDate())).append("</td>");
			pw.append("</tr>").println();
		}
		pw.append("</table>").println();
	}

	private void printQuery(PrintWriter pw, List<PurchaseByDateReport> purchaseReport) {
		pw.append("<table class='report'>").println();
		pw.append("<tr>").println();
		String [] header = {
				"purchase_identifier",
				"purchase_date",
				"purchase_detail_identifier",
				"person_identifier",
				"fullname",
				"payment_date",
				"delivery_date",
				"product_identifier",
				"product_label",
				"count",
				"unit_price",
				"product_price"	
		};
		for (String col : header) {
			if (!"".equals(col))
				pw.append("<th>").append(col.replace("_", "<br>")).append("</th>");
		}
		pw.println();
		pw.append("</tr>").println();
		for (PurchaseByDateReport row : purchaseReport) {
			pw.append("<tr>").println();
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getPurchaseIdentifier())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getPurchaseDate())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getPurchaseDetailIdentifier())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getPersonIdentifier())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getFullname())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getPaymentDate())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getDeliveryDate())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getProductIdentifier())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getProductLabel())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getCount())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getUnitPrice())).append("</td>");
			pw.append("<td>").append(ServletHTMLUtil.getValue(row.getProductPrice())).append("</td>");
			pw.append("</tr>").println();
		}
		pw.append("</table>").println();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AppLog.out("PurchasesReportServlet.doPost(...)");
		doGet(request, response);
	}

}
