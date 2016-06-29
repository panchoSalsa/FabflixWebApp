<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="models.Movies" import="models.Stars" %>
    <% 
	String context = (String) session.getAttribute("contextPath");
	String servletDispatcher = (String) session.getAttribute("origin");
		Stars star = (Stars) request.getAttribute("Star");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><% out.println(star.getName()); %></title>
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
	</font><img src="${pageContext.request.contextPath}/checkout.png" style="height: 50px; width: 50px;"></a></div></div>div>
</div>

<body style="background-color: black;">
<center>
	<%
	out.println("<h1><font color=\"chucknorris\">"+star.getName()+"</font></h1>");
	out.println("<img src='" + star.getPicture() +"' alt=\"Image unavailable\" style=\"color: white;\"><br>");
	out.println("<font color=\"chucknorris\">Date of Birth: </font><font color=\"white\">" + star.getDob() + "</font><br>");
	out.println("<font color=\"chucknorris\">Star ID: </font><font color=\"white\">" + star.getId() + "</font><br>");

	out.println("<br><font color=\"chucknorris\">Starring In: </font><br>");
	
	for(Movies movie: star.getMovies()){
		out.println("<a href ='SingleMovie?id=" + movie.getId() + "' style=\"text-decoration: none;\"> <font color=\"white\">" 
	+ movie.getTitle() + ",</font></a>");
	}
	out.println("</table>");
	%>
	</center>
</body>
</html>