<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Payment</title>
</head>
<div class="header" style="width: 100%; height: 100px;">
<div align="right" style="margin-right: 100px; margin-top: 20px;">
<a href="logout" style="text-decoration: none;"><b><font color="white">Sign out of Fabflix</font></b></a>
</div>
<div align="left" style="margin-top: -40px;">
	<a href="main">
	<img src="${pageContext.request.contextPath}/Fabflix_logo.png" alt="logo" style="margin: 23px 0px 0px 41px; height: 90px; width: 188px;">
	</a></div><div style="margin-left: -px; margin-top: -80px;">
	<%
		Object obj = session.getAttribute("cart");
		
			out.println("<div align=\"center\" style='margin-top: -50px; margin-left: -20px;'><a href='checkout' style='text-decoration: none'>"
			+ "<font color='white'><b>Checkout </b>");
	%>
	</font><img src="${pageContext.request.contextPath}/checkout.png" style="height: 50px; width: 50px;"></a></div></div>
</div>
<body style="background-color: black;">
<center>
	<form action="pay" method="post">
	  <font color='white'>First name:</font><br>
	  <input type="text" name="firstname">
	  <br>
	  <font color='white'>Last name:</font><br>
	  <input type="text" name="lastname">
	  <br>
	  <font color='white'>Credit Card Number:</font><br>
	  <input type="text" name="creditcard">
	  <br>
	  <font color='white'>Exp Date:</font><br>
	  <input type="text" name="exp"><br><br>
	  <input type="submit">
	</form>
	</center>
</body>
</html>