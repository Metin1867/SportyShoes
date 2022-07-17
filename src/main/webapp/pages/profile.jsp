<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Profile</title>
    <script src="jquery-3.6.0.min.js"></script> 
    <script src="sportyshoes.js"></script> 
</head>
<body>
<table width="100%">
<tr><th colspan="3"><div data-include="healogin"></div>
</th></tr>
<tr width="100%">
<td id="dynav" width="10%">
	<center>
	<nav style="background-color:cyan">
<%
	Object usrLoginObj = session.getAttribute("userid");
	String usrLogin=null;
	if (usrLoginObj!=null) {
		usrLogin = (String) usrLoginObj;
		if ("".equals(((String) usrLogin).trim()))
			usrLogin=null;
	}
	Object isEmployeeObj = session.getAttribute("employee");
	boolean isEmployee = false;
	if (isEmployeeObj!=null)
		isEmployee = (boolean) isEmployeeObj;
		
	if (usrLogin != null) {
		if (isEmployee) {
			out.println("<a href='/UsersServlet'>Users</a><br>");
			out.println("<a href='/EmployeesServlet'>Employee</a><br>");
			out.println("<a href='/ProductsServlet'>Product</a><br>");
			out.println("<a href='/CategoriesServlet'>Category</a><br>");
		} 
		out.println("<a href='/ShopServlet'>Shop</a><br>");
		out.println("<a href='/PurchasesServlet'>Purchase</a><br>");
		out.println("<a href='/Logout'>Logout</a><br>");
	} else {
			out.println("Redirect to main page");
%>
<jsp:forward page="../index.html" />
<%	}
%>
	</nav>
	</center>
</td>
<td width="70%">
This is the great portal to world of Sporty Shoes!
Welcome and nice shopping.
Please let us know if you miss something.
</td>
<td width="20%">
Here can you found interesting news to shoes!
Or put some helpful messages self!
</td>
</tr>
<tr>
<td>
</td>
<td>
</td>
<td>
</td>
</tr>
</table>

</body>
</html>