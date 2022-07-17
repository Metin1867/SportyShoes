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
import tr.com.macik.myapp.pojo.Purchase;

/**
 * Servlet implementation class PurchaseDeleteServlet
 */
@WebServlet("/PurchaseDeleteServlet")
public class PurchaseDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int pchid;
	String msg;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		pw.append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		if ("yes".equals(request.getParameter("confirmed"))) {
			System.out.println("Delete confirmed");
			Purchase pch = new Purchase();
			pch.setPchID(pchid);
			PurchaseDto.delete(pch);

			RequestDispatcher rd = request.getRequestDispatcher("PurchasesServlet");
			rd.forward(request, response);
		} else {
			pchid = Integer.valueOf(request.getParameter("pchid"));
			msg = request.getParameter("msg");

			pw.append("<h1>Delete Confirmation</h1>").println();
			pw.append("<form action='PurchaseDeleteServlet?confirmed=yes' method='post'>").println();
			pw.append("<input type='submit' value='Yes'><br>").println();
			pw.append("</form>").println();   
			pw.append("<form action='PurchasesServlet'>").println();
			pw.append("<input type='submit' value='No'><br>").println();
			pw.append("</form>").println();   
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
