<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="shoppingcart.Cart" import="java.util.HashMap" import="shoppingcart.MovieItem" import="java.text.DecimalFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Checkout</title>
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
	<%		
	Cart shoppingCart = (Cart) session.getAttribute("cart");
			String servletDispatcher = (String) session.getAttribute("origin");
			String contextPath = (String) session.getAttribute("contextPath"); 	
			out.println("<center>");
			out.println("<h2><font color='chucknorris'>Cart</h2>");
			HashMap<String,MovieItem> items = shoppingCart.getCartItems();
 			out.println("<table border='1px'>"); 

			if (!items.isEmpty()) {
	 			for(MovieItem item: items.values()){
					 out.println("<tr><td><font color='white'>" + item.title +"</font></td><td><font color='white'>x" + item.quantity 
							 + "</font></td>" + "<td><font color='white'>$" + item.price + "</font></td></tr>"); 
				} 
			}
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			out.println("<tr><td><font color='white'>" + "Total is $" + df.format(Math.round(shoppingCart.total * 100.0) / 100.0) + "</font></td></tr>");
 			out.println("</table>"); 
 			if (shoppingCart.total != 0 )
 				out.println("<h2><a href='payingInfo' style='text-decoration: none;'><font color='chucknorris'>Pay</font></a></h2>");
			%>
		</center>
</body>
</html>