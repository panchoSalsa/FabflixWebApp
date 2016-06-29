<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List"%>
    	<% 
		List<String> genres = (List<String>)session.getAttribute("Genres");
    	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Browse By Genre</title>
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
<%	out.println("<table border='true'>");
		for(String genre: genres){
			out.println("<tr>");
			out.println("<td>");
			out.println("<a href=\"movieByGenre?genre=" + genre + 
					"\" style=\"text-decoration: none;\"><font color=\"chucknorris\">" + genre + "</font></a>");
			out.println("</td>");
			out.println("</tr>");
		}
		out.println("</table>");
	%>
</center>
</body>
</html>