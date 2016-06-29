<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
	<form method=get action="titleServlet">
		<select name="choice">
		  <option value="0">0</option>
		  <option value="1">1</option>
		  <option value="2">2</option>
		  <option value="3">3</option>
		  <option value="4">4</option>
		  <option value="5">5</option>
		  <option value="6">6</option>
		  <option value="7">7</option>
		  <option value="8">8</option>
		  <option value="9">9</option>
		  <option value="A">A</option>
		  <option value="B">B</option>
		  <option value="C">C</option>
		  <option value="D">D</option>
		  <option value="E">E</option>
		  <option value="F">F</option>
		  <option value="G">G</option>
		  <option value="H">H</option>
		  <option value="I">I</option>
		  <option value="J">J</option>
		  <option value="K">K</option>
		  <option value="L">L</option>
		  <option value="M">M</option>
		  <option value="N">N</option>
		  <option value="O">O</option>
		  <option value="P">P</option>
		  <option value="Q">Q</option>
		  <option value="R">R</option>
		  <option value="S">S</option>
		  <option value="T">T</option>
		  <option value="U">U</option>
		  <option value="V">V</option>
		  <option value="W">W</option>
		  <option value="X">X</option>
		  <option value="Y">Y</option>
		  <option value="Z">Z</option>		  
		</select>
		<br><br><input type="image" src="${pageContext.request.contextPath}/search.png" >
	</form>
	</center>
</body>
</html>