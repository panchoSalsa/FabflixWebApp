<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<style>
html
{
	height: 100%; 
	width: 100%;
	margin: -1px 0px 0px 0px;
	overflow-x: hidden;
}
body
{
	height: 100%;  
	margin: 0px 0px 0px 0px;
	overflow-x: hidden;
	overflow-y: hidden;
}
.splash
{
	background: url('${pageContext.request.contextPath}/dd_splash.png'); 
	background-size: 100%;
	z-index: -2;
	margin: 0px 0px 0px 0px;
	background-repeat: no-repeat;
	overflow-x: hidden;
	overflow-y: hidden;
}
.logon_container
{
margin-left: -452px;
}
input[type="text"]
{
margin-left: -186px;
}
input[type="image"]
{
margin-left: -187px;
}
</style>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
<script src='https://www.google.com/recaptcha/api.js'></script>
</head>

<body>
 

<div class="splash">
	<a href="login.jsp">
	<img src="${pageContext.request.contextPath}/Fabflix_logo.png" alt="logo" style="margin: 23px 0px 0px 41px; height: 90px; width: 188px;">
	</a>
		<div class="logon_container">
			<img src="${pageContext.request.contextPath}/100_logon.png" alt="logon" style="position: relative;
				left: 50%;
				margin-top: -21px;
				width: 462px;
				height: 532px;
				z-index: 0;">
		</div>
	<form method="post" action="LoginServlet"> 
			<input name="email" value="a@email.com" type="text" style="left: 50%; top: -417px; width: 378px; height: 38px; position: relative; z-index: 2;">
			<br>
			<input name="password" value="a2" type="text" style="left: 50%; top: -377px; width: 378px; height: 38px; position: relative; z-index: 3;">
			<input type="image" src="${pageContext.request.contextPath}/100_signin.png" style="left: 50%; top: 425px; width: 383px; position: absolute; z-index: 4;">
 			<div class="captchacontainer" style="margin-left: -295px;">
			<div class="g-recaptcha" data-sitekey="6LdrLx8TAAAAAKwERj91di___qTZ4AgeB6TjrJPc" style="left: 50%; top: -368px; z-index: 4; position: relative;"></div> 					
			</div> 
	</form>
</div>

  
</FORM>

</body>
</html>