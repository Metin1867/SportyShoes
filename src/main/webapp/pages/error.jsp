<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Let us inform! It can be happening.</h1>
<img src="../img/smiley.jpg" alt="Sorry!"></img>
Unfortunately, an unexpected error is occurred.<br>
<%=request.getAttribute("msg")%><br>
Please send an email to this email <a href="mailto:support@sportyshoes.com">support@sportyshoes.com</a>.<br>
Thank you for your appreciation.<br>
<form action="index.html">
<input type="submit" name="fromError" value="Home">
</form>

</body>
</html>