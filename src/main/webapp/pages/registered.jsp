<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registered</title>
</head>
<body>
<h1>Thanks for registration!</h1>
<%=request.getAttribute("msg")%><br>
<form action="index.html">
<input type="submit" name="fromRegistered" value="Home">
</form>
</body>
</html>