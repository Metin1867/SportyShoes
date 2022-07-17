package tr.com.macik.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tr.com.macik.gui.PurchaseDto;
import tr.com.macik.myapp.pojo.PurchaseDetails;
import tr.com.macik.utils.AppLog;

/**
 * Servlet implementation class PurchaseDeleteServlet
 */
@WebServlet("/PurchaseDetailsDeleteServlet")
public class PurchaseDetailsDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int pchid;
	int pcdid;
	String msg;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseDetailsDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AppLog.out("PurchaseDetailsDeleteServlet.doGet(...)");
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		if ("yes".equals(request.getParameter("confirmed"))) {
			System.out.println("Delete confirmed");
			PurchaseDetails pcd = new PurchaseDetails();
			pcd.setPcdID(pcdid);
			PurchaseDto.delete(pcd);

			String redirect="PurchaseEditServlet?submit=doGet&pchid="+pchid;
			AppLog.keyVal("Redirect to", redirect);
			RequestDispatcher rd = request.getRequestDispatcher(redirect);
			rd.forward(request, response);
		} else {
			pchid = Integer.valueOf(request.getParameter("pchid"));
			pcdid = Integer.valueOf(request.getParameter("pcdid"));
			msg = request.getParameter("msg");

			pw.append("<h1>Delete Confirmation</h1>").println();
			pw.append("<form action='PurchaseDetailsDeleteServlet?confirmed=yes' method='post'>").println();
			pw.append("<input type='submit' value='Yes'><br>").println();
			pw.append("</form>").println();   
			pw.append("<form action='PurchaseEditServlet?submit=doGet&pchid='"+pchid+">").println();
			pw.append("<input type='submit' value='No'><br>").println();
			pw.append("</form>").println();   
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AppLog.out("PurchaseDetailsDeleteServlet.doPost(...)");
		doGet(request, response);
	}

}
